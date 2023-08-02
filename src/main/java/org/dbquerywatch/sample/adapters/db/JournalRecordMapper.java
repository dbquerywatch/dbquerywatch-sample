package org.dbquerywatch.sample.adapters.db;

import org.dbquerywatch.sample.domain.Journal;
import org.dbquerywatch.sample.jooq.tables.records.JournalsRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface JournalRecordMapper {

    JournalsRecord toJooqRecord(Journal journal);

    Journal fromJooqRecord(JournalsRecord journalRecord);
}
