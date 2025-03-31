package com.ARD.eCommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PermissionDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    private String name;
}
