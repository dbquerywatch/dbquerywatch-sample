package org.dbquerywatch.sample.adapters.db;

import org.dbquerywatch.sample.domain.Article;
import org.dbquerywatch.sample.jooq.tables.records.ArticlesRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface ArticleRecordMapper {

    ArticlesRecord toJooqRecord(Article article);

    Article fromJooqRecord(ArticlesRecord articlesRecord);
}
