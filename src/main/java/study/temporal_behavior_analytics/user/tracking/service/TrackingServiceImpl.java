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
     * 요일별 주간 활성 이용자 집계
     *
     * @return List<DailyStatVo>
     */
    @Override
    @Transactional(readOnly = true)
    public List<DailyStatVo> getWeeklyStats() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7); // 예시
        LocalDateTime endDate = LocalDateTime.now();


        return behaviorTimeFilterRepository.findWeeklyActionStats(startDate, endDate);
    }

    /**
     * 6개월 이내 활성 사용자 집계
     * 각 시간대 (새벽, 오전, 저녁, 밤)별 집계 카운트
     *
     * @return List<TimeSlotStatVo>
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotStatVo> getTimeSlotStats() {
        try {
            LocalDateTime startDate = LocalDateTime.now().minusMonths(6);
            LocalDateTime endDate = LocalDateTime.now();


            return behaviorTimeFilterRepository.findTimeSlotActionStats(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 
     */
    @Override
    @Transactional(readOnly = true)
    public Long countVoteUsers() {
        try {
            LocalDateTime actionPeriodStart = LocalDateTime.now().minusMonths(6);
            LocalDateTime lastActiveStart = LocalDateTime.now().minusMonths(3);

            Long userCount = behaviorTimeFilterRepository.countFilteredActiveUsers("vote", actionPeriodStart, lastActiveStart);

            return userCount;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
