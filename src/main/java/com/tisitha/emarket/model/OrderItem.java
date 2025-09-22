package com.tisitha.emarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class OrderItem {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String vendorName;

    @Column(nullable = false)
    private UUID vendorId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double totalPrice;

}
