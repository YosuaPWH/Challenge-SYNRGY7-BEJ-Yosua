package org.yosua.binfood.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.yosua.binfood.model.dto.response.ApiResponse;

import java.io.IOException;

public class UnauthenticatedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .success(false)
                .data(null)
                .errors("Access Denied")
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}
