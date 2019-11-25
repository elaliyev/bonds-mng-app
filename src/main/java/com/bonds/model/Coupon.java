package com.bonds.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="COUPON")
public class Coupon {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="RATE")
    private double rate;

    @Column(name="TERM")
    @Min(5)
    private int term;

    @Column(name="PRICE")
    private BigDecimal price;

}
