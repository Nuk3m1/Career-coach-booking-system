CREATE TABLE `user` (
                        `user_id` VARCHAR(64) NOT NULL COMMENT '用户唯一标识',
                        `name` VARCHAR(100) NOT NULL COMMENT '用户姓名',
                        `email` VARCHAR(100) NOT NULL COMMENT '用户邮箱',
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE `booking` (
                           `booking_id` VARCHAR(64) NOT NULL COMMENT 'Cal.com 返回的预约唯一ID',
                           `user_id` VARCHAR(64) NOT NULL COMMENT '关联的用户ID',
                           `coach_name` VARCHAR(100) NOT NULL COMMENT '导师姓名',
                           `coach_email` VARCHAR(100) NOT NULL COMMENT '导师邮箱',
                           `start_time` DATETIME NOT NULL COMMENT '预约开始时间',
                           `end_time` DATETIME NOT NULL COMMENT '预约结束时间',
                           `status` VARCHAR(20) NOT NULL COMMENT '预约状态: PENDING, BOOKING_CREATED, BOOKING_CANCELLED, MEETING_ENDED, NO_SHOW',
                           `cancel_url` TEXT COMMENT '去取消预约的URL',
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`booking_id`),
                           INDEX `idx_user_id` (`user_id`),
                           INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;