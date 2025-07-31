package study.temporal_behavior_analytics.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServerCode {

    DATA_NOT_FOUND("1000", "데이터를 찾을 수 없습니다."),

    //----- 공통 코드 -----
    SUCCESS("0000", "성공"),
    NOT_FOUND("0404", "정보를 찾을 수 없습니다."),
    BAD_REQUEST("0400", "잘못된 요청입니다."),
    FORBIDDEN("0403", "접근할 수 없습니다."),
    UNAUTHORIZED("0401", "토큰이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR("0500", "알 수 없는 에러가 발생하였습니다.");

    private final String code;

    private final String message;
}
