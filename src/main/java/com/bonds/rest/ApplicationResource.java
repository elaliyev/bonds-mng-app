package com.bonds.rest;

import com.bonds.exception.BadRequestException;
import com.bonds.exception.NotFoundException;
import com.bonds.model.Application;
import com.bonds.model.ApplicationHistory;
import com.bonds.model.ClientTermStatus;
import com.bonds.model.ApplicationStatus;
import com.bonds.service.ApplicationHistoryService;
import com.bonds.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Slf4j
public class ApplicationResource {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationHistoryService applicationHistoryService;


    private static final String NEW_LINE="\n";

    @PostMapping("applications/save")
    public ResponseEntity<Resource<Application>> createApplication(@RequestBody Application application, HttpServletRequest request) {

        log.info("Applications/save method was called");
        final String ipAddress = request.getRemoteAddr();
        validateApplication(application,ipAddress);

        application.setApplicationStatus(ApplicationStatus.APPROVED);
        application.setApplicationDate(LocalDate.now());
        application.setIpAddress(ipAddress);
        application.setCouponTotalAmount(application.getPaidAmount());//customer will get his money back at the end how much he paid (e.g term*one_coupon_price)

        ApplicationHistory applicationHistory = new ApplicationHistory();
        applicationHistory.setUpdatedDate(LocalDateTime.now());
        applicationHistory.setLastTerm(application.getTerm());
        applicationHistory.setUpdatedTerm(application.getTerm());
        applicationHistory.setClientTermStatus(ClientTermStatus.INITIAL_TERM);
        applicationHistory.setAmountOfCoupon(application.getPaidAmount());//this value only for keep tracking (history)
        applicationHistory.setApplication(application);

        ApplicationHistory savedApplicationHistory = applicationHistoryService.save(applicationHistory);

        Resource<Application> applicationResource = new Resource<Application>(savedApplicationHistory.getApplication());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getApplicationById(savedApplicationHistory.getApplication().getId()));
        applicationResource.add(linkTo.withRel("applications/{id}"));

        log.info("Application saved with id {}",application.getId());
        return new ResponseEntity<Resource<Application>>(applicationResource, HttpStatus.CREATED);
    }

    @GetMapping("applications/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable long id) {

        Optional<Application> application = applicationService.findById(id);
        if (!application.isPresent())
            throw new NotFoundException("id", id, "Application");

        return new ResponseEntity<Application>(application.get(), HttpStatus.OK);
    }

    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationService.findAll();
    }

    @PutMapping("/applications/{id}")
    public ResponseEntity<Object> updateApplication(@RequestBody Application application, @PathVariable long id) {

        Optional<Application> applicationOptional = applicationService.findById(id);

        if (!applicationOptional.isPresent())
            throw new NotFoundException("id", id, "Application");

        Application currentApplication = applicationOptional.get();
        ApplicationHistory applicationHistory = new ApplicationHistory();
        applicationHistory.setLastTerm(currentApplication.getTerm());
        applicationHistory.setUpdatedDate(LocalDateTime.now());
        applicationHistory.setUpdatedTerm(application.getTerm());

        if (application.getTerm() > currentApplication.getTerm()) {
            BigDecimal newTotalAmount = currentApplication.getCouponTotalAmount().multiply(new BigDecimal("0.9"));//decrease 10%
            applicationHistory.setAmountOfCoupon(newTotalAmount);
            applicationHistory.setClientTermStatus(ClientTermStatus.INCREASED_TERM);
            currentApplication.setCouponTotalAmount(newTotalAmount);
        } else {
            applicationHistory.setClientTermStatus(ClientTermStatus.DECREASED_TERM);
            applicationHistory.setAmountOfCoupon(currentApplication.getCouponTotalAmount());
        }
        currentApplication.setTerm(application.getTerm());
        applicationHistory.setApplication(currentApplication);
        applicationHistoryService.save(applicationHistory);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity deleteApplication(@PathVariable long id) {
        Optional<Application> application = applicationService.findById(id);

        if (!application.isPresent())
            throw new NotFoundException("id", id, "Application");

        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validateApplication(Application application, String ipAddress) {
        StringBuilder builder = new StringBuilder();
        if (application.getTerm() < 5)
            builder.append("Application cannot be saved, because term must be higher than 5 years").append(NEW_LINE);
        int hour = LocalDateTime.now().getHour();
        if ((hour > 22 || hour < 6) && (application.getPaidAmount().compareTo(new BigDecimal("1000")) == 1)) {
            builder.append("Application cannot be saved beetween 18:00 and 06:00 with an amount higher than 1000").append(NEW_LINE);;
        }
        if (applicationService.findApplicationByApplicationDate(LocalDate.now()).stream().filter(a -> a.getIpAddress().equals(ipAddress)).count() > 5) {
            builder.append("Your daily limit to create application is over").append(NEW_LINE);;
        }
        if (!builder.toString().isEmpty()) {
            throw new BadRequestException(builder.toString());
        }
    }
}
