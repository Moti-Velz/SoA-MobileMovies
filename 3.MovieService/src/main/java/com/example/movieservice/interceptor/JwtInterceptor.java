package com.example.movieservice.interceptor;
import com.example.movieservice.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {

        // Allow OPTIONS requests without JWT validation
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            return true;
        }

        String token = null;
        jakarta.servlet.http.Cookie[] cookies = req.getCookies();

        // Check if cookies exist and find our JWT cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // If JWT cookie found, validate it
        if (token != null) {
            System.out.println("Received JWT token from cookie: " + token);
            if (jwtService.validate(token)) {
                return true;
            }
        }
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
