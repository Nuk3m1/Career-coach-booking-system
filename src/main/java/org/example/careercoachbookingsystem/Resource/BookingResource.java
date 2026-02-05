package org.example.careercoachbookingsystem.Resource;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.careercoachbookingsystem.Enum.BookingStatus;
import org.example.careercoachbookingsystem.entity.tables.pojos.Booking;
import org.example.careercoachbookingsystem.entity.tables.records.BookingRecord;
import org.example.careercoachbookingsystem.repository.BookingRepository;
import org.example.careercoachbookingsystem.request.BookingIdRequest;
import org.example.careercoachbookingsystem.request.BookingWebHookRequest;
import org.example.careercoachbookingsystem.request.UserIdRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenhua
 * @version 2026年02月05日  18时59分
 * @Description:
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class BookingResource {
    private final BookingRepository bookingRepository;
    /**
     * 功能 A: "去预约" 列表 (Booking API)
     * 接口地址: POST /api/booking-url
     */
    @PostMapping("/booking-url")
    public String getBookingUrl(@RequestBody UserIdRequest request) {
        log.info("Request booking URL for userId: {}", request.getUserId());
        return bookingRepository.getBookingLink(request.getUserId());
    }

    /**
     * 功能 B: "我的预约" 列表 (My Bookings API)
     * 接口地址: GET /api/bookings
     */
    @GetMapping("/bookings")
    public List<Booking> getMyBookings(@RequestBody UserIdRequest request) {
        log.info("Fetching bookings for userId: {}", request.getUserId());
        return bookingRepository.findMyBookings(request.getUserId());
    }

    /**
     * 功能 C: "去取消预约" (Cancel API)
     * 接口地址: POST /api/bookings/cancel
     */
    @PostMapping("/bookings/cancel")
    public String getCancelUrl(@RequestBody BookingIdRequest request) {
        log.info("Request cancel URL for bookingId: {}", request.getBookingId());
        return bookingRepository.getCancelLink(request.getBookingId());
    }

    /**
     * 功能 D: "Webhook预约回调" (WebHook API)
     * 接口地址: POST /api/webhook/cal
     */
    @PostMapping("/webhook/cal")
    public int handleCalWebhook(@RequestBody BookingWebHookRequest webHookRequest) {

        JSONObject json = JSONUtil.parseObj(webHookRequest);
        String eventType = json.getStr("triggerEvent");

        if (BookingStatus.BOOKING_CREATED.name().equals(eventType)) {
            JSONObject payload = json.getJSONObject("payload");
            JSONObject organizer = payload.getJSONObject("organizer");

            BookingRecord record = new BookingRecord();
            record.setBookingId(payload.getStr("bookingId"));
            record.setCoachName(organizer.getStr("name"));
            record.setCoachEmail(organizer.getStr("email"));
            record.setStartTime(LocalDateTime.parse(payload.getStr("startTime")));
            record.setEndTime(LocalDateTime.parse(payload.getStr("endTime")));
            // 此处配合鉴权系统获取当前用户上下文UserContext ，填入即可
            // 配合测试，写死为 001
            record.setUserId("001");
            record.setStatus(BookingStatus.BOOKING_CREATED.name());
            return bookingRepository.upsertBookingFromWebhook(record);
        } else {
            return -1;
        }
    }
}
