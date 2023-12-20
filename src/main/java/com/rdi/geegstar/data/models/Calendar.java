package com.rdi.geegstar.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User talent;
    @OneToOne
    private Booking booking;
    private LocalDateTime eventDateAndTime;
    private LocalDateTime dateCreated;

    @PrePersist
    public void setDateRegistered() {
        this.dateCreated = LocalDateTime.now();
    }

}
