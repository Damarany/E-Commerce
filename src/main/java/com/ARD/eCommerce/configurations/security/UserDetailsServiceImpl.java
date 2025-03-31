package com.ARD.eCommerce.configurations.security;

import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

private final UserRepo userRepo;
//login with email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByEmail(username);
        if(user == null || !userRepo.existsById(user.getId())){
            throw new UsernameNotFoundException("User name not found!!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail()
                ,user.getPassword()
                ,getAuthoritiesOptimized(user));
    }

    //add roles and permissions to GrantedAuthority
    public Set<GrantedAuthority> getAuthoritiesOptimized(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Collect all roles and permissions in one pass
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));

            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });

        return authorities;
    }
}
