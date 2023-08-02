package org.dbquerywatch.sample.domain;

import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ArticleQuery {
    @Nullable
    String authorLastName;

    @Nullable
    Integer fromYear;

    @Nullable
    Integer toYear;

    @Nullable
    String journalName;
}
