package study.temporal_behavior_analytics.user.tracking.repository;

import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.QDailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.QTimeSlotVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotVo;

import java.time.LocalDateTime;
import java.util.List;

import static study.temporal_behavior_analytics.user.tracking.entity.QActionHistory.actionHistory;
import static study.temporal_behavior_analytics.user.tracking.entity.QUserLastAction.userLastAction;


@Repository
@RequiredArgsConstructor
public class BehaviorTimeFilterRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 기간 동안의 요일별 사용자 행동 통계를 조회
     * DB에서 직접 요일별, action_type별로 그룹화하여 집계 연산을 수행
     * 
     * @param start 조회 시작 시간
     * @param end 조회 종료 시간
     * @return 요일별 통계 데이터 리스트
     */
    public List<DailyStatVo> findWeeklyActionStats(LocalDateTime start, LocalDateTime end) {

        // postgreSQL에서 to_char(ah1_0.action_time, 'FMDay')를 통해 요일 지정을 할 수 있음
        StringTemplate dayOfWeek = Expressions.stringTemplate(
                "to_char({0}, 'FMDay')",
                actionHistory.actionTime
        );

        return jpaQueryFactory
                .select(new QDailyStatVo(
                        dayOfWeek,
                        actionHistory.actionType,
                        actionHistory.actionCount.sum().coalesce(0),
                        actionHistory.userId.countDistinct()
                ))
                .from(actionHistory)
                .where(actionHistory.actionTime.between(start, end))
                .groupBy(dayOfWeek, actionHistory.actionType)
                .orderBy(dayOfWeek.asc(), actionHistory.actionType.asc())
                .fetch();
    }

    /**
     * 특정 기간 동안의 시간대별 사용자 행동 통계를 조회
     * DB의 CASE WHEN 구문을 사용하여 시간대별로 그룹화하고 집계
     *
     * @param end 조회 종료 시간
     * @param start 조회 시작 시간
     * @return 시간대별 통계 데이터 리스트
     */
    public List<TimeSlotVo> findTimeSlotActionStats(LocalDateTime start, LocalDateTime end) {
        // CASE WHEN 구문 정의
        StringTemplate timeSlot = Expressions.stringTemplate(
                "CASE " +
                        "WHEN EXTRACT(HOUR FROM {0}) BETWEEN 0 AND 5 THEN '새벽' " +
                        "WHEN EXTRACT(HOUR FROM {0}) BETWEEN 6 AND 11 THEN '오전' " +
                        "WHEN EXTRACT(HOUR FROM {0}) BETWEEN 12 AND 17 THEN '오후' " +
                        "ELSE '야간' END",
                actionHistory.actionTime
        );

        return jpaQueryFactory
                .select(new QTimeSlotVo(
                        timeSlot, // CASE WHEN 구문 사용
                        actionHistory.actionType,
                        actionHistory.actionCount.sum().coalesce(0),
                        actionHistory.userId.countDistinct()
                ))
                .from(actionHistory)
                .where(actionHistory.actionTime.between(start, end))
                .groupBy(timeSlot, actionHistory.actionType) // 집계 조건
                .orderBy(timeSlot.asc(),
                        actionHistory.actionType.asc()
                )
                .fetch();
    }

    /**
     * 특정 타겟 액션을 수행한 활성 사용자 수를 조회
     * action_history와 user_last_action을 조인하여 여러 조건을 동시에 필터링
     * 
     * @param targetAction 조회할 액션 타겟
     * @param actionPeriodStart 액션 이력 조회 시작 시간
     * @param lastActiveStart 마지막 활동 시간 기준
     * @return 조건에 맞는 고유 사용자 수
     */
    public Long countFilteredActiveUsers(String targetAction, LocalDateTime actionPeriodStart, LocalDateTime lastActiveStart) {
        return jpaQueryFactory
                .select(actionHistory.userId.countDistinct()) // 고유 사용자 수만 카운트
                .from(actionHistory)
                // inner join을 사용하여 활성 사용자(user_last_action에 데이터가 있는)만 필터링
                .innerJoin(userLastAction).on(actionHistory.userId.eq(userLastAction.userId))
                .where(
                        actionHistory.actionTarget.eq(targetAction), // 특정 타겟 액션
                        actionHistory.actionTime.goe(actionPeriodStart), // 특정 기간 내 액션
                        userLastAction.lastActionTime.goe(lastActiveStart) // 최근 활성 사용자
                )
                .fetchOne(); // 단일 결과(count)를 가져옴
    }
}
