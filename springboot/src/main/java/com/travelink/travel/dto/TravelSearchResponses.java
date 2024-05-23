package com.travelink.travel.dto;

import java.util.List;

public class TravelSearchResponses {

    private List<TravelSearchResponse> responses;

    private TravelSearchResponses() {
    }

    private TravelSearchResponses(final List<TravelSearchResponse> responses) {
        this.responses = responses;
    }

    public static TravelSearchResponses from(final List<TravelSearchResponse> responses){
        return new TravelSearchResponses(responses);
    }

    public List<TravelSearchResponse> getResponses() {
        return responses;
    }
}
