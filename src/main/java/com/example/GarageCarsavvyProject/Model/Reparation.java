package com.example.GarageCarsavvyProject.Model;

import com.example.GarageCarsavvyProject.Enums.TypeReparation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Reparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_reparation", nullable = false)
    private TypeReparation typeReparation;

    private Long duree;
    private String description;
}

