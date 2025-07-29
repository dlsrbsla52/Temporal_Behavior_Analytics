package study.temporal_behavior_analytics.user.tracking.code;

import lombok.Getter;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * 시간대를 정의하고, 특정 시간이 어느 시간대에 속하는지 판별하는 기능을 제공하는 열거형 클래스입니다.
 * 각 시간대(새벽, 오전, 오후, 야간)는 시작 시간과 종료 시간을 속성으로 가집니다.
 */
public enum TimeSlot {
    // 각 시간대별 상수 정의
    DAWN("DAWN", 0, 5),      // 새벽: 00시 ~ 05시
    MORNING("MORNING", 6, 11),   // 오전: 06시 ~ 11시
    AFTERNOON("AFTERNOON", 12, 17), // 오후: 12시 ~ 17시
    NIGHT("NIGHT", 18, 23);   // 야간: 18시 ~ 23시

    // Getter 메서드
    @Getter
    private final String description; // 시간대 설명 (예: "새벽")
    private final int startHour;      // 시작 시간 (포함)
    private final int endHour;        // 종료 시간 (포함)

    
    /**
     * 생성자
     * 
     * @param description 시간대 설명
     * @param startHour   시작 시간
     * @param endHour     종료 시간
     */
    TimeSlot(String description, int startHour, int endHour) {
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
    }


    /**
     * 시간에 따라
     *
     * @param hour 0부터 23 사이의 시간 값
     * @return 해당 시간에 맞는 TimeSlot 상수
     * @throws IllegalArgumentException 만약 유효한 시간대(0-23)에 속하지 않는 경우 발생
     */
    public static TimeSlot fromHour(int hour) {
        // 모든 TimeSlot 상수를 순회하며 적절한 시간대를 찾습니다.
        return Arrays.stream(values())
                .filter(slot -> hour >= slot.startHour && hour <= slot.endHour)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 시간입니다: " + hour));
    }

    /**
     * 현재 시간을 기준으로 해당하는 TimeSlot을 반환합니다.
     * 내부적으로 LocalTime.now()를 사용합니다.
     *
     * @return 현재 시간에 맞는 TimeSlot 상수
     */
    public static TimeSlot now() {
        int currentHour = LocalTime.now().getHour();
        return fromHour(currentHour);
    }
}
