package com.parolisoft.dbquerywatch.sample.application.service;

import com.parolisoft.dbquerywatch.sample.application.out.ArticleRepository;
import com.parolisoft.dbquerywatch.sample.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;

    public Optional<Article> findById(int id) {
        return repository.findById(id);
    }

    public List<Article> query(ArticleQuery query) {
        return repository.query(query);
    }
}
