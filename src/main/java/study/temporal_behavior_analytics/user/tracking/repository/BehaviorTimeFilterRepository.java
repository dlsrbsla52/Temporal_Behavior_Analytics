package study.temporal_behavior_analytics.user.tracking.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.temporal_behavior_analytics.user.tracking.code.TimeSlot;
import study.temporal_behavior_analytics.user.tracking.vo.ActVo;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static study.temporal_behavior_analytics.user.tracking.entity.QActionHistory.actionHistory;


@Repository
@RequiredArgsConstructor
public class BehaviorTimeFilterRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<ActiveUserVo> findActiveUsersWau() {
        Map<Long, List<ActVo>> transform = jpaQueryFactory
                .from(actionHistory)
                // 여기에 where 조건 등을 추가할 수 있습니다.
                .transform(groupBy(actionHistory.userId).as(list(
                        Projections.constructor(ActVo.class,
                                actionHistory.actionType,
                                actionHistory.actionTarget,
                                actionHistory.actionCount,
                                actionHistory.actionValue,
                                // ActVo의 timeSlot 필드는 ActionHistory 엔티티에 없으므로
                                // 여기서는 매핑에서 제외하거나, 엔티티에 추가해야 합니다.
                                // 우선 null로 채우기 위해 Expressions.nullExpression() 사용 가능
                                Expressions.nullExpression(TimeSlot.class)
                        )
                )));

        return transform.entrySet().stream()
                .map(entry -> {
                    ActiveUserVo vo = new ActiveUserVo();
                    vo.setUserId(entry.getKey());
                    vo.setActVo(entry.getValue());
                    return vo;
                })
                .collect(Collectors.toList());
    }


}
