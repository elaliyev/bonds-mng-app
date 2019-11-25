package com.bonds.service;

import com.bonds.model.Application;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    List<Application> findAll();
    Optional<Application> findById(long id);
    Application save(Application application);
    void deleteById(long id);
    List<Application> findApplicationByApplicationDate(LocalDate date);
}
