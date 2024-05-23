package com.travelink.auth.service;

import com.travelink.auth.dto.MemberCreateRequest;
import com.travelink.auth.dto.MemberLoginRequest;
import com.travelink.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse login(final MemberLoginRequest memberLoginRequest);

    void register(MemberCreateRequest memberCreateRequest);

    void logout(String loginId);

    TokenResponse refreshToken(String token);

    boolean checkSignId(String memberId);
}
