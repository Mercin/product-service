package com.example.productservice.dao.model;

import com.example.productservice.util.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, columnDefinition = "UUID")
    private UUID id = UUID.randomUUID();

    @Column(name = "PRICE")
    private int priceInCents;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
}
