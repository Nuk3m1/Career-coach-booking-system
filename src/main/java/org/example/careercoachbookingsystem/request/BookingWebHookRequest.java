package org.example.careercoachbookingsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author chenhua
 * @version 2026年02月05日  21时34分
 * @Description:
 */
@Data
@Accessors(chain = true)
public class BookingWebHookRequest {
    private String triggerEvent;
    private LocalDateTime createdAt;
    private PayLoad payload;
    @Data
    @Accessors(chain = true)
    public static class PayLoad {
        private String bookerUrl;
        private String title;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Organizer organizer;
        private String bookingId;
    }
    //"id": 1,
    //      "name": "Organizer Name",
    //      "email": "organizer@example.com",
    //      "username": "organizer-handle",
    //      "usernameInOrg": "organizer",
    //      "timeZone": "UTC",
    //      "language": {
    //        "locale": "en"
    //      },
    //      "timeFormat": "h:mma",
    //      "utcOffset": 0
    @Data
    @Accessors(chain = true)
    public static class Organizer {
        @JsonProperty("id")
        private String organizerId;
        private String name;
        private String email;
        private String username;
        private String usernameInOrg;
        private String timeZone;
        private Language language;
    }
    @Data
    @Accessors(chain = true)
    public static class Language {
        private String locale;
    }
}
