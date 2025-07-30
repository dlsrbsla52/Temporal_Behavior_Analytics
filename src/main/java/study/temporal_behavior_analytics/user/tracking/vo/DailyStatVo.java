package study.temporal_behavior_analytics.user.tracking.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DailyStatVo {

    private String dayOfWeek; // 요일 (예: "MONDAY", "TUESDAY")
    private String actionType; // 행동 타입
    private int totalActionCount; // 행동 카운트 총합
    private long uniqueUserCount; // 고유 사용자 수

    @QueryProjection // QueryDSL 조회 결과를 이 생성자에 직접 매핑
    public DailyStatVo(String dayOfWeek, String actionType, int totalActionCount, long uniqueUserCount) {
        this.dayOfWeek = dayOfWeek;
        this.actionType = actionType;
        this.totalActionCount = totalActionCount;
        this.uniqueUserCount = uniqueUserCount;
    }
}
