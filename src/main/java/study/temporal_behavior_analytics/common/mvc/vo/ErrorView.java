package study.temporal_behavior_analytics.common.mvc.vo;

import lombok.Getter;
import lombok.NonNull;
import study.temporal_behavior_analytics.common.type.ServerCode;

import java.util.Date;

public class ErrorView {

    private @NonNull ServerCode result;

    private String message;

    private String code;

    @Getter
    private int status;

    @Getter
    private Date timestamp;

    private @NonNull String error;

    @Getter
    private String path;

    public String getCode() {
        return this.code != null && !this.code.isEmpty() ? this.code : this.result.getCode();
    }

    public String getMessage() {
        return this.message != null && !this.message.isEmpty() ? this.message : this.result.getMessage();
    }

    ErrorView(final @NonNull ServerCode result, final String message, final String code, final int status, final Date timestamp, final @NonNull String error, final String path) {
        if (result == null) {
            throw new NullPointerException("result is marked non-null but is null");
        } else if (error == null) {
            throw new NullPointerException("error is marked non-null but is null");
        } else {
            this.result = result;
            this.message = message;
            this.code = code;
            this.status = status;
            this.timestamp = timestamp;
            this.error = error;
            this.path = path;
        }
    }

    public static ErrorViewBuilder builder() {
        return new ErrorViewBuilder();
    }

    public String toString() {
        return "ErrorView(result=" + this.result +
                ", message=" + this.getMessage() +
                ", code=" + this.getCode() + ", status=" + this.getStatus() +
                ", timestamp=" + this.getTimestamp() + ", error=" + this.getError() +
                ", path=" + this.getPath() + ")";
    }

    public @NonNull String getError() {
        return this.error;
    }

    public static class ErrorViewBuilder {
        private ServerCode result;
        private String message;
        private String code;
        private int status;
        private Date timestamp;
        private String error;
        private String path;

        ErrorViewBuilder() {
        }

        public ErrorViewBuilder result(final @NonNull ServerCode result) {
            if (result == null) {
                throw new NullPointerException("result is marked non-null but is null");
            } else {
                this.result = result;
                return this;
            }
        }

        public ErrorViewBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ErrorViewBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ErrorViewBuilder status(final int status) {
            this.status = status;
            return this;
        }

        public ErrorViewBuilder timestamp(final Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorViewBuilder error(final @NonNull String error) {
            if (error == null) {
                throw new NullPointerException("error is marked non-null but is null");
            } else {
                this.error = error;
                return this;
            }
        }

        public ErrorViewBuilder path(final String path) {
            this.path = path;
            return this;
        }

        public ErrorView build() {
            return new ErrorView(this.result, this.message, this.code, this.status, this.timestamp, this.error, this.path);
        }

        public String toString() {
            return "ErrorView.ErrorViewBuilder(result=" + this.result + ", message=" +
                    this.message + ", code=" + this.code + ", status=" + this.status +
                    ", timestamp=" + this.timestamp + ", error=" + this.error + ", path=" + this.path + ")";
        }
    }
}
