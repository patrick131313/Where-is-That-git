package com.patrick.whereisthat.data;

/**
 * Created by Patrick on 2/1/2018.
 */

public class Scores {

        private String mLevel;
        private String mHighscore;

        public Scores(String mLevel,String mHighscore)
        {
            this.mLevel=mLevel;
            this.mHighscore=mHighscore;
        }

    public void setmLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public void setmHighscore(String mHighscore) {
        this.mHighscore = mHighscore;
    }

    public String getmLevel() {

        return mLevel;
    }

    public String getmHighscore() {
        return mHighscore;
    }
}
