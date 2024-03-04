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
        int seconds = Integer.parseInt(this.score);
        int minuts = seconds / 60;
        int remainingSeconds = seconds % 60;
        this.score = String.format("%02d:%02d", minuts, remainingSeconds);

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
