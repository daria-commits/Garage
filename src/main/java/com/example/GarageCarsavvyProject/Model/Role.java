package com.example.GarageCarsavvyProject.Model;

import com.example.GarageCarsavvyProject.Enums.TypeDeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeDeRole libelle;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public TypeDeRole getLibelle() {
        return libelle;
    }


    public void setLibelle(TypeDeRole libelle) {
        this.libelle = libelle;
    }


}
