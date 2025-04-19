package com.example.GarageCarsavvyProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "modele")
    public class Modele {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @Column(name = "modele")
        private String modele;

        @Column(name = "annee")
        private int annee;

        @ManyToOne
        @JoinColumn(name = "id_Marque")
        private Marque marque;
    }

