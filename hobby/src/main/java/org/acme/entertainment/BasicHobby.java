package org.acme.entertainment;


import javax.json.bind.annotation.JsonbCreator;

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
