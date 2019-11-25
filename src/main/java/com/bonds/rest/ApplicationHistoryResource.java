package com.bonds.rest;

import com.bonds.exception.NotFoundException;
import com.bonds.model.ApplicationHistory;
import com.bonds.model.Customer;
import com.bonds.service.ApplicationHistoryService;
import com.bonds.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ApplicationHistoryResource {

    @Autowired
    private ApplicationHistoryService applicationHistoryService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("application-histories/{id}")
    public ResponseEntity<ApplicationHistory> getApplicationHistoryById(@PathVariable long id) {

        Optional <ApplicationHistory> applicationHistory = applicationHistoryService.findById(id);
        if (!applicationHistory.isPresent())
            throw new NotFoundException("id",id,"ApplicationHistory");

        return new ResponseEntity<ApplicationHistory>(applicationHistory.get(),HttpStatus.OK);
    }

    @GetMapping("/application-histories")
    public List<ApplicationHistory> getAllApplicationHistories() {
        return applicationHistoryService.findAll();
    }

    @GetMapping("/application-histories/customer/{customerId}")
    public List<ApplicationHistory> getApplicationHistoriesByCustomerId(@PathVariable long customerId) {

        Optional<Customer> customer = customerService.findById(customerId);
        if (!customer.isPresent())
            throw new NotFoundException("id",customerId,"Customer");

        return applicationHistoryService.findByCustomerId(customerId);
    }

    @PutMapping("/application-histories/{id}")
    public ResponseEntity<Object> updateApplicationHistory(@RequestBody ApplicationHistory applicationHistory, @PathVariable long id) {

        Optional<ApplicationHistory> applicationOptional = applicationHistoryService.findById(id);

        if (!applicationOptional.isPresent())
            return ResponseEntity.notFound().build();

        applicationHistory.setId(id);
        applicationHistoryService.save(applicationHistory);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/application-histories/{id}")
    public ResponseEntity deleteApplicationHistory(@PathVariable long id) {
        Optional<ApplicationHistory> applicationHistory = applicationHistoryService.findById(id);

        if (!applicationHistory.isPresent())
            throw new NotFoundException("id",id,"ApplicationHistory");

        applicationHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
