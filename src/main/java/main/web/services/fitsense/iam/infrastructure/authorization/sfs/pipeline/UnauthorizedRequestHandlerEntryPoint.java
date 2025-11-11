package main.web.services.fitsense.iam.infrastructure.authorization.sfs.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Unauthorized Request Handler.
 * <p>
 * This class is responsible for handling unauthorized requests.
 * It is used by the Spring Security framework to handle unauthorized requests.
 * It implements the AuthenticationEntryPoint interface.
 * </p>
 * @see AuthenticationEntryPoint
 */

@Component
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedRequestHandlerEntryPoint.class);

    /**
     * This method is called by the Spring Security framework when an unauthorized request is detected.
     * @param request The request that caused the exception
     * @param response The response that will be sent to the client
     * @param authenticationException The exception that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        LOGGER.error("Unauthorized request: {} for path: {}", authenticationException.getMessage(), request.getRequestURI());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        var errorResponse = new ErrorResponse(
            HttpServletResponse.SC_UNAUTHORIZED,
            "Acceso no autorizado",
            "Se requiere autenticación para acceder a este recurso",
            request.getRequestURI()
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    @Getter
    @lombok.Value
    private static class ErrorResponse {
        int status;
        String error;
        String message;
        String path;
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .format(new java.util.Date());

        public ErrorResponse(int status, String error, String message, String path) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }
    }
}