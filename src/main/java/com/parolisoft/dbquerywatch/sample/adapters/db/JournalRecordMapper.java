package com.parolisoft.dbquerywatch.sample.adapters.db;

import com.parolisoft.dbquerywatch.sample.domain.Journal;
import com.parolisoft.dbquerywatch.sample.jooq.tables.records.JournalsRecord;
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
