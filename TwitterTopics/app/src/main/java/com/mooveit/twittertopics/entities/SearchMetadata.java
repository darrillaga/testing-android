package com.mooveit.twittertopics.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchMetadata {

    @JsonProperty("next_results")
    private String nextResults;

    public SearchMetadata() {

    }

    public String getNextResults() {
        return nextResults;
    }

    public void setNextResults(String nextResults) {
        this.nextResults = nextResults;
    }
}
