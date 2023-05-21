package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class PersonBaseDTO {

    @SerializedName("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
