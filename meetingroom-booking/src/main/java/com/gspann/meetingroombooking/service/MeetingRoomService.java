package com.gspann.meetingroombooking.service;

import com.gspann.meetingroombooking.entity.MeetingRoom;

import java.util.List;

public interface MeetingRoomService {
    List<MeetingRoom> getAllRooms();
    MeetingRoom getRoomById(Long id);
    MeetingRoom createRoom(MeetingRoom room);
    MeetingRoom updateRoom(Long id, MeetingRoom room);
    void deleteRoom(Long id);
}
