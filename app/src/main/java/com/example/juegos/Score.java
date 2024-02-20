package com.example.juegos;

public class Score {
    private String name;
    private String score;

    public Score(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public Score() {
    }

    public void changeScoreSenku() {
        try {
            int score = Integer.parseInt(this.score);
            int minutes = score / 60;
            int seconds = score % 60;
            this.score = minutes + "m " + seconds + "s";
        } catch (Exception e) {
            this.score = "0m 0s";
        }

    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
