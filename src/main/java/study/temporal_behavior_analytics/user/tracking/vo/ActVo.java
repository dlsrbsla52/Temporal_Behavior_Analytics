package study.temporal_behavior_analytics.user.tracking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import study.temporal_behavior_analytics.user.tracking.code.TimeSlot;

@Getter
@Setter
@ToString
public class ActVo {
    
    @Schema(name = "액션(행위) 타입")
    private String actionType;
    
    @Schema(name = "액션(행위) 타겟")
    private String actionTarget;
    
    @Schema(name = "액션(행위) 카운트")
    private Integer actionCount;
    
    @Schema(name = "액션(행위) 번호")
    private Double actionValue;

    @Schema(name = "액션(행위)")
    private TimeSlot timeSlot;
}
