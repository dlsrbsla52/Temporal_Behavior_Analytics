package study.temporal_behavior_analytics.user.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.temporal_behavior_analytics.user.tracking.entity.UserLastAction;

@Repository
public interface UserLastActionRepository extends JpaRepository<UserLastAction, Long> {
}
