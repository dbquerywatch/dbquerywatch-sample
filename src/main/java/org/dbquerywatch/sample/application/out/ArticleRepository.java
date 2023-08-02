package org.dbquerywatch.sample.application.out;

import org.dbquerywatch.sample.domain.Article;
import org.dbquerywatch.sample.domain.ArticleQuery;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findById(int id);

    List<Article> query(ArticleQuery query);
}
