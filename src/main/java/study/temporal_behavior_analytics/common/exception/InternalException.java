package study.temporal_behavior_analytics.common.exception;


import study.temporal_behavior_analytics.common.code.ServerCode;

public class InternalException extends RootException {

    public InternalException(ServerCode code) {
        super(code);
    }

    protected InternalException(ServerCode code, String message) {
        super(code, message);
    }

    protected InternalException(ServerCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    protected InternalException(ServerCode code, Throwable cause) {
        super(code, cause);
    }
}
