package bsuir.vlad.universityshooter.game;

public class ProfileController {
    private Profile profile;

    public ProfileController(Profile profile) {
        this.profile = profile;
    }

    public void controlIncreasingScore(int newScore) {
        profile.increaseScore(newScore);
    }

    public long controlGettingScore() {
        return profile.getScore();
    }
}
