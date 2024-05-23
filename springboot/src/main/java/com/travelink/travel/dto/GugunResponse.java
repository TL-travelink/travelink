package com.travelink.travel.dto;

import java.util.List;

public class GugunResponse {

    private List<String> guguns;

    private GugunResponse() {
    }

    private GugunResponse(final List<String> guguns) {
        this.guguns = guguns;
    }

    public static GugunResponse from(final List<String> guguns){
        return new GugunResponse(guguns);
    }

    public List<String> getGuguns() {
        return guguns;
    }
}
