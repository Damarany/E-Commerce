package com.ARD.eCommerce.services.security;

import com.ARD.eCommerce.configurations.security.JWT.JwtUtilty;
import com.ARD.eCommerce.dtos.AuthDto;
import com.ARD.eCommerce.dtos.JwtResponseDto;
import com.ARD.eCommerce.dtos.LoginDto;
import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.model.Role;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.repository.PermissionRepo;
import com.ARD.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PermissionRepo permissionRepo;
    private final AuthenticationManager authenticationManager;

    private final JwtUtilty jwtUtilty;
    @Transactional
    public JwtResponseDto login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getJwtResponse(loginDto.getEmail());
    }

    @Transactional
    public JwtResponseDto getJwtResponse(String email){
        User user = userRepo.findByEmail(email);
        List<Permission> permissions = permissionRepo.getPermissionByUserId(user.getId());
        String token = jwtUtilty.generateToken(AuthDto
                .builder()
                .email(user.getEmail())
                .username(user.getFirstName())
                .userId(user.getId())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .permissions(permissions.stream().map(Permission::getName).toList())
                .build());
        return JwtResponseDto
                .builder()
                .token(token)
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .permissions(permissions.stream().map(Permission::getName).toList())
                .userId(user.getId())
                .username(user.getFirstName())
                .build();

    }













}
