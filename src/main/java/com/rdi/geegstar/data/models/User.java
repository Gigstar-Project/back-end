package com.rdi.geegstar.data.models;


import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.enums.TalentCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profilePicture;
    private LocalDateTime dateRegistered;

    @PrePersist
    public void setDateRegistered() {
        this.dateRegistered = LocalDateTime.now();
    }
}
