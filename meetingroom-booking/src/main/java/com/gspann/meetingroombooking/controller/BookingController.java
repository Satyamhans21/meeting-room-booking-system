package com.gspann.meetingroombooking.controller;

import com.gspann.meetingroombooking.dto.BookingDetailsResponse;
import com.gspann.meetingroombooking.dto.BookingRequest;
import com.gspann.meetingroombooking.dto.BookingResponse;
import com.gspann.meetingroombooking.dto.MeetingRoomResponse;
import com.gspann.meetingroombooking.entity.Booking;
import com.gspann.meetingroombooking.repository.BookingRepository;
import com.gspann.meetingroombooking.security.JWTTokenProvider;
import com.gspann.meetingroombooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request,@RequestHeader("Authorization") String authHeader) {
        String token=authHeader.replace("Bearer ","");
        Long userIdFromToken= jwtTokenProvider.getUserIdFromToken(token);
        BookingResponse createdBooking = bookingService.bookRoom(request,userIdFromToken);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDetailsResponse>> getBookingsByUser(@PathVariable Long userId){
        List<BookingDetailsResponse> bookings=bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("available-rooms")
    public ResponseEntity<List<MeetingRoomResponse>> getAvailableRooms(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                                       @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
        List<MeetingRoomResponse> availableRooms = bookingService.getAvailableRooms(startTime, endTime);
        return ResponseEntity.ok(availableRooms);
    }

    @DeleteMapping("{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId,@RequestHeader("Authorization") String authHeader){
        String token=authHeader.replace("Bearer ","");
        Long userIdFromToken= jwtTokenProvider.getUserIdFromToken(token);

        bookingService.cancelBooking(bookingId,userIdFromToken);
        return ResponseEntity.ok("Booking cancelled succesfully");
    }
}
