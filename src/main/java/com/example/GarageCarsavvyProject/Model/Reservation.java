package com.example.GarageCarsavvyProject.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plaqueImmatriculation;
    private Long duree;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Reparation reparation;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @Setter
    @Getter
    private String etatReservation;

}
