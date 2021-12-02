package org.acme.entertainment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.json.bind.annotation.JsonbCreator;

@JsonIgnoreProperties(ignoreUnknown = true)
public sealed class BasicHobby permits RandomHobby {

    public String key;
    public String activity;
    public String type;
    public int participants;
    public double price;

    @JsonbCreator
    public static BasicHobby empty() {
        return new BasicHobby();
    }
}
