package study.temporal_behavior_analytics.user.tracking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;
import study.temporal_behavior_analytics.user.tracking.service.TrackingService;
import study.temporal_behavior_analytics.user.tracking.vo.DailyStatVo;
import study.temporal_behavior_analytics.user.tracking.vo.TimeSlotVo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Schema(name = "트래킹 API", description = "트래킹 요청 API")
@RestController("/")
@RequiredArgsConstructor
public class TrackingController {
    
    private final TrackingService trackingService;

    @Operation(summary = "요일별 사용자 행동 통계", description = "최근 1주일 평균 요일별 통계를 제공합니다.")
    @GetMapping("/activeUsers/wau")
    public ApiResultResponse<List<DailyStatVo>> getWeeklyStats(
    ) {

        // 오늘을 포함한 가장 최근의 토요일 계산
        LocalDate lastSaturday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));

        // 해당 토요일이 속한 주의 일요일 계산
        LocalDate firstSunday = lastSaturday.minusDays(6);


        return ApiResultResponse.success(
                trackingService.getStats(
                        firstSunday.atStartOfDay(),
                        lastSaturday.atTime(LocalTime.MAX)
                )
        );
    }

    @Operation(summary = "요일별 사용자 행동 통계", description = "6개월 평균 요일별 통계를 제공합니다.")
    @GetMapping("/activeUsers/mau")
    public ApiResultResponse<List<DailyStatVo>> getMonthlyStats(
    ) {
        return ApiResultResponse.success(
                trackingService.getStats(
                        LocalDateTime.now().minusMonths(6),
                        LocalDateTime.now()
                )
        );
    }

    @Operation(summary = "요일별 사용자 행동 통계", description = "어제 하루 평균 요일별 통계를 제공합니다.")
    @GetMapping("/activeUsers/dau/timeslot")
    public ApiResultResponse<List<TimeSlotVo>> getDailyStats(
    ) {

        return ApiResultResponse.success(
                trackingService.getTimeSlotStats(
                        LocalDate.now().minusDays(1).atStartOfDay(),
                        LocalDate.now().minusDays(1).atTime(LocalTime.MAX)
                )
        );
    }

    @Operation(summary = "시간대별 사용자 행동 통계", description = "어제 하루 또는 6개월 평균 시간대별 통계를 제공합니다.")
    @GetMapping("/activeUsers/wau/timeslot")
    public ApiResultResponse<List<TimeSlotVo>> getTimeSlotStats(
    ) {
        return ApiResultResponse.success(
                trackingService.getTimeSlotStats(
                        LocalDateTime.now().minusMonths(6),
                        LocalDateTime.now()
                )
        );
    }

    @Operation(summary = "특정 타겟('vote') 액션 사용자 수", description = "최근 6개월 내 'vote' 액션을 하고, 최근 3개월 내 활동한 사용자 수를 반환합니다.")
    @GetMapping("/active-voters/count")
    public ApiResultResponse<Long> getActiveVoterCount() {
        return ApiResultResponse.success(trackingService.countVoteUsers());
    }
    
}
