package com.example.catalog.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimit implements HandlerInterceptor {

    @Value("${rate-limit.algo}")
    private String rateLimitAlgo;

    @Value("${rate-limit.rpm}")
    private String rateLimitRPM;

    private final int maxRequestsPerMinute = 10;  // Configurable RPM
    private final long timeWindowInMillis = 60000; // 1 minute in milliseconds

    private final Map<String, Queue<Long>> slidingWindowRequests = new ConcurrentHashMap<>();
    private final Map<String, RequestWindow> fixedWindowRequests = new ConcurrentHashMap<>();

    private static class RequestWindow {
        int requestCount = 0;
        long windowStartTime = System.currentTimeMillis();
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        String requestUri = request.getRequestURI();

        if ("/internal".equals(requestUri)) {
            return true;  // Allow the request to pass without any rate limiting
        }

        if (isAllowed(clientIp,rateLimitAlgo)) {
            // If allowed, set the remaining requests header
            long remainingRequests = maxRequestsPerMinute - getRequestCount(clientIp);
            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(remainingRequests));
            return true;
        }

        // If rate limit is exceeded, block the request and set retry-after header
        response.setStatus(429);
        response.setHeader("X-Rate-Limit-Remaining", String.valueOf(0));
        long retryAfterSeconds = calculateRetryAfter(clientIp,rateLimitAlgo);
        response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(retryAfterSeconds));

        return false;

    }
    public boolean isAllowed(String clientIp, String rateLimitAlgo) {
        if ("fixed".equals(rateLimitAlgo)) {
            return isAllowedFixed(clientIp);
        } else if ("moving".equals(rateLimitAlgo)) {
            return isAllowedSliding(clientIp);
        }
        return false;
    }
    private boolean isAllowedSliding(String clientIp) {
        long currentTime = System.currentTimeMillis();
        slidingWindowRequests.putIfAbsent(clientIp, new LinkedList<>());

        Queue<Long> timestamps = slidingWindowRequests.get(clientIp);

        // Remove requests that are outside the current time window
        while (!timestamps.isEmpty() && (currentTime - timestamps.peek()) > timeWindowInMillis) {
            timestamps.poll();
        }

        if (timestamps.size() < maxRequestsPerMinute) {
            timestamps.offer(currentTime);  // Add the current request timestamp
            return true;
        }

        return false;

    }


    private boolean isAllowedFixed(String clientIp) {
        long currentTime = System.currentTimeMillis();

        fixedWindowRequests.putIfAbsent(clientIp, new RequestWindow());
        RequestWindow window = fixedWindowRequests.get(clientIp);

        // If the time exceeds the fixed window, reset the counter
        if ((currentTime - window.windowStartTime) >= timeWindowInMillis) {
            window.windowStartTime = currentTime;
            window.requestCount = 0;
        }

        if (window.requestCount < maxRequestsPerMinute) {
            window.requestCount++;
            return true;
        }

        // Deny the request if the limit is exceeded
        return false;
    }
    private int getRequestCount(String clientIp) {

        if ("fixed".equals(rateLimitAlgo)) {
            return getRequestCountFixed(clientIp);
        } else if ("moving".equals(rateLimitAlgo)) {
            return getRequestCountSliding(clientIp);
        }
        return 0;
    }
    public int getRequestCountFixed(String clientIp) {
        RequestWindow window = fixedWindowRequests.get(clientIp);
        if (window == null) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - window.windowStartTime) >= timeWindowInMillis) {
            return 0;  // Reset if the window has expired
        }
        return window.requestCount;
    }

    public int getRequestCountSliding(String clientIp) {
        Queue<Long> timestamps = slidingWindowRequests.get(clientIp);
        if (timestamps == null) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        // Remove timestamps outside the window
        while (!timestamps.isEmpty() && (currentTime - timestamps.peek()) > timeWindowInMillis) {
            timestamps.poll();
        }
        return timestamps.size();
    }



    public long calculateRetryAfter(String clientIp, String rateLimitAlgo) {
        if ("fixed".equals(rateLimitAlgo)) {
            return calculateRetryAfterFixed(clientIp);
        } else if ("moving".equals(rateLimitAlgo)) {
            return calculateRetryAfterSliding(clientIp);
        }
        return 0;
    }
    // Calculate the retry time for the blocked client
    private long calculateRetryAfterSliding(String clientIp) {
        long currentTime = System.currentTimeMillis();
        Queue<Long> timestamps = slidingWindowRequests.get(clientIp);
        if (timestamps.isEmpty()) {
            return 0;
        }

        long firstRequestTime = timestamps.peek();
        return (timeWindowInMillis - (currentTime - firstRequestTime)) / 1000;
    }

    private long calculateRetryAfterFixed(String clientIp) {
        long currentTime = System.currentTimeMillis();
        RequestWindow window = fixedWindowRequests.get(clientIp);

        if (window == null) {
            return 0;
        }

        long timeRemaining = timeWindowInMillis - (currentTime - window.windowStartTime);
        return timeRemaining / 1000;
    }
}