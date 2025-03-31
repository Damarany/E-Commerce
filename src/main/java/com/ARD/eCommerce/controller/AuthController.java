package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.JwtResponseDto;
import com.ARD.eCommerce.dtos.LoginDto;
import com.ARD.eCommerce.services.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        JwtResponseDto responseDto = authService.login(loginDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/test")
public String test(){
    return "Testing....";
}
}
