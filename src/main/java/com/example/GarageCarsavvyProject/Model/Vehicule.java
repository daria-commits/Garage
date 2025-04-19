package com.example.GarageCarsavvyProject.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity

public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "modele_id")
    private Modele modele;

    private String carburant;
    private int kilometrage;

    @Column(name = "annee")
    private Date annee;

    @Column(name = "plaqueImmatriculation")
    private String plaqueImmatriculation;

}
