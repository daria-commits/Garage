package com.example.GarageCarsavvyProject.Service;

import com.example.GarageCarsavvyProject.Model.Reparation;
import com.example.GarageCarsavvyProject.Model.Reservation;
import com.example.GarageCarsavvyProject.Model.Utilisateur;
import com.example.GarageCarsavvyProject.Repository.ReservationRepository;
import com.example.GarageCarsavvyProject.Repository.UtilisateurRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ReservationService(ReservationRepository reservationRepository, UtilisateurRepository utilisateurRepository) {
        this.reservationRepository = reservationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public void takeReservation(Reservation reservation, int utilisateurId) throws Exception {

        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById((long) utilisateurId);
        Utilisateur utilisateur = utilisateurOptional.orElseThrow(() -> new Exception("Utilisateur introuvable pour l'ID fourni."));
        LocalDateTime startDate = reservation.getStartDate();
        if (startDate == null) {
            throw new IllegalArgumentException("La date de début doit être spécifiée.");
        }

        Reparation reparation = reservation.getReparation();
        if (reparation == null) {
            throw new IllegalArgumentException("La réparation doit être spécifiée.");
        }

        List<Reservation> existingReservations = reservationRepository.findByReparationAndEndDateAfter(reparation, LocalDateTime.now());
        if (existingReservations.size() >= 2) {
            throw new IllegalStateException("Deux réservations actives existent déjà pour ce type de réparation.");
        }

        // Расчет даты окончания резервации
        if(reparation.getDuree() != null){
            Long selectedDuree = reparation.getDuree();
            LocalDateTime endDate = startDate.plusMinutes(selectedDuree);
            reservation.setEndDate(endDate);
        }
        reservation.setUtilisateur(utilisateur);

        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Long userId, Long reservationId, Reservation reservationModifiee) {

        return null;
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    @Scheduled(fixedRate = 60000)
    public void updateReservationStatus() {

        List<Reservation> reservations = reservationRepository.findByEndDateBeforeAndEtatReservationNot(LocalDateTime.now(), "passé");
        for (Reservation entretien : reservations) {
            entretien.setEtatReservation("passé");
            reservationRepository.save(entretien);
        }

        List<Reservation> reservationsActives = reservationRepository.findByEndDateAfterAndEtatReservationNot(LocalDateTime.now(), "actif");
        for (Reservation reservation : reservationsActives) {
            reservation.setEtatReservation("actif");
            reservationRepository.save(reservation);
        }
    }
}
