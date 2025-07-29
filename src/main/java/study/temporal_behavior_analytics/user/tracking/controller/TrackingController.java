package study.temporal_behavior_analytics.user.tracking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.service.TrackingService;
import study.temporal_behavior_analytics.user.tracking.vo.ActiveUserVo;

import java.util.List;

@Schema(name = "트래킹 API", description = "트래킹 요청 API")
@RestController("/")
@RequiredArgsConstructor
public class TrackingController {
    
    private final TrackingService trackingService;
    
    @Operation(summary = "요일별 사용자 행동 통계", description = "주간 활성 이용자")
    @GetMapping(value = "/activeUsers/wau")
    public ApiResultResponse<List<ActiveUserVo>> wau() {
        return trackingService.trackingByWau();
    }

    @Operation(summary = "요일별 사용자 행동 통계", description = "주간 활성 이용자 날짜 기준 데이터 집계")
    @GetMapping(value = "/activeUsers/wau/date/{startDay}")
    public ApiResultResponse<List<ActiveUserVo>> wauByDate(
            @PathVariable(name = "startDay") String startDay
    ) {
        return trackingService.trackingByWau();
    }

    @Operation(summary = "요일별 사용자 행동 통계", description = "주간 활성 이용자 날짜 기준 데이터 집계")
    @GetMapping(value = "/activeUsers/wau/date/{startDate}/{endDate}")
    public ApiResultResponse<List<ActiveUserVo>> wauByDateRange(
            @PathVariable(name = "startDate") String startDay,
            @PathVariable(name = "endDate") String endDate
    ) {
        return trackingService.trackingByWau();
    }
    
    @Operation(summary = "요일별 사용자 행동 통계", description = "주간 활성 이용자")
    @GetMapping(value = "/activeUsers/wau/{userId}")
    public ApiResultResponse<List<ActiveUserVo>> wauByUserId(
            @PathVariable(name = "userId") Long userId
    ) {
        return trackingService.trackingByWau();
    }
    
}
