package com.patrick.whereisthat.data;

import java.util.Map;

/**
 * Created by Patrick on 2/1/2018.
 */

public class FirebaseScores {

        private String email;
        private Map<String,Long> scores;
        private Long sprint_mode;
        private String user;

    public FirebaseScores(String email, Map<String, Long> scores, Long sprint_mode, String user) {
        this.email = email;
        this.scores = scores;
        this.sprint_mode = sprint_mode;
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScores(Map<String, Long> scores) {
        this.scores = scores;
    }

    public void setSprint_mode(Long sprint_mode) {
        this.sprint_mode = sprint_mode;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {

        return email;
    }

    public Map<String, Long> getScores() {
        return scores;
    }

    public Long getSprint_mode() {
        return sprint_mode;
    }

    public String getUser() {
        return user;
    }
    public FirebaseScores()
    {

    }


}
