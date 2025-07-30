package study.temporal_behavior_analytics.user.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.temporal_behavior_analytics.common.code.ServerCode;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.repository.BehaviorTimeFilterRepository;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final BehaviorTimeFilterRepository behaviorTimeFilterRepository;

    @Override
    public ApiResultResponse<List<ActiveUserVo>> trackingByWau() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResultResponse<List<ActiveUserVo>> trackingByWauRowData() {

        try {
            long startTime = System.currentTimeMillis();

            List<ActiveUserVo> resultResponse = behaviorTimeFilterRepository.findActiveUsersWauRowData();
            long endTime = System.currentTimeMillis();
            System.out.println("작업 시간 : " + (endTime - startTime) + "ms");

            return ApiResultResponse.success(resultResponse);
        } catch (Exception e) {
            return ApiResultResponse.ofResponse(ServerCode.INTERNAL_SERVER_ERROR);
        }
    }
}
