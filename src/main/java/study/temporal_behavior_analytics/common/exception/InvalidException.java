package study.temporal_behavior_analytics.common.exception;


import study.temporal_behavior_analytics.common.type.ServerCode;

public class InvalidException extends RootException {

    public InvalidException(ServerCode code) {
        super(code);
    }

    public InvalidException(ServerCode code, String message) {
        super(code, message);
    }

    public InvalidException(ServerCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
