package study.temporal_behavior_analytics.user.tracking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.service.TrackingService;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

@Schema(name = "트래킹 API", description = "트래킹 요청 API")
@RestController("/")
@RequiredArgsConstructor
public class TrackingController {
    
    private final TrackingService trackingService;
    
    @Operation(summary = "요일별 사용자 행동 통계", description = "주간 활성 이용자")
    @GetMapping(value = "/activeUsers/wau")
    public ApiResultResponse<ActiveUserVo> activeUsers() {
        return trackingService.trackingByWau();
    }
    
}
