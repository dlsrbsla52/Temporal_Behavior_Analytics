package study.temporal_behavior_analytics.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "페이징 응답의 공통 wrapper 객체")
public class PagingResponse<T> {

    @Schema(description = "페이징 관련 응답")
    private Pagination pagination;

    @Schema(description = "현재 페이지의 응답 데이터")
    private List<T> elements;


    public PagingResponse(Page<T> source) {
        pagination = Pagination.builder()
                .totalPages(source.getTotalPages())
                .totalElements(source.getTotalElements())
                .currentPage(source.getNumber())
                .size(source.getSize())
                .build();
        elements = source.getContent();
    }

    public static PagingResponse emptyResponse() {
        PagingResponse<Object> response = new PagingResponse<>();
        response.pagination = new Pagination(0,0,0,0);
        response.elements = Collections.emptyList();
        return response;
    }

    public PagingResponse(List<T> sources, int totalPages, long totalElements, int currentPage, int size) {
        pagination = Pagination.builder()
                .totalPages(totalPages)
                .totalElements(totalElements)
                .currentPage(currentPage)
                .size(size)
                .build();
        elements = sources;
    }
}
