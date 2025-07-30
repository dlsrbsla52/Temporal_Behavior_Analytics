package study.temporal_behavior_analytics.user.tracking.service;

import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.util.List;

public interface TrackingService {


    /**
     * 오늘 날짜를 기준으로 통계 데이터의 주간 정보를 확인한다.
     *
     * @return List<ActiveUserVo>
     */
    ApiResultResponse<List<ActiveUserVo>> trackingByWau();

    /**
     * 오늘 날짜를 기준으로 통계 데이터의 주간 row 데이터를 확인 한다.
     * @return
     */
    ApiResultResponse<List<ActiveUserVo>> trackingByWauRowData();
}
