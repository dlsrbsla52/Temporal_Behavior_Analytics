package study.temporal_behavior_analytics.common.domain;

public enum MDCKey {
    TRX_ID("request_id");

    private final String key;

    public String getKey() {
        return key;
    }

    MDCKey(String key) {
        this.key = key;
    }
}
