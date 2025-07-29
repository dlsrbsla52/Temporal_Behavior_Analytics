package study.temporal_behavior_analytics.user.tracking.service;

import org.springframework.stereotype.Service;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    @Override
    public ApiResultResponse<List<ActiveUserVo>> trackingByWau() {
        
        
        
        return null;
    }
}
