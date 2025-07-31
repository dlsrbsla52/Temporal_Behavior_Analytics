package study.temporal_behavior_analytics.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import study.temporal_behavior_analytics.common.type.ServerCode;

@Getter
@ToString
public class ApiResultResponse<T> {

    @Schema(description = "성공 실패 코드 0000인경우 성공 이외 실패")
    private final String code;

    @Schema(description = "성공 실패 메세지 SUCCESS인 경우 성공")
    private final String message;

    @Schema(description = "데이터 정보")
    private final T data;

    @JsonCreator
    @Builder
    public ApiResultResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResultResponse<T> success(T data) {
        return ApiResultResponse.ofResponse(ServerCode.SUCCESS, data);
    }

    public static <T> ApiResultResponse<PagingResponse<T>> ofResponseWithPaging(Page<T> paging) {
        PagingResponse<T> data = new PagingResponse<>(paging);
        return ApiResultResponse.ofResponse(ServerCode.SUCCESS, data);
    }

    public static <T> ApiResultResponse<PagingResponse<T>> ofResponseWithPaging(ServerCode code, Page<T> paging) {
        PagingResponse<T> data = new PagingResponse<>(paging);
        return ApiResultResponse.ofResponse(code.getCode(), code.getMessage(), data);
    }

    public static <T> ApiResultResponse<T> ofResponse(ServerCode code) {
        return ApiResultResponse.ofResponse(code.getCode(), code.getMessage(),null);
    }

    public static <T> ApiResultResponse<T> ofResponse(String code, String message) {
        return ApiResultResponse.ofResponse(code, message,null);
    }

    public static <T> ApiResultResponse<T> ofResponse(ServerCode code, T data) {
        return ApiResultResponse.ofResponse(code.getCode(), code.getMessage(),data);
    }

    public static <T> ApiResultResponse<T> ofResponse(String code, String message, T data) {
        return ApiResultResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
