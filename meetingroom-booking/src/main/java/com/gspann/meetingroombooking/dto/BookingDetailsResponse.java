package com.gspann.meetingroombooking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDetailsResponse {
    private Long bookingId;
    private Long roomId;
    private String roomName;
    private String roomLocation;
    private Long userId;
    private String purpose;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}