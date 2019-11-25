package com.bonds.service;

import com.bonds.model.Application;
import com.bonds.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Override
    public List<Application> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Application> findById(long id) {
         return repository.findById(id);
    }

    @Override
    public Application save(Application application) {
        return repository.save(application);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Application> findApplicationByApplicationDate(LocalDate date) {
        return repository.findApplicationByApplicationDate(date);
    }
}
