package com.parolisoft.dbquerywatch.sample.application.out;

import com.parolisoft.dbquerywatch.sample.application.service.ArticleQuery;
import com.parolisoft.dbquerywatch.sample.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findById(int id);

    List<Article> query(ArticleQuery query);
}
