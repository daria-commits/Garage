package com.example.GarageCarsavvyProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean desactive;
    private boolean expire;
    private String valeur;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;


    public int getId() {
        return id;
    }

    public boolean isDesactive() {
        return desactive;
    }

    public boolean isExpire() {
        return expire;
    }

    public String getValeur() {
        return valeur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDesactive(boolean desactive) {
        this.desactive = desactive;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
