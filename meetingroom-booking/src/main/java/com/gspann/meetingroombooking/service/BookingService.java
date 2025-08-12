package com.gspann.meetingroombooking.service;

import com.gspann.meetingroombooking.dto.BookingDetailsResponse;
import com.gspann.meetingroombooking.dto.BookingRequest;
import com.gspann.meetingroombooking.dto.BookingResponse;
import com.gspann.meetingroombooking.dto.MeetingRoomResponse;
import com.gspann.meetingroombooking.entity.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingResponse bookRoom(BookingRequest request,Long userIdFromToken);
    List<BookingDetailsResponse> getBookingsByUser(Long userId);
    List<MeetingRoomResponse> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime);
    void cancelBooking(Long bookingId, Long userIdFromToken);

}
