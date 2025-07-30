package study.temporal_behavior_analytics.user.tracking.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.temporal_behavior_analytics.user.tracking.code.TimeSlot;
import study.temporal_behavior_analytics.user.tracking.vo.ActVo;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static study.temporal_behavior_analytics.user.tracking.entity.QActionHistory.actionHistory;
import static study.temporal_behavior_analytics.user.tracking.entity.QUserLastAction.userLastAction;


@Repository
@RequiredArgsConstructor
public class BehaviorTimeFilterRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ActiveUserVo> findActiveUsersWauRowData() {
        // 1. fetch()를 사용하여 데이터를 먼저 조회합니다.
        List<Tuple> results = jpaQueryFactory
                .select(
                        actionHistory.userId,
                        actionHistory.actionType,
                        actionHistory.actionTarget,
                        actionHistory.actionCount,
                        actionHistory.actionValue,
                        actionHistory.actionTime
                )
                .from(actionHistory)
                .innerJoin(userLastAction).on(actionHistory.userId.eq(userLastAction.userId).and(actionHistory.actionTime.after(LocalDateTime.now().minusDays(7))))
                .where(actionHistory.actionTime.between(LocalDateTime.now().minusDays(7), LocalDateTime.now()))
//                .limit(1000)
                .fetch();

        // 2. Java Stream API를 사용하여 메모리에서 그룹화 및 변환 작업을 수행합니다.
        Map<Long, List<ActVo>> groupedByUserId = results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(actionHistory.userId), // userId로 그룹화
                        Collectors.mapping(
                                tuple -> new ActVo( // 각 Tuple을 ActVo 객체로 매핑
                                        tuple.get(actionHistory.actionType),
                                        tuple.get(actionHistory.actionTarget),
                                        tuple.get(actionHistory.actionCount),
                                        tuple.get(actionHistory.actionValue),
                                        tuple.get(actionHistory.actionTime)
                                ),
                                Collectors.toList() // ActVo 객체들을 리스트로 수집
                        )
                ));

        // 3. 그룹화된 맵을 최종 결과 형태인 List<ActiveUserVo>로 변환합니다.
        return groupedByUserId.entrySet().stream()
                .map(entry -> {
                    ActiveUserVo vo = new ActiveUserVo();
                    vo.setUserId(entry.getKey());
                    vo.setActVo(entry.getValue());
                    return vo;
                })
                .collect(Collectors.toList());
    }



}
