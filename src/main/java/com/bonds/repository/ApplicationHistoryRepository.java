package com.bonds.repository;

import com.bonds.model.ApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory,Long> {

    @Override
    Optional<ApplicationHistory> findById(Long aLong);

    @Override
    List<ApplicationHistory> findAll();

    @Override
    ApplicationHistory save(ApplicationHistory applicationHistory);

    @Override
    void deleteById(Long id);

    @Query("SELECT a FROM ApplicationHistory a WHERE a.application.customer.id=:customerId")
    List<ApplicationHistory> findByCustomerId(@Param("customerId")long customerId);
}
