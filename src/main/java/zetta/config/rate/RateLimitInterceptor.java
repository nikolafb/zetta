package zetta.config.rate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS_PER_SECOND = 2;
    private Map<String, Map<Long, AtomicInteger>> clientRequestCounts = new ConcurrentHashMap<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();
        long currentSecond = currentTime / 1000;

        // Get or create a map to hold request counts per second for this client
        Map<Long, AtomicInteger> requestCounts = clientRequestCounts.computeIfAbsent(clientIP, k -> new ConcurrentHashMap<>());

        // Get or create an AtomicInteger to count requests for the current second
        AtomicInteger requestsThisSecond = requestCounts.computeIfAbsent(currentSecond, k -> new AtomicInteger(0));

        // Increment the count for this second
        int count = requestsThisSecond.incrementAndGet();

        // Cleanup old entries periodically to prevent memory issues
        cleanupOldEntries();

        // Check if the client exceeded the rate limit
        if (count > MAX_REQUESTS_PER_SECOND) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Please wait a moment and try again.");
            response.getWriter().flush();
            return false;
        }

        return true;
    }

    private void cleanupOldEntries() {
        long currentTime = System.currentTimeMillis();
        long currentSecond = currentTime / 1000;
        clientRequestCounts.forEach((clientIP, requestCounts) -> {
            requestCounts.keySet().removeIf(second -> second < currentSecond);
            if (requestCounts.isEmpty()) {
                clientRequestCounts.remove(clientIP);
            }
        });
    }
}