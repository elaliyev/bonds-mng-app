package com.bonds.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="APPLICATION")
public class Application {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="APPLICATION_DATE")
    private LocalDate applicationDate;

    @Column(name="TERM")
    private int term;

    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="COUPON_ID")
    private Coupon coupon;

    @Column(name="PAID_AMOUNT")
    private BigDecimal paidAmount;

    @Column(name="IP_ADDRESS")
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column(name="COUPON_TOTAL_AMOUNT")
    private BigDecimal couponTotalAmount;

    public Application(long id,int term,Customer customer,Coupon coupon,BigDecimal paidAmount){
        this.id=id;
        this.term=term;
        this.customer=customer;
        this.coupon=coupon;
        this.paidAmount=paidAmount;
    }
}
