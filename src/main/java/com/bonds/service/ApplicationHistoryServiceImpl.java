package com.bonds.service;

import com.bonds.model.ApplicationHistory;
import com.bonds.repository.ApplicationHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ApplicationHistoryServiceImpl implements ApplicationHistoryService {

    @Autowired
    private ApplicationHistoryRepository repository;

    @Override
    public List<ApplicationHistory> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ApplicationHistory> findById(long id) {

        log.info("ApplicationHistory findById was called with parameter %s",id);
        return repository.findById(id);
    }

    @Override
    public ApplicationHistory save(ApplicationHistory applicationHistory) {
        log.info("ApplicationHistory save was called");
        return repository.save(applicationHistory);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ApplicationHistory> findByCustomerId(long customerId) {
        return repository.findByCustomerId(customerId);
    }

}
