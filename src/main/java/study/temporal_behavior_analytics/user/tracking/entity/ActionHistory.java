package study.temporal_behavior_analytics.user.tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(schema = "test", name = "action_history")
public class ActionHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
