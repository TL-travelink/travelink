package com.travelink.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class MemberCreateRequest {

    @NotBlank(message = "id를 입력해주세요.")
    private String id;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    private MemberCreateRequest() {
    }

    public MemberCreateRequest(final String id, final String password, final String name, final String address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
