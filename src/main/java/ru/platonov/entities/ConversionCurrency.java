package ru.platonov.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "conversion_currency")
@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "currency_pair")
    private String CurrencyPair;

    private BigDecimal value;
}
