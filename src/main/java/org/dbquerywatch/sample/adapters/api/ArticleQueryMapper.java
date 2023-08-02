package org.dbquerywatch.sample.adapters.api;

import org.dbquerywatch.sample.domain.ArticleQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ArticleQueryMapper {
    ArticleQuery fromModel(ArticleQueryModel model);
}
