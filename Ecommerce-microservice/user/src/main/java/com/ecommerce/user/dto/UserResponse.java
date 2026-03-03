package com.ecommerce.user.dto;

import lombok.Data;
import com.ecommerce.user.model.UserRole;


@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto address;

}
