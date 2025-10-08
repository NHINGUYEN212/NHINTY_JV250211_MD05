package com.ra.md05_session09.repository;

import com.ra.md05_session09.model.constant.Status;
import com.ra.md05_session09.model.entity.Reservation;
import com.ra.md05_session09.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByRoomsAndStatusIn(Room room, List<Status> statuses);
}
