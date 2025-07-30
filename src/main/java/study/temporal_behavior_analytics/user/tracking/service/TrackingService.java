package study.temporal_behavior_analytics.user.tracking.service;

import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotStatVo;

import java.util.List;

public interface TrackingService {


    ApiResultResponse<List<DailyStatVo>> getWeeklyStats(String period);
    ApiResultResponse<List<TimeSlotStatVo>> getTimeSlotStats(String period);
    ApiResultResponse<Long> countVoteUsers();
}
