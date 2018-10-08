package bsuir.vlad.universityshooter.game;

public class Profile {
    private final String name;
    private final String difficulty;
    private long score;

    public long getScore() {
        return score;
    }

    public Profile(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
    }

    public void increaseScore(int newScore) {
        score += newScore;
    }
}
