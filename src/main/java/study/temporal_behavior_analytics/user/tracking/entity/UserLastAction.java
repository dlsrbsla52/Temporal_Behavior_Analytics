package study.temporal_behavior_analytics.user.tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(schema = "test", name = "user_last_action")
public class UserLastAction {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @OneToMany(mappedBy = "userLastAction")
    private List<ActionHistory> actionHistoryList;
    
    @Column(name = "last_action_time")
    private LocalDateTime lastActionTime;
}
