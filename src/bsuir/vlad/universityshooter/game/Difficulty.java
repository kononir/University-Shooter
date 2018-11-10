package bsuir.vlad.universityshooter.game;

import java.io.IOException;

public enum Difficulty {
    EASY(3),
    MEDIUM(2),
    HARD(1);

    private int coefficient;

    Difficulty(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }
}
