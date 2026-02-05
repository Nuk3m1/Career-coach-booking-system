package org.example.careercoachbookingsystem.Enum;

import lombok.Getter;

@Getter
public enum BookingStatus {
    PENDING("初始状态"),
    BOOKING_CREATED("支付成功且预约确认"),
    BOOKING_CANCELLED("预约已取消"),
    MEETING_ENDED("课程正常结束"),
    NO_SHOW("用户或导师未出席");
    private String description;
    BookingStatus(String description) {this.description = description;}
}
