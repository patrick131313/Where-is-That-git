package com.patrick.whereisthat.data;

/**
 * Created by Patrick on 3/3/2018.
 */

public class ScoresRank {

    private String user;
    private String score;

    public ScoresRank(String user, String score) {
        this.user = user;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
