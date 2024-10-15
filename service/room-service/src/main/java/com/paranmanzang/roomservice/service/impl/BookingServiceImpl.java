package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.entity.Booking;
import com.paranmanzang.roomservice.model.entity.Time;
import com.paranmanzang.roomservice.model.repository.BookingRepository;
import com.paranmanzang.roomservice.model.repository.RoomRepository;
import com.paranmanzang.roomservice.service.BookingService;
import com.paranmanzang.roomservice.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final TimeServiceImpl timeService;
    private final AccountServiceImpl accountService;
    private final Converter converter;

    @Override
    public BookingModel insert(BookingModel model) {
        return Optional.of(bookingRepository.save(Booking.builder()
                        .date(model.getDate())
                        .groupId(model.getGroupId())
                        .room(roomRepository.findById(model.getRoomId()).get())
                        .build()))
                .map(saveBooking -> {
                    model.setId(saveBooking.getId());
                    timeService.saveBooking(model);
                    return saveBooking;
                }).map(converter::convertToBookingModel).get();

    }

    @Override
    public Boolean delete(Long id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    BookingModel bookingModel = converter.convertToBookingModel(booking);
                    bookingRepository.delete(booking);
                    return timeService.saveBooking(bookingModel) && accountService.cancel(id);
                })
                .orElse(false);
    }

    @Override
    public Page<?> findByGroup(long groupId, Pageable pageable) {
        return bookingRepository.findByGroupId(groupId, pageable);
    }

    @Override
    public Page<?> findByRoom(long roomId, Pageable pageable) {
        return bookingRepository.findByRoomId(roomId, pageable);
    }

    @Override
    public BookingModel findOne(long id) {
        return bookingRepository.findById(id).map(booking ->
                new BookingModel(booking.getId(), booking.isEnabled(), booking.getDate(), booking.getTimes().stream().map(Time::getTime).toList(), booking.getRoom().getId(), booking.getGroupId())
        ).orElse(null);
    }


    @Override
    public BookingModel updateState(Long id) {
        if (bookingRepository.findById(id).get().isEnabled()) return null;

        return bookingRepository.findById(id).map(booking -> {
                    booking.setEnabled(true);
                    booking.setResponseAt(LocalDateTime.now());
                    return bookingRepository.save(booking);
                })
                .map(converter::convertToBookingModel).get();
    }

}
