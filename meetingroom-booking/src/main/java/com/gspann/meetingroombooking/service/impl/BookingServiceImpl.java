package com.gspann.meetingroombooking.service.impl;

import com.gspann.meetingroombooking.dto.BookingDetailsResponse;
import com.gspann.meetingroombooking.dto.BookingRequest;
import com.gspann.meetingroombooking.dto.BookingResponse;
import com.gspann.meetingroombooking.dto.MeetingRoomResponse;
import com.gspann.meetingroombooking.entity.Booking;
import com.gspann.meetingroombooking.entity.MeetingRoom;
import com.gspann.meetingroombooking.entity.User;
import com.gspann.meetingroombooking.exception.BookingConflictException;
import com.gspann.meetingroombooking.repository.BookingRepository;
import com.gspann.meetingroombooking.repository.MeetingRoomRepository;
import com.gspann.meetingroombooking.repository.UserRepository;
import com.gspann.meetingroombooking.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private final UserRepository userRepository;
    @Override
    @Transactional
    public BookingResponse bookRoom(BookingRequest request,Long userIdFromToken) {
        if (request.getRoomId() == null) {
            throw new IllegalArgumentException("Meeting Room ID must not be null");
        }
        MeetingRoom room=meetingRoomRepository.findById(request.getRoomId()).orElseThrow(()->new RuntimeException("Meeting Room not found"));
        User user = userRepository.findById(userIdFromToken)
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateBooking(request.getStartTime(),request.getEndTime());

        // 🔴 Check for overlapping bookings
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                request.getRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (!overlapping.isEmpty()) {
            throw new BookingConflictException(
                    "This meeting room is already booked between " +
                            request.getStartTime() + " and " + request.getEndTime()
            );
        }

        Booking booking=Booking.builder()
                .meetingRoom(room)
                .user(user)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .purpose(request.getPurpose())
                .build();
        bookingRepository.save(booking);

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .message("Booking confirmed!")
                .build();
    }

    @Override
    public List<BookingDetailsResponse> getBookingsByUser(Long userId) {
        List<Booking> bookings=bookingRepository.findByUserId(userId);
        return bookings.stream().map(this:: mapToDetailsResponse).collect(Collectors.toList());

    }

    @Override
    public List<MeetingRoomResponse> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        validateBooking(startTime,endTime);
        List<Long> bookedRoomIds=bookingRepository.findBookedRoomIdsBetween(startTime,endTime);
        List<MeetingRoom> rooms;
        if(bookedRoomIds.isEmpty()){
            rooms= meetingRoomRepository.findAll();
        }
        else{
            rooms= meetingRoomRepository.findByIdNotIn(bookedRoomIds);
        }

        return rooms.stream()
                .map(room->MeetingRoomResponse.builder()
                        .id(room.getId())
                        .name(room.getName())
                        .location(room.getLocation())
                        .capacity(room.getCapacity())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId, Long userIdFromToken) {
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found"));
        if(!booking.getUser().getId().equals(userIdFromToken)){
            throw new AccessDeniedException("You are not allowed to cancel this booking");

        }
        bookingRepository.delete(booking);

    }
    public void validateBooking(LocalDateTime startTime,LocalDateTime endTime){
        if(startTime.isBefore(LocalDateTime.now())){
            throw new RuntimeException("Start time cannot be in past");
        }
        if(!endTime.isAfter(startTime)){
            throw new RuntimeException("End time cannot be before start time");
        }
        if(!startTime.toLocalDate().equals(endTime.toLocalDate())){
            throw new RuntimeException("booking must start and end on same day");
        }
        long durationMinutes= Duration.between(startTime,endTime).toMinutes();
        if(durationMinutes>480){
            throw new RuntimeException("booking cannot exceed 8 hours");
        }
    }

    private BookingDetailsResponse mapToDetailsResponse(Booking booking) {
        return BookingDetailsResponse.builder()
                .bookingId(booking.getId())
                .roomId(booking.getMeetingRoom().getId())
                .roomName(booking.getMeetingRoom().getName())           // Set name
                .roomLocation(booking.getMeetingRoom().getLocation())
                .userId(booking.getUser().getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .purpose(booking.getPurpose())
                .build();
    }


}
