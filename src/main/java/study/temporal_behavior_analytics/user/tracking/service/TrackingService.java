package study.temporal_behavior_analytics.user.tracking.service;

import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotVo;

import java.time.LocalDateTime;
import java.util.List;

public interface TrackingService {


    /**
     * @return
     */
    List<DailyStatVo> getStats(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * @return
     */
    List<TimeSlotVo> getTimeSlotStats(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * @return
     */
    Long countVoteUsers();
}
