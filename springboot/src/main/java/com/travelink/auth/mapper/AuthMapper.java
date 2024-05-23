package com.travelink.auth.mapper;

import com.travelink.auth.dto.MemberCreateRequest;
import com.travelink.auth.dto.MemberLoginRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AuthMapper {

    String login(MemberLoginRequest memberLoginRequest);

    void saveRefreshToken(Map<String, Object> map);

    void register(MemberCreateRequest memberCreateRequest);

    void removeRefreshToken(String id);

    String getRefreshToken(String id);

    int checkSignId(String memberId);
}
