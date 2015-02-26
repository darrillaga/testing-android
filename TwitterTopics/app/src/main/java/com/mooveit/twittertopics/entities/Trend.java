package com.mooveit.twittertopics.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trend {

    @JsonProperty("name")
    private String name;

    public Trend() {

    }

    public String getTrend() {
        return name;
    }

    public void setTrend(String mTrend) {
        this.name = mTrend;
    }

}
