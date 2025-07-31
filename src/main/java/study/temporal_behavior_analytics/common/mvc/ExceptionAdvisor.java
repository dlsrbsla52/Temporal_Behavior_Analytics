package study.temporal_behavior_analytics.common.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import study.temporal_behavior_analytics.common.exception.InternalException;
import study.temporal_behavior_analytics.common.exception.InvalidException;
import study.temporal_behavior_analytics.common.exception.RootException;
import study.temporal_behavior_analytics.common.mvc.vo.ErrorView;
import study.temporal_behavior_analytics.common.type.ServerCode;

import java.util.Date;

@ControllerAdvice
public class ExceptionAdvisor {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvisor.class);

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<ErrorView> runtimeExceptionHandler(HttpServletRequest request, RootException error) {
        log.error("authHandler.error : ", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorView.builder()
                        .result(ServerCode.INTERNAL_SERVER_ERROR)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(new Date())
                        .path(request.getServletPath()).build()
                );
    }

    @ExceptionHandler({RootException.class})
    @ResponseBody
    public ResponseEntity<ErrorView> rootExceptionHandler(HttpServletRequest request, RootException error) {
        log.error("authHandler.error : ", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorView.builder()
                        .result(ServerCode.BAD_REQUEST)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(new Date())
                        .path(request.getServletPath()).build()
                );
    }

    @ExceptionHandler({InvalidException.class})
    @ResponseBody
    public ResponseEntity<ErrorView> invalidExceptionHandler(HttpServletRequest request, InvalidException error) {
        log.error("noS3KeyHandler.error : ", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorView.builder().result(ServerCode.BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(new Date())
                        .path(request.getServletPath())
                        .build()
                );
    }

    @ExceptionHandler({InternalException.class})
    @ResponseBody
    public ResponseEntity<ErrorView> internalExceptionHandler(HttpServletRequest request, InternalException error) {
        log.error("accessDeniedHandler.error : ", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorView.builder()
                        .result(ServerCode.INTERNAL_SERVER_ERROR)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(new Date())
                        .path(request.getServletPath())
                        .build()
                );
    }
}
