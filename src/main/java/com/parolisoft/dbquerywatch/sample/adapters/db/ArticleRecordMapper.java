package com.parolisoft.dbquerywatch.sample.adapters.db;

import com.parolisoft.dbquerywatch.sample.domain.Article;
import com.parolisoft.dbquerywatch.sample.jooq.tables.records.ArticlesRecord;
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
