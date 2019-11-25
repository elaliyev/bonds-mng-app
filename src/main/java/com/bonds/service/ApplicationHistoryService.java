package com.bonds.service;

import com.bonds.model.ApplicationHistory;
import java.util.List;
import java.util.Optional;


public interface ApplicationHistoryService {

    List<ApplicationHistory> findAll();
    Optional<ApplicationHistory> findById(long id);
    ApplicationHistory save(ApplicationHistory applicationHistory);
    void deleteById(long id);
    List<ApplicationHistory> findByCustomerId(long customerId);
}
