package com.gspann.meetingroombooking.controller;

import com.gspann.meetingroombooking.dto.MeetingRoomResponse;
import com.gspann.meetingroombooking.entity.MeetingRoom;
import com.gspann.meetingroombooking.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class MeetingRoomController {
    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping
    public ResponseEntity< List<MeetingRoomResponse>> getAllRooms(){
        List<MeetingRoom> rooms =meetingRoomService.getAllRooms();
        List<MeetingRoomResponse> responses = rooms.stream()
                .map(room -> MeetingRoomResponse.builder()
                        .id(room.getId())
                        .name(room.getName())
                        .location(room.getLocation())
                        .capacity(room.getCapacity())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
    @GetMapping("/{id}")
    public MeetingRoom getRoomById(@PathVariable Long id){
        return meetingRoomService.getRoomById(id);
    }
    @PostMapping
    public MeetingRoom createRoom(@RequestBody MeetingRoom room){
        return meetingRoomService.createRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingRoomResponse> updateRoom(@PathVariable Long id, @RequestBody MeetingRoom updatedRoomData){
        MeetingRoom updatedRoom=meetingRoomService.updateRoom(id, updatedRoomData);
        MeetingRoomResponse response = MeetingRoomResponse.builder()
                .id(updatedRoom.getId())
                .name(updatedRoom.getName())
                .location(updatedRoom.getLocation())
                .capacity(updatedRoom.getCapacity())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id){
        meetingRoomService.deleteRoom(id);
    }


}
