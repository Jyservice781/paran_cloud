package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.entity.QBooking;
import com.paranmanzang.roomservice.model.repository.BookingCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBooking booking= QBooking.booking;


    @Override
    public Page<BookingModel> findByGroupId(Long id, Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                BookingModel.class,
                                booking.id.as("id"),
                                booking.enabled.as("enabled"),
                                booking.date.as("date"),
                                booking.times.as("times"),
                                booking.room.id.as("roomId"),
                                booking.groupId.as("groupId")
                        )
                )
                .from(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.groupId.eq(id))
                                .limit( pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<BookingModel> findByRoomId(Long id, Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                BookingModel.class,
                                booking.id.as("id"),
                                booking.enabled.as("enabled"),
                                booking.date.as("date"),
                                booking.times.as("times"),
                                booking.room.id.as("roomId"),
                                booking.groupId.as("groupId")
                        )
                )
                .from(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.room.id.eq(id))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }
}
