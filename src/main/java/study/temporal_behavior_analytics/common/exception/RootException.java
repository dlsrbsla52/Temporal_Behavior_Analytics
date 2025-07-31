package study.temporal_behavior_analytics.common.exception;

import lombok.Getter;
import study.temporal_behavior_analytics.common.type.ServerCode;

public abstract class RootException extends RuntimeException{

    @Getter
    private final ServerCode code;

    protected RootException(ServerCode code) {
        super(code.getMessage());
        this.code = code;
    }

    protected RootException(ServerCode code, String message) {
        super(message);
        this.code = code;
    }

    protected RootException(ServerCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    protected RootException(ServerCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
