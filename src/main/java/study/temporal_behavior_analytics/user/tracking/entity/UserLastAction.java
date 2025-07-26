package study.temporal_behavior_analytics.user.tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(schema = "test", name = "user_last_action")
public class UserLastAction {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(name = "last_action_time")
    private LocalDateTime lastActionTime;
}
