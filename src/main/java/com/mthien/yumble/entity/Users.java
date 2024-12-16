package com.mthien.yumble.entity;

import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "dietary")
    private String dietary;

    @Column(name = "allergy")
    private String allergy;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
