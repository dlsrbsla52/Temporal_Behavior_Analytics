package study.temporal_behavior_analytics.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
public class LoggingHttpServletRequestWrapper extends ContentCachingRequestWrapper {

    private final ObjectMapper objectMapper;

    public LoggingHttpServletRequestWrapper(HttpServletRequest request, ObjectMapper objectMapper) {
        super(request);
        this.objectMapper = objectMapper;
    }

    public String getHeaderString() {
        HashMap<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerArray = getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, getHeader(headerName));
        }
        try {
            return objectMapper.writeValueAsString(headerMap);
        } catch (Exception e) {
            log.error("LoggingHttpServletRequestWrapper::getHeaderString", e);
            return headerMap.toString();
        }
    }

    public String getQueryParameter() {

        try {
            return objectMapper.writeValueAsString(getParameterMap());
        } catch (Exception e) {
            log.error("LoggingHttpServletRequestWrapper::getQueryParameter", e);
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            getParameterMap().forEach((key, value) -> sb.append(key).append(" : ").append(Arrays.stream(value).collect(Collectors.joining(",","[","]"))).append(", "));
            sb.append("}");
            return sb.toString();
        }
    }

    public String getContentBody() {
        byte[] contents = getContentAsByteArray();
        try {
            return objectMapper.readTree(contents).toString();
        }catch (IOException e) {
            log.error("LoggingHttpServletRequestWrapper::getContentBody", e);
            return new String(contents);
        }
    }
}
