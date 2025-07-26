package study.temporal_behavior_analytics.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.temporal_behavior_analytics.common.model.ApiResultResponse;

@RequestMapping("/health")
@RestController
public class HealthCheckController {

    @GetMapping
    public ApiResultResponse<String> health() {
        return ApiResultResponse.success("OK");
    }
}
