package com.ARD.eCommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_permissions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_id")
    Long roleId;
    @Column(name = "permission_id")
    Long permissionId;
}
