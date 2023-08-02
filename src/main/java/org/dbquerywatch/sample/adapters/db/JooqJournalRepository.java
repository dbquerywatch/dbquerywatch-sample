package org.dbquerywatch.sample.adapters.db;

import org.dbquerywatch.sample.application.out.JournalRepository;
import org.dbquerywatch.sample.domain.Journal;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.dbquerywatch.sample.jooq.tables.Journals.JOURNALS;

@Repository
@RequiredArgsConstructor
class JooqJournalRepository implements JournalRepository {

    private final DSLContext dslContext;
    private final JournalRecordMapper recordMapper;

    public List<Journal> findByPublisher(String publisher) {
        return dslContext.selectFrom(JOURNALS)
            .where(JOURNALS.PUBLISHER.eq(publisher))
            .fetch(recordMapper::fromJooqRecord);
    }
}