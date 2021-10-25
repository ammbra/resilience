package org.acme.entertainment;

import javax.json.bind.annotation.JsonbCreator;

public non-sealed class RandomHobby extends BasicHobby {
    public String cloudId;
    public String workerId;

    @JsonbCreator
    public static RandomHobby empty() {
        return new RandomHobby();
    }
}
