package org.dbquerywatch.sample.domain;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Article {
    Integer id;
    LocalDate publishedAt;
    String authorFullName;
    String authorLastName;
    String title;
    Journal journal;
}
