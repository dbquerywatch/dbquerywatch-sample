package com.parolisoft.dbquerywatch.sample.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Article {
    Integer id;
    LocalDate publishedAt;
    String authorFullName;
    String authorLastName;
    String title;
    Journal journal;
}
