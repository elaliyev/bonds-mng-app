package com.bonds.repository;

import com.bonds.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Override
    Optional<Customer> findById(Long id);

    @Override
    List<Customer> findAll();

    @Override
    Customer save(Customer customer);

    @Override
    void deleteById(Long id);
}
