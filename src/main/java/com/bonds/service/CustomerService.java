package com.bonds.service;

import com.bonds.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();
    Optional<Customer> findById(long id);
    Customer save(Customer customer);
    void deleteById(long id);
}
