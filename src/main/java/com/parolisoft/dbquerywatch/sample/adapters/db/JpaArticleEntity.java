package com.parolisoft.dbquerywatch.sample.adapters.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
class JpaArticleEntity {

    @Id
    Integer id;

    LocalDate publishedAt;

    String authorFullName;

    String authorLastName;

    @Column(length = 100)
    String title;

    @ManyToOne()
    @JoinColumn(name = "journal_id", updatable = false, insertable = false)
    JpaJournalEntity journal;
}
