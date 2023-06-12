package com.parolisoft.dbquerywatch.sample.adapters.api;

import com.parolisoft.dbquerywatch.sample.application.service.ArticleQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ArticleQueryMapper {
    ArticleQuery fromModel(ArticleQueryModel model);
}
