package org.example.careercoachbookingsystem.repository;

/**
 * @author chenhua
 * @version 2026年02月05日  18时50分
 * @Description:
 */

import org.example.careercoachbookingsystem.entity.tables.pojos.Booking;
import org.example.careercoachbookingsystem.entity.tables.records.BookingRecord;

import java.util.List;

/**
 * 预约领域仓库接口
 */
public interface BookingRepository {

    /**
     * 功能 A: 获取去预约的 URL
     * @param userId 用户 ID
     * @return 导师的预约链接
     */
    String getBookingLink(String userId);

    /**
     * 功能 B: 查询“我的预约”列表
     * @param userId 用户 ID
     * @return 包含预约状态、导师名称、预约时间的列表
     */
    List<Booking> findMyBookings(String userId);

    /**
     * 功能 C: 获取取消预约的 URL
     * @param bookingId 预约唯一 ID
     * @return 用于取消预约的 URL
     */
    String getCancelLink(String bookingId);

    /**
     * 功能 D: WebHook预约回调
     *
     * @param record 上层封装好的数据库记录
     */
    int upsertBookingFromWebhook(BookingRecord record);
}
