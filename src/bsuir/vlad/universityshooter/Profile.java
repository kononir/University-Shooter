package bsuir.vlad.universityshooter;

public class Profile {
    private String name;
    private Player profilePlayer;
    private String difficulty;
    private long score;

    public Profile(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;

        profilePlayer = new Player();
    }

    public Player getProfilePlayer() {
        return profilePlayer;
    }

    public void increaseScore(long newScore) {
        score += newScore;
    }


}
