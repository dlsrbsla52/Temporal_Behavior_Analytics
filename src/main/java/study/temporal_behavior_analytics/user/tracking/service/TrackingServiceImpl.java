package study.temporal_behavior_analytics.user.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.temporal_behavior_analytics.common.exception.InvalidException;
import study.temporal_behavior_analytics.common.type.ServerCode;
import study.temporal_behavior_analytics.user.tracking.repository.BehaviorTimeFilterRepository;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotVo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {
    
    private static final String Vote = "vote";

    private final BehaviorTimeFilterRepository behaviorTimeFilterRepository;


    /**
     * 요일별 주간 활성 이용자 집계
     *
     * @return List<DailyStatVo>
     */
    @Override
    @Transactional(readOnly = true)
    public List<DailyStatVo> getStats(LocalDateTime startDate, LocalDateTime endDate) {

        filterDateBetween(startDate, endDate);

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
    public List<TimeSlotVo> getTimeSlotStats(LocalDateTime startDate, LocalDateTime endDate) {

        filterDateBetween(startDate, endDate);
        
        return behaviorTimeFilterRepository.findTimeSlotActionStats(startDate, endDate);
    }

    /**
     * 최근 6개월간 actionTarget이 vote인 사용자 중
     * 최근 3개월 이내 활동한 사용자
     * 
     * @return Long 최근 활동한 사용자수
     */
    @Override
    @Transactional(readOnly = true)
    public Long countVoteUsers() {
        LocalDateTime actionPeriodStart = LocalDateTime.now().minusMonths(6);
        LocalDateTime lastActiveStart = LocalDateTime.now().minusMonths(3);
        
        // 현재 'vote'만 출력하는 로직으로 설계되어 불변객체로 Vote를 선언 했지만 요구사항이 변경되면 파라미터로 데이터 전달필요
        return behaviorTimeFilterRepository.countFilteredActiveUsers(Vote, actionPeriodStart, lastActiveStart);
    }

    private void filterDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate.isAfter(endDate)) {
            throw new InvalidException(ServerCode.BAD_REQUEST, "종료 시간은 시작시간보다 빠를 수 없습니다.");
        }
    }
}
