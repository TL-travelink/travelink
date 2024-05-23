package com.travelink.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class MemberLoginRequest {

    @NotBlank(message = "id를 입력해주세요.")
    private String id;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    private MemberLoginRequest() {
    }

    public MemberLoginRequest(final String id, final String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
