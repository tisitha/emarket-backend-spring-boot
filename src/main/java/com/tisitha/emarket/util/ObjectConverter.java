package com.tisitha.emarket.util;

import com.tisitha.emarket.dto.*;
import com.tisitha.emarket.model.*;

import java.util.OptionalDouble;

public class ObjectConverter {

    public static ProductResponseDto mapProductToProductDto(Product product){
        OptionalDouble average = product.getReviews().stream()
                .mapToInt(Review::getRate)
                .average();
        return new ProductResponseDto(
                product.getId(),
                mapVendorToVendorDto(product.getVendorProfile()),
                product.getName(),
                product.getImgUrl(),
                product.getDescription(),
                product.getPrice(),
                product.getDeal(),
                product.isCod(),
                product.isFreeDelivery(),
                product.getBrand(),
                mapCategoryToCategoryDto(product.getCategory()),
                average.isPresent()?average.getAsDouble():null,
                mapProvinceToProvinceDto(product.getProvince()),
                mapWarrantyToWarrantyDto(product.getWarranty()),
                product.getQuantity()
        );
    }

    public static AccountResponseDto mapAccountToAccountDto(User user){
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(user.getId());
        accountResponseDto.setFname(user.getFname());
        accountResponseDto.setLname(user.getLname());
        accountResponseDto.setEmail(user.getEmail());
        accountResponseDto.setPhoneNo(user.getPhoneNo());
        accountResponseDto.setAddress(user.getAddress());
        accountResponseDto.setRole(user.getRole());
        accountResponseDto.setProvince(mapProvinceToProvinceDto(user.getProvince()));
        VendorProfile vendorProfile = user.getVendorProfile();
        if(vendorProfile==null){
            return accountResponseDto;
        }
        accountResponseDto.setBusinessName(vendorProfile.getBusinessName());
        accountResponseDto.setBankAccountNo(vendorProfile.getBankAccountNo());
        accountResponseDto.setBank(vendorProfile.getBank());
        return accountResponseDto;
    }

    public static VendorProfileDto mapVendorToVendorDto(VendorProfile vendorProfile){
        return new VendorProfileDto(
                vendorProfile.getVendorId(),
                vendorProfile.getBusinessName());
    }

    public static CategoryResponseDto mapCategoryToCategoryDto(Category category){
        return new CategoryResponseDto(category.getId(),category.getName());
    }

    public static CarouselResponseDto mapCarouselToCarouselDto(Carousel carousel){
        return new CarouselResponseDto(carousel.getId(), carousel.getName(), carousel.getImgUrl(), carousel.isHidden());
    }

    public static CartItemResponseDto mapCartItemToCartItemDto(CartItem cartItem){
        return new CartItemResponseDto(
                cartItem.getId(),
                mapUserToUserDto(cartItem.getUser()),
                cartItem.getQuantity(),
                mapProductToProductDto(cartItem.getProduct())
        );
    }

    public static NotificationResponseDto mapNotificationToDto(Notification notification){
        return new NotificationResponseDto(
                notification.getId(),
                notification.getMessage(),
                notification.getAttachedId(),
                notification.getNotificationType(),
                notification.isSeen(),
                notification.getDateAndTime()
        );
    }

    public static OrderResponseDto mapOrderToOrderDto(Order order){
        return new OrderResponseDto(order.getId(),
                mapUserToUserDto(order.getUser()),
                order.getOrderStatus(),
                mapPaymentMethodToPaymentMethodDto(order.getPaymentMethod()),
                mapProductToProductDto(order.getProduct()),
                mapVendorToVendorDto(order.getVendorProfile()),
                order.getDate(),
                order.getQuantity(),
                order.getCost(),
                order.getDeliveryCost(),
                order.getTotalCost());
    }

    public static PaymentMethodResponseDto mapPaymentMethodToPaymentMethodDto(PaymentMethod paymentMethod){
        return new PaymentMethodResponseDto(paymentMethod.getId(),paymentMethod.getName());
    }

    public static ProvinceResponseDto mapProvinceToProvinceDto(Province province){
        return new ProvinceResponseDto(province.getId(),province.getName());
    }

    public static QuestionResponseDto mapQuestionToQuestionDto(Question question){
        return new QuestionResponseDto(
                question.getId(),
                question.getQuestion(),
                question.getAnswer(),
                mapProductToProductDto(question.getProduct()),
                mapUserToUserDto(question.getUser()),
                question.getDate()
        );
    }

    public static ReviewResponseDto mapReviewToReviewDto(Review review){
        return new ReviewResponseDto(
                review.getId(),
                review.getBody(),
                review.getRate(),
                mapProductToProductDto(review.getProduct()),
                review.getDate(),
                mapUserToUserDto(review.getUser()),
                review.isEdited()
        );
    }

    public static WarrantyResponseDto mapWarrantyToWarrantyDto(Warranty warranty){
        return new WarrantyResponseDto(warranty.getId(),warranty.getName());
    }

    public static UserResponseDto mapUserToUserDto(User user){
        return new UserResponseDto(user.getId(),user.getFname(),user.getLname(), user.getEmail());
    }
}
