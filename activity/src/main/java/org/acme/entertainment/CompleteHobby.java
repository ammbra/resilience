package org.acme.entertainment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public non-sealed class CompleteHobby extends BasicHobby {
    public String link;
    public double accessibility;
}


