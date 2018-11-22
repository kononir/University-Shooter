package bsuir.vlad.universityshooter.game.profile;

import bsuir.vlad.universityshooter.game.Difficulty;

public class Profile {
    private final String name;
    private final Difficulty difficulty;
    private long score;

    String getName() {
        return name;
    }

    Difficulty getDifficulty() {
        return difficulty;
    }

    public long getScore() {
        return score;
    }

    public Profile(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
    }

    public Profile(String name, Difficulty difficulty, long score) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
    }

    void increaseScore(int newScore) {
        score += newScore;
    }
}
