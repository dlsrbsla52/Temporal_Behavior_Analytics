package study.temporal_behavior_analytics.user.tracking.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ActiveUserVo {
    
    // 사용자 ID
    private Long userId;
    
    // 행위 데이터
    private List<ActVo> actVo;
}
