package com.gspann.meetingroombooking.repository;

import com.gspann.meetingroombooking.entity.Booking;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Booking b WHERE b.meetingRoom.id = :roomId AND " +
            "(:startTime < b.endTime AND :endTime > b.startTime)")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    List<Booking> findByUserId(Long userId);

    @Query("SELECT b.meetingRoom.id FROM Booking b " +
            "WHERE b.startTime < :endTime AND b.endTime > :startTime")
    List<Long> findBookedRoomIdsBetween(LocalDateTime startTime, LocalDateTime endTime);

}
