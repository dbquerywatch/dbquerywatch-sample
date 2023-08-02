package org.dbquerywatch.sample.application;

import org.dbquerywatch.api.spring.junit5.CatchSlowQueries;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/data.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@CatchSlowQueries
public class MockMvcIntegrationTests {

    @SuppressWarnings("resource")
    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.3-alpine")
        .withTmpFs(singletonMap("/var/lib/postgresql/data", "rw"))
        .withCommand("postgres", "-c", "fsync=off", "-c", "enable_seqscan=off");

    @Autowired
    MockMvc mvc;

    @DynamicPropertySource
    static void setupDatasource(DynamicPropertyRegistry registry) {
        postgresContainer.start();
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    void should_find_article_by_author_last_name() throws Exception {
        mvc.perform(post("/articles/query")
                .content(new JSONObject().put("author_last_name", "Parnas").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.length()").value(1),
                jsonPath("$[0].author_full_name").value("David L. Parnas")
            );
    }

    @Test
    void should_find_article_by_year_range() throws Exception {
        mvc.perform(post("/articles/query")
                .content(new JSONObject().put("from_year", 1970).put("to_year", 1980).toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.length()").value(3),
                jsonPath("$[*].author_last_name").value(containsInAnyOrder(
                    "Parnas",
                    "Diffie-Hellman",
                    "Lamport"
                ))
            );
    }

    @Test
    void should_find_journal_by_publisher() throws Exception {
        mvc.perform(get("/journals/{publisher}", "ACM")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.length()").value(1),
                jsonPath("$[0].name").value("Communications of the ACM")
            );
    }
}
