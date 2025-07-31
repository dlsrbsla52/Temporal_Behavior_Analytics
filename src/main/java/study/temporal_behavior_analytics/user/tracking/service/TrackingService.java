package study.temporal_behavior_analytics.user.tracking.service;

import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotStatVo;

import java.util.List;

public interface TrackingService {


    /**
     * @return
     */
    List<DailyStatVo> getWeeklyStats();

    /**
     * @return
     */
    List<TimeSlotStatVo> getTimeSlotStats();

    /**
     * @return
     */
    Long countVoteUsers();
}
