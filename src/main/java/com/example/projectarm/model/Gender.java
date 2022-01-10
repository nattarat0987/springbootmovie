package com.example.projectarm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    @Column(name = "gender_id")
    private Integer id;

    private String  feature;
}
