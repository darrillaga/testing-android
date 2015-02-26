package com.mooveit.twittertopics.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TweetList {

    private List<Tweet> statuses;

    @JsonProperty("search_metadata")
    private SearchMetadata searchMetadata;

    public TweetList() {

    }

    public List<Tweet> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Tweet> statuses) {
        this.statuses = statuses;
    }

    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }
}
