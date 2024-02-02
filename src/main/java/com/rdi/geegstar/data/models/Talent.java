package com.rdi.geegstar.data.models;

import com.rdi.geegstar.enums.TalentCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "talents")
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Talent extends User {
    private String bio;
    @Enumerated
    private TalentCategory talentCategory;
    @OneToOne(cascade = CascadeType.ALL)
    private Portfolio portfolio;
    private String displayName;
}
