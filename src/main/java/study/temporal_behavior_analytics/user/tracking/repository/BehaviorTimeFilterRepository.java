package study.temporal_behavior_analytics.user.tracking.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import study.temporal_behavior_analytics.user.tracking.entity.ActionHistory;
import study.temporal_behavior_analytics.user.tracking.entity.QActionHistory;
import study.temporal_behavior_analytics.user.tracking.entity.QUserLastAction;

@Repository
public class BehaviorTimeFilterRepository extends QuerydslRepositorySupport {
    
    private final QActionHistory actionHistory = QActionHistory.actionHistory;
    private final QUserLastAction userLastAction = QUserLastAction.userLastAction;
    
    public BehaviorTimeFilterRepository() {
        super(ActionHistory.class);
    }
    
    
    
}
