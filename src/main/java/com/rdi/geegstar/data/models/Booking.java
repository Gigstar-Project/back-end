package com.rdi.geegstar.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@Table(name = "bookings")
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> talent;
    @ManyToOne
    private User planner;
    @OneToOne
    private EventDetail eventDetail;
    private boolean isAccepted;
}
