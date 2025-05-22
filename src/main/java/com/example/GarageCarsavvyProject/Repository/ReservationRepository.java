package com.example.GarageCarsavvyProject.Repository;

import com.example.GarageCarsavvyProject.Model.Reparation;
import com.example.GarageCarsavvyProject.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReparationAndEndDateAfter(Reparation reparation, LocalDateTime currentDateTime);

    List<Reservation> findByEndDateBeforeAndEtatReservationNot(LocalDateTime endDate, String etatReservation);

    List<Reservation> findByEndDateAfterAndEtatReservationNot(LocalDateTime endDate, String etatReservation);

}
