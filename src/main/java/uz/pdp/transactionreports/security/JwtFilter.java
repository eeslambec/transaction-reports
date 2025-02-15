package uz.pdp.transactionreports.security;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.service.UserService;

import java.io.IOException;

@NonNullApi
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("register") || request.getServletPath().contains("login")) {
            filterChain.doFilter(request, response);
            return;
        }

        checkAuth(request, response);

        filterChain.doFilter(request, response);
    }

    private void checkAuth(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) return;

        if (!header.startsWith("Bearer ")) return;

        String token = header.substring(7);

        String username = null;

        try {
            username = jwtProvider.getSubject(token);
        } catch (Exception e) {
            response.setStatus(401);
        }

        UserCreateReadDto user = userService.getByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, jwtProvider.getAuthorities(token));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
