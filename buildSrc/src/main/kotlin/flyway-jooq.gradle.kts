import nu.studer.gradle.jooq.JooqExtension
import nu.studer.gradle.jooq.JooqGenerate
import org.flywaydb.gradle.FlywayExtension
import org.jooq.meta.jaxb.Logging
import org.slf4j.LoggerFactory
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

plugins {
    id("nu.studer.jooq")
    // @see https://documentation.red-gate.com/fd/gradle-task-184127407.html
    id("org.flywaydb.flyway")
}

val jooqVersion: String by project

dependencies {
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")  // requires 3+ for jakarta package (instead of javax)
    jooqGenerator("org.postgresql:postgresql")
}

abstract class PostgresContainerService : BuildService<BuildServiceParameters.None>, AutoCloseable {

    internal val container = PostgreSQLContainer<Nothing>("postgres:15.3-alpine").apply {
        withLogConsumer(Slf4jLogConsumer(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)))
        withTmpFs(mapOf("/var/lib/postgresql/data" to "rw"))
    }

    init {
        container.start()
    }

    override fun close() = container.stop()
}

val pgContainerService = gradle.sharedServices.registerIfAbsent("postgresContainerService", PostgresContainerService::class) {}

configure<JooqExtension> {
    version.set(jooqVersion)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                generator.apply {
                    generate.apply {
                        withFluentSetters(true)
                    }
                    database.apply {
                        inputSchema = "public"
                        excludes = "flyway_schema_history"
                    }
                    target.packageName = "com.parolisoft.dbquerywatch.sample.jooq"
                }
            }
        }
    }
}

fun FlywayExtension.configureJdbc(container: JdbcDatabaseContainer<*>) = apply {
    driver = container.driverClassName
    url = container.jdbcUrl
    user = container.username
    password = container.password
}

fun JooqExtension.configureJdbc(container: JdbcDatabaseContainer<*>) = apply {
    configurations.forEach {
        it.jooqConfiguration.jdbc.apply {
            driver = container.driverClassName
            url = container.jdbcUrl
            user = container.username
            password = container.password
        }
    }
}

tasks.named<JooqGenerate>("generateJooq") {
    allInputsDeclared.set(true)
    inputs.files(fileTree("src/main/resources/db/migration"))
        .withPropertyName("jooqMigrationsInput")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    outputs.files(fileTree("$buildDir/generated-src/jooq"))
        .withPropertyName("jooqMigrationsOutput")

    usesService(pgContainerService)

    doFirst {
        val pgContainer = pgContainerService.get().container
        project.configure<FlywayExtension> {
            configureJdbc(pgContainer)
        }
        logger.lifecycle("> Task :{}", tasks.flywayMigrate.name)
        tasks.flywayMigrate.get().runTask()
        project.configure<JooqExtension> {
            configureJdbc(pgContainer)
        }
    }
}
