package com.example.GarageCarsavvyProject.Controllor;

import com.example.GarageCarsavvyProject.Repository.MarqueRepository;
import com.example.GarageCarsavvyProject.Repository.ReparationRepository;
import com.example.GarageCarsavvyProject.Repository.VehiculeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rendezvous")
public class ReservationControleur {
    private final VehiculeRepository vehiculeRepository;
    private final MarqueRepository marqueRepository;
    private final ReparationRepository reparationRepository;
}
