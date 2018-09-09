public class Profile {
    private String name;
    private Player profilePlayer;
    private String difficulty;
    private long score;

    public void increaseScore(long newScore){
        score += newScore;
    }
}
