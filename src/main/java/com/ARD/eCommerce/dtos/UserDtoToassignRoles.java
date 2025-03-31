package com.ARD.eCommerce.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class UserDtoToassignRoles {
    private Long id;
    private String email;
    private Set<Long>roleIds;
}
