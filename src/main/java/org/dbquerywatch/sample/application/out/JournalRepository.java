package org.dbquerywatch.sample.application.out;

import org.dbquerywatch.sample.domain.Journal;

import java.util.List;

public interface JournalRepository {

    List<Journal> findByPublisher(String publisher);
}
