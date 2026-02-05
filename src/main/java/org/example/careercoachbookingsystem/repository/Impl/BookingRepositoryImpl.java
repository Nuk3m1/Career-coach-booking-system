package org.example.careercoachbookingsystem.repository.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.careercoachbookingsystem.entity.tables.records.BookingRecord;
import org.example.careercoachbookingsystem.repository.BookingRepository;
import org.jooq.DSLContext;
import org.example.careercoachbookingsystem.entity.tables.pojos.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.careercoachbookingsystem.entity.Tables.BOOKING;

/**
 * @author chenhua
 * @version 2026年02月05日  18时53分
 * @Description:
 */
@RequiredArgsConstructor
@Repository
@Slf4j
public class BookingRepositoryImpl implements BookingRepository {
    private final DSLContext dsl;
    /**
     * 实现功能 A: "去预约"
     * 逻辑说明：由于与 Cal.com 解耦，此处通常是从配置或导师池中获取分配好的 Cal 链接。
     */
    @Override
    public String getBookingLink(String userId) {
        // 返回一个url(目前随意)
        return "https://cal.com/?userId=" + userId;
    }

    /**
     * 实现功能 B: "我的预约"
     * 字段要求：状态 (status), coach名称 (coach_name), 预约时间 (start_time)
     */
    @Override
    public List<Booking> findMyBookings(String userId) {
        return dsl.selectFrom(BOOKING)
                .where(BOOKING.USER_ID.eq(userId))
                .orderBy(BOOKING.START_TIME.desc())
                .fetchInto(Booking.class);
    }
    /**
     * 实现功能 C: "去取消预约"
     * 逻辑说明：从数据库中查询已存的取消链接
     */
    @Override
    public String getCancelLink(String bookingId) {
        return dsl.select(BOOKING.CANCEL_URL)
                .from(BOOKING)
                .where(BOOKING.BOOKING_ID.eq(bookingId))
                .fetchOneInto(String.class);
    }
    @Override
    public int upsertBookingFromWebhook(BookingRecord record) {
        return dsl.insertInto(BOOKING)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(BOOKING.STATUS, record.getStatus())       // 更新状态
                .set(BOOKING.START_TIME, record.getStartTime()) // 更新开始时间
                .set(BOOKING.END_TIME, record.getEndTime())     // 更新结束时间
                .set(BOOKING.COACH_NAME, record.getCoachName()) // 更新分配到的导师
                .set(BOOKING.COACH_EMAIL, record.getCoachEmail())
                .execute();

    }
}
