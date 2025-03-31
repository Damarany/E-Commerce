package com.ARD.eCommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseAPI {
    String message;
    Object object;
}
