package com.travelink.auth.support;

import com.travelink.auth.service.JwtAuthService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class JwtLoginResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_SEPARATOR = " ";
    private static final int BEARER_INDEX = 0;
    private static final int PAYLOAD_INDEX = 1;
    private static final String BEARER = "Bearer";

    private final JwtAuthService jwtAuthService;

    public JwtLoginResolver(final JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtLogin.class);
    }

    @Override
    public String resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {
        String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        validateAuthorization(authorizationHeader);

        return jwtAuthService.findMemberIdByJwtPayload(getJwtPayload(Objects.requireNonNull(authorizationHeader)));
    }

    private void validateAuthorization(final String authorizationHeader) {
        if (!hasAuthorizationHeader(authorizationHeader) || !isBearerToken(authorizationHeader)) {
            throw new RuntimeException();
        }
    }

    private boolean hasAuthorizationHeader(final String authorizationHeader) {
        return authorizationHeader != null;
    }

    private boolean isBearerToken(final String authorizationHeader) {
        return authorizationHeader.split(TOKEN_SEPARATOR)[BEARER_INDEX].equals(BEARER);
    }

    private String getJwtPayload(final String authorizationHeader) {
        return authorizationHeader.split(TOKEN_SEPARATOR)[PAYLOAD_INDEX];
    }
}
