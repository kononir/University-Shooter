package bsuir.vlad.universityshooter.game;

import java.util.HashMap;

public class Profile {
    private final String name;
    private final String difficulty;
    //private final HashMap<String, Long> botsScoreMap;
    private long score;

    public Profile(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;

        //botsScoreMap
    }

    /*public void increaseScore(String botsType) {
        long newScore = botsScoreMap.get(botsType);
        score += newScore;
    }*/


}
