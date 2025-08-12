package com.gspann.meetingroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String message;
    private Long bookingId;
}