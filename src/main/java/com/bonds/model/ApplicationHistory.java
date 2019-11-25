package com.bonds.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="APPLICATION_HISTORY")
public class ApplicationHistory {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="APPLICATON_ID")
    private Application application;

    @Column(name="UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name="LAST_TERM")
    private int lastTerm;

    @Column(name="UPDATED_TERM")
    private int updatedTerm;

    @Column(name="CLIENT_TERM_STATUS")
    @Enumerated(EnumType.STRING)
    private ClientTermStatus clientTermStatus;

    @Column(name="AMOUNT_OF_COUPON")
    private BigDecimal amountOfCoupon;
}
