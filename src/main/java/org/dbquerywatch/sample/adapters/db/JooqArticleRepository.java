package org.dbquerywatch.sample.adapters.db;

import org.dbquerywatch.sample.application.out.ArticleRepository;
import org.dbquerywatch.sample.domain.ArticleQuery;
import org.dbquerywatch.sample.domain.Article;
import org.dbquerywatch.sample.jooq.tables.records.ArticlesRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.dbquerywatch.sample.jooq.tables.Articles.ARTICLES;
import static org.jooq.impl.DSL.noCondition;

@Repository
@RequiredArgsConstructor
class JooqArticleRepository implements ArticleRepository {

    private final DSLContext dslContext;
    private final ArticleRecordMapper recordMapper;

    @Override
    public void save(Article article) {

    }

    @Override
    public Optional<Article> findById(int id) {
        try (SelectWhereStep<ArticlesRecord> selectWhereStep = dslContext.selectFrom(ARTICLES)) {
            return selectWhereStep
                .where(ARTICLES.ID.eq(id))
                .fetchOptional(recordMapper::fromJooqRecord);
        }
    }

    @Override
    public List<Article> query(ArticleQuery query) {
        Condition condition = noCondition()
            .and(elvis(query.getAuthorLastName(), ARTICLES.AUTHOR_LAST_NAME::eq))
            .and(elvis(query.getFromYear(), (Integer y) -> ARTICLES.PUBLISHED_AT.greaterOrEqual(firstDayOfYear(y))))
            .and(elvis(query.getToYear(), (Integer y) -> ARTICLES.PUBLISHED_AT.lessOrEqual(lastDayOfYear(y))));
        try (SelectWhereStep<ArticlesRecord> selectWhereStep = dslContext.selectFrom(ARTICLES)) {
            return selectWhereStep
                .where(condition)
                .fetch(recordMapper::fromJooqRecord);
        }
    }

    private static LocalDate firstDayOfYear(Integer year) {
        return LocalDate.of(year, Month.JANUARY, 1);
    }

    private static LocalDate lastDayOfYear(Integer year) {
        return LocalDate.of(year, Month.DECEMBER, 31);
    }

    private static <T> Condition elvis(T value, Function<T, Condition> mapper) {
        if (value == null) {
            return noCondition();
        }
        return mapper.apply(value);
    }
}
