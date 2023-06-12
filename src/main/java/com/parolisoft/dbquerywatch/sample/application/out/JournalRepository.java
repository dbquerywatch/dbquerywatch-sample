package com.parolisoft.dbquerywatch.sample.application.out;

import com.parolisoft.dbquerywatch.sample.domain.Journal;

import java.util.List;

public interface JournalRepository {

    List<Journal> findByPublisher(String publisher);
}
