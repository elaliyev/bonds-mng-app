package com.bonds.repository;

import com.bonds.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    @Override
    Optional<Application> findById(Long aLong);

    @Override
    List<Application> findAll();

    @Override
    Application save(Application application);

    @Override
    void deleteById(Long id);

    List<Application> findApplicationByApplicationDate(LocalDate date);
}
