package com.ARD.eCommerce.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
