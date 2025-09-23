package com.tisitha.emarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private VendorProfile vendorProfile;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double deal;

    @Column(nullable = false)
    private boolean cod;

    @Column(nullable = false)
    private boolean freeDelivery;

    @Column(nullable = false)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "warranty_id")
    private Warranty warranty;

    @OneToMany(mappedBy = "product")
    private List<Question> questions;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    private Integer quantity;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

}

