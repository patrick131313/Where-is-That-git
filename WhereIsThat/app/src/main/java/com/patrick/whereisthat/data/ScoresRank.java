package com.patrick.whereisthat.data;

/**
 * Created by Patrick on 3/3/2018.
 */

public class ScoresRank {

    private String user;
    private String score;
    private int position;

    public ScoresRank(String user, String score,int position) {
        this.user = user;
        this.score = score;
        this.position=position;
    }

    public String getUser() {
        return user;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
