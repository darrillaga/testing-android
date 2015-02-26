package com.mooveit.twittertopics.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrendList {
    
    @JsonProperty("trends")
    private List<Trend> trends;

    @JsonProperty("search_metadata")
    private SearchMetadata searchMetadata;

    public List<Trend> getList() {
        return this.trends;
    }

    public void setList(List<Trend> list) {
        this.trends = list;
    }

    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }
}
