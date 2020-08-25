package com.junho.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Header에 JWT가 포함되어있는지 확인하고 포함되어있지 않은 경우 일반적인 Spring Security 흐름을 따라간다.
// JWT가 포함되어있을 경우 토큰의 유효성을 검사하여 유저에게 권한을 부여할 수 있음을 나타낸다.
@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // JWT Token의 경우 "Bearer token" 형태이므로 Bearer 부분을 지워주고 Token만 가져온다.
            String jwtToken = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
                UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "",
                        jwtTokenUtil.getRolesFromToken(jwtToken));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // context 상에서 권한인증을 설정한 뒤 현재 사용자가 인증되었음을 명시한다.
                // 그 후 Spring Security의 구성을 성공적으로 통과시킨다.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Cannot set the Security Context");
            }
        } catch (ExpiredJwtException ex) {

            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // 아래 조건이 참이라면 Refresh Token을 생성한다.
            // isRefreshToken : Header로부터 isRefreshToken이라는 정보를 받아왔을 때 이것이 Null이 아닐 경우
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else
                request.setAttribute("exception", ex);

        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        chain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // null값이 들어가있는 UsernamePasswordAuthenticationToken을 생성한다.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // context에서 인증을 설정하고, 현재 사용자가 인증된 사용자임을 지정한다.
        // Spring Security의 구성을 성공적으로 통과시킨다.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // claim을 설정해서 controller에서 claim을 사용하여 새로운 JWT를 생성하도록 한다.
        request.setAttribute("claims", ex.getClaims());

    }

    // "Bearer token" 형태를 "token" 형태로 걸러준다.
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
