package com.parolisoft.dbquerywatch.sample.adapters.api;


import lombok.Data;

import javax.annotation.Nullable;

@Data
class ArticleQueryModel {
    @Nullable
    String authorLastName;

    @Nullable
    Integer fromYear;

    @Nullable
    Integer toYear;

    @Nullable
    String journalName;
}
