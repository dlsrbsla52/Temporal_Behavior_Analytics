package study.temporal_behavior_analytics.user.tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(schema = "test", name = "action_history")
public class ActionHistory {

    /**
     * 행동 이력의 고유 식별자입니다.
     * @GeneratedValue는 ID 생성을 @GenericGenerator에 위임합니다.
     * @GenericGenerator는 'snowflakeIdGenerator'라는 이름의 생성기를 정의하며,
     * 실제 구현 클래스로 'SnowflakeIdGenerator'를 사용하도록 지정합니다.
     * Spring Boot 환경에서는 Hibernate가 strategy에 지정된 클래스를 Spring 컨테이너에서 찾아 사용하므로
     * 의존성 주입이 완료된 인스턴스를 사용하게 됩니다.
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
