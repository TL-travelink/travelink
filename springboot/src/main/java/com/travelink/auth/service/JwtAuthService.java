package com.travelink.auth.service;

import com.travelink.auth.dto.MemberCreateRequest;
import com.travelink.auth.mapper.AuthMapper;
import com.travelink.auth.dto.MemberLoginRequest;
import com.travelink.auth.dto.TokenResponse;
import com.travelink.auth.util.JWTProvider;
import com.travelink.member.dto.MemberInfoDto;
import com.travelink.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtAuthService implements AuthService {

    private final AuthMapper authMapper;
    private final MemberMapper memberMapper;
    private final JWTProvider jwtProvider;

    public JwtAuthService(final AuthMapper authMapper, final MemberMapper memberMapper,
        final JWTProvider jwtProvider) {
        this.authMapper = authMapper;
        this.memberMapper = memberMapper;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public TokenResponse login(final MemberLoginRequest memberLoginRequest) {
        String id = authMapper.login(memberLoginRequest);

        if (id.isEmpty()) {
            // todo 커스텀 에러
            throw new RuntimeException();
        }

        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshToken(id);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("refreshToken", refreshToken);
        authMapper.saveRefreshToken(params);

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public String findMemberIdByJwtPayload(final String jwtPayload) {
        return jwtProvider.getPayload(jwtPayload);
    }

    @Transactional
    public void register(MemberCreateRequest memberCreateRequest) {
        authMapper.register(memberCreateRequest);
        memberMapper.createInfo(MemberInfoDto.of(memberCreateRequest.getId()));
    }

    @Transactional
    public void logout(String loginId) {
        authMapper.removeRefreshToken(loginId);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponse refreshToken(String token) {
        String userId = jwtProvider.getPayload(token);
        String refreshToken = authMapper.getRefreshToken(userId);

        if (!refreshToken.equals(token) || !jwtProvider.validateToken(token)) {
            //todo 잘못된 토큰
            throw new RuntimeException();
        }
        String accessToken = jwtProvider.createAccessToken(userId);
        return TokenResponse.of(accessToken, refreshToken);
    }

    @Override
    public boolean checkSignId(String memberId) {
        int count = authMapper.checkSignId(memberId);
        System.out.println(count);
        return count == 0;
    }

    private boolean isSameMember(String loginId, String id) {
        return loginId.equals(id);
    }
}
