package bsuir.vlad.universityshooter.game.profile;

import bsuir.vlad.universityshooter.game.Difficulty;

public class ProfileController {
    private Profile profile;

    public ProfileController(Profile profile) {
        this.profile = profile;
    }

    public ProfileController(String profileName, Difficulty difficulty) {
        profile = new Profile(profileName, difficulty);
    }

    public Profile controlGettingProfile() {
        return profile;
    }

    public void controlIncreasingScore(int newScore) {
        profile.increaseScore(newScore);
    }

    public long controlGettingScore() {
        return profile.getScore();
    }
}
