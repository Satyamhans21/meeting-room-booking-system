package com.gspann.meetingroombooking.repository;

import com.gspann.meetingroombooking.dto.MeetingRoomResponse;
import com.gspann.meetingroombooking.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,Long> {
    List<MeetingRoom> findByIdNotIn(List<Long> ids);
}
