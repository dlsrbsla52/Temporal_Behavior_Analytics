package study.temporal_behavior_analytics.user.tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(schema = "test", name = "action_history")
public class ActionHistory {

    /**
     * action_history 테이블 PK.
     * @GeneratedValue는 ID 생성을 @GenericGenerator에 위임 처리
     * @GenericGenerator는 'snowflakeIdGenerator'라는 이름의 생성기를 정의하면서
     * 실제 구현 클래스로 'SnowflakeIdGenerator'를 사용하도록 지정 하는 설정
     */
    @Id
    @GeneratedValue(generator = "snowflakeIdGenerator")
    @GenericGenerator(
            name = "snowflakeIdGenerator",
            strategy = "study.temporal_behavior_analytics.common.config.SnowflakeIdGenerator"
    )
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "action_type", length = 50)
    private String actionType;
    
    @Column(name = "action_target", length = 100)
    private String actionTarget;
    
    @Column(name = "action_count")
    private int actionCount;
    
    @Column(name = "action_value")
    private Double actionValue;
    
    @Column(name = "action_time")
    private LocalDateTime actionTime;

    /**
     * 연관관계 매핑 N:1 의존 관계를 가지고 있음
     * 확실하지 않지만 테이블의 구조만 확인한다면 user_last_action 테이블에
     * postgresql의 upsert로 구현해서 데이터를 저장해두고
     * 상세 기록은 action_history 테이블이 처리하는 것으로 예상하여 연관관계 맵핑
     * 
     * 실제 Row 데이터의 처리 보단 read시 연관관계를 활용할 계획으로 데이터 처리시 insert, update를 처리하지 않게 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserLastAction userLastAction;


    public ActionHistory(Long userId, String actionType, String actionTarget, int actionCount, Double actionValue, LocalDateTime actionTime) {
        this.userId = userId;
        this.actionType = actionType;
        this.actionTarget = actionTarget;
        this.actionCount = actionCount;
        this.actionValue = actionValue;
        this.actionTime = actionTime;
    }
}
