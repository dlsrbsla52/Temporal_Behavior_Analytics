package study.temporal_behavior_analytics.user.tracking.service;

import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.util.List;

public interface TrackingService {
    ApiResultResponse<List<ActiveUserVo>> trackingByWau();
}
