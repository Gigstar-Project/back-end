package com.rdi.geegstar.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "planners")
public class Planner extends User{
    private String companyName;
}
