INSERT INTO `user` (user_id, name, email)
VALUES ('001', '张三', 'zhangsan@example.com');

INSERT INTO `booking` (booking_id, user_id, coach_name, coach_email, start_time, end_time, status, cancel_url)
VALUES (
           '123',
           '001',
           '导师 A',
           'coachA@cal.com',
           '2026-02-10 10:00:00',
           '2026-02-10 10:30:00',
           'BOOKING_CREATED',
           'https://cal.com/reschedule/mock_token'
       );