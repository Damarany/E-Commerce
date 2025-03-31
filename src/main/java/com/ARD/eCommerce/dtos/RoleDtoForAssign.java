package com.ARD.eCommerce.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoleDtoForAssign {
    private Long id;
    private String name;
    private List<Long> permissionId;
}
