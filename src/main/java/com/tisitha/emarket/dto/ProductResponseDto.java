package com.tisitha.emarket.dto;

import com.tisitha.emarket.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {

    private UUID id;

    private VendorProfile vendorProfile;

    private String name;

    private String imgUrl;

    private String description;

    private double price;

    private double deal;

    private boolean cod;

    private boolean freeDelivery;

    private String brand;

    private Category category;

    private List<Review> reviews;

    private Double avgRatings;

    private Province province;

    private Warranty warranty;

    private List<Question> questions;

    private List<CartItem> cartItems;

    private Integer quantity;

}
