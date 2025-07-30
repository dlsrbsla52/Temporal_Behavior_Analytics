package study.temporal_behavior_analytics.user.tracking.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TimeSlotStatVo {

    private String timeSlot; // 시간대 (예: "새벽", "오전")
    private String actionType; // 행동 타입
    private int totalActionCount; // 행동 카운트 총합
    private long uniqueUserCount; // 고유 사용자 수

    @QueryProjection // QueryDSL 조회 결과를 이 생성자에 직접 매핑
    public TimeSlotStatVo(String timeSlot, String actionType, int totalActionCount, long uniqueUserCount) {
        this.timeSlot = timeSlot;
        this.actionType = actionType;
        this.totalActionCount = totalActionCount;
        this.uniqueUserCount = uniqueUserCount;
    }
}