package bsuir.vlad.universityshooter.game;

import java.io.IOException;

public class Profile {
    private final String name;
    private final Difficulty difficulty;
    private long score;

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
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

    public void increaseScore(int newScore) {
        score += newScore;
    }

    public int createCoefficient() {
        int coefficient = 0;

        try {
            switch (difficulty) {
                case EASY:
                    coefficient = 3;
                    break;
                case MEDIUM:
                    coefficient = 2;
                    break;
                case HARD:
                    coefficient = 1;
                    break;
                default:
                    throw new IOException("Difficulty input exception");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coefficient;
    }
}
