package study.temporal_behavior_analytics.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "페이징 정보 관련 객체")
public class Pagination {

    @Schema(description = "총 페이지 수")
    private int totalPages;

    @Schema(description = "전체 데이터 수")
    private long totalElements;

    @Schema(description = "현재 페이지")
    private int currentPage;

    @Schema(description = "요청 size 개수")
    private int size;
}
