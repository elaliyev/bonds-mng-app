package com.bonds.rest;

import com.bonds.exception.NotFoundException;
import com.bonds.model.Customer;
import com.bonds.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @PostMapping("customers/save")
    public ResponseEntity<Resource<Customer>> createSCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.save(customer);

        Resource<Customer> customerResource = new Resource<Customer>(savedCustomer);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCustomerById(savedCustomer.getId()));
        customerResource.add(linkTo.withRel("customer/{id}"));

        return new ResponseEntity<Resource<Customer>>(customerResource, HttpStatus.CREATED);

    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {

        Optional<Customer> customer = customerService.findById(id);
        if (!customer.isPresent())
            throw new NotFoundException("id",id,"Customer");

        return new ResponseEntity<Customer>(customer.get(),HttpStatus.OK);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer, @PathVariable long id) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent())
            return ResponseEntity.notFound().build();

        customer.setId(id);
        customerService.save(customer);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (!customer.isPresent())
            throw new NotFoundException("id",id,"Customer");

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
