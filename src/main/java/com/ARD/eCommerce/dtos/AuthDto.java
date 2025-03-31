package com.ARD.eCommerce.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AuthDto {
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;
    private List<String>permissions;
}

