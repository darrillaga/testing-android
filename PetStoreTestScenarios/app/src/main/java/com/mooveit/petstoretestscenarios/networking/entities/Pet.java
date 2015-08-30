package com.mooveit.petstoretestscenarios.networking.entities;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;

import static java8.util.J8Arrays.stream;

public class Pet {

    public enum Status {
        AVAILABLE("available"), PENDING("pending"), SOLD("sold");

        private String statusName;

        Status(String statusName) {
            this.statusName = statusName;
        }

        @JsonValue
        public String statusName() {
            return statusName;
        }

        public static String[] statusNames() {
            List<String> statusNames = stream(Status.values()).map(Status::statusName).
                    collect(Collectors.toList());

            return statusNames.toArray(new String[statusNames.size()]);
        }
    }

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tag> tags;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public String getMainPictureUrl() {
        return !getPhotoUrls().isEmpty() ? getPhotoUrls().get(0) : null;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}