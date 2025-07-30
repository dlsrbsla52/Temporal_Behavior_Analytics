package study.temporal_behavior_analytics.user.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.repository.BehaviorTimeFilterRepository;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotStatVo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final BehaviorTimeFilterRepository behaviorTimeFilterRepository;


    /**
     * @param period 
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ApiResultResponse<List<DailyStatVo>> getWeeklyStats(String period) {
        try {
            LocalDateTime startDate = LocalDateTime.now().minusDays(7); // 예시
            LocalDateTime endDate = LocalDateTime.now();

            List<DailyStatVo> result = behaviorTimeFilterRepository.findWeeklyActionStats(startDate, endDate);

            return ApiResultResponse.success(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param period 
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ApiResultResponse<List<TimeSlotStatVo>> getTimeSlotStats(String period) {
        try {
            LocalDateTime startDate = LocalDateTime.now().minusMonths(6);
            LocalDateTime endDate = LocalDateTime.now();

            List<TimeSlotStatVo> result = behaviorTimeFilterRepository.findTimeSlotActionStats(startDate, endDate, period);

            return ApiResultResponse.success(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 
     */
    @Override
    @Transactional(readOnly = true)
    public ApiResultResponse<Long> countVoteUsers() {
        try {
            LocalDateTime actionPeriodStart = LocalDateTime.now().minusMonths(6);
            LocalDateTime lastActiveStart = LocalDateTime.now().minusMonths(3);

            Long userCount = behaviorTimeFilterRepository.countFilteredActiveUsers("vote", actionPeriodStart, lastActiveStart);

            return ApiResultResponse.success(userCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
