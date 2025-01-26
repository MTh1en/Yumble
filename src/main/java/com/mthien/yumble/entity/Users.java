package com.mthien.yumble.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mthien.yumble.entity.Enum.Role;
import com.mthien.yumble.entity.Enum.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(mappedBy = "users")
    private Premium premium;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<UserAllergy> userAllergies;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<UserDietary> userDietaries;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Favorite> favorites;
}
