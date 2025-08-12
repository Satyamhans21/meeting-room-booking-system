package com.gspann.meetingroombooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeetingRoomResponse {
    private Long id;
    private String name;
    private String location;
    private int capacity;
}
