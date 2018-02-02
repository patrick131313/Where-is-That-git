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

    /*   private Long level1;
        private Long level2;
        private Long level3;
        private Long level4;
        private Long level5;
        private Long level6;
        private Long level7;
        private Long level8;
        private Long level9;
        private Long level10;
        private Long level11;


    public FirebaseScores(Long level1, Long level2, Long level3, Long level4, Long level5, Long level6, Long level7, Long level8, Long level9, Long level10, Long level11) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.level6 = level6;
        this.level7 = level7;
        this.level8 = level8;
        this.level9 = level9;
        this.level10 = level10;
        this.level11 = level11;
    }

    public void setLevel1(Long level1) {
        this.level1 = level1;
    }

    public void setLevel2(Long level2) {
        this.level2 = level2;
    }

    public void setLevel3(Long level3) {
        this.level3 = level3;
    }

    public void setLevel4(Long level4) {
        this.level4 = level4;
    }

    public void setLevel5(Long level5) {
        this.level5 = level5;
    }

    public void setLevel6(Long level6) {
        this.level6 = level6;
    }

    public void setLevel7(Long level7) {
        this.level7 = level7;
    }

    public void setLevel8(Long level8) {
        this.level8 = level8;
    }

    public void setLevel9(Long level9) {
        this.level9 = level9;
    }

    public void setLevel10(Long level10) {
        this.level10 = level10;
    }

    public void setLevel11(Long level11) {
        this.level11 = level11;
    }

    public Long getLevel1() {

        return level1;
    }

    public Long getLevel2() {
        return level2;
    }

    public Long getLevel3() {
        return level3;
    }

    public Long getLevel4() {
        return level4;
    }

    public Long getLevel5() {
        return level5;
    }

    public Long getLevel6() {
        return level6;
    }

    public Long getLevel7() {
        return level7;
    }

    public Long getLevel8() {
        return level8;
    }

    public Long getLevel9() {
        return level9;
    }

    public Long getLevel10() {
        return level10;
    }

    public Long getLevel11() {
        return level11;
    }*/
}
