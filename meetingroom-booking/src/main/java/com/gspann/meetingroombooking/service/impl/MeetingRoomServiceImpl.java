package com.gspann.meetingroombooking.service.impl;

import com.gspann.meetingroombooking.entity.MeetingRoom;
import com.gspann.meetingroombooking.repository.MeetingRoomRepository;
import com.gspann.meetingroombooking.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Override
    public List<MeetingRoom> getAllRooms() {

        return meetingRoomRepository.findAll();

    }

    @Override
    public MeetingRoom getRoomById(Long id) {
        return meetingRoomRepository.findById(id).orElseThrow(()->new RuntimeException("Room not found with id: "+id));
    }

    @Override
    public MeetingRoom createRoom(MeetingRoom room) {
        return meetingRoomRepository.save(room);
    }

    @Override
    public MeetingRoom updateRoom(Long id, MeetingRoom room) {
        MeetingRoom existingRoom= getRoomById(id);
        existingRoom.setName(room.getName());
        existingRoom.setLocation(room.getLocation());
        existingRoom.setCapacity(room.getCapacity());
        return meetingRoomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        meetingRoomRepository.deleteById(id);

    }
}
