package org.dbquerywatch.sample.application.service;

import org.dbquerywatch.sample.application.out.JournalRepository;
import org.dbquerywatch.sample.domain.Journal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository repository;

    public List<Journal> findByPublisher(String publisher) {
        return repository.findByPublisher(publisher);
    }
}
