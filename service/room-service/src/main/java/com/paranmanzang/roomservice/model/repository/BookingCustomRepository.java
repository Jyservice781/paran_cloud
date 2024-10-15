package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingCustomRepository {
    Page<BookingModel> findByGroupId(Long id, Pageable pageable);
    Page<BookingModel> findByRoomId( Long id, Pageable pageable);
}
