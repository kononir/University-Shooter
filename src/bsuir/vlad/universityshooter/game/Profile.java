package bsuir.vlad.universityshooter.game;

public class Profile {
    private String name;
    private String difficulty;
    private long score;

    public Profile(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
    }

    public void increaseScore(long newScore) {
        score += newScore;
    }


}
