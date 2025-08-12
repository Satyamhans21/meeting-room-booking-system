package com.gspann.meetingroombooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoom {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private String name;
private String location;
private  int capacity;

@OneToMany(mappedBy = "meetingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

}
