package com.tisitha.emarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VendorProfile {

    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String bankAccountNo;

    @Column(nullable = false)
    private String bank;

    @OneToMany(mappedBy = "vendorProfile")
    private List<Product> products;

}