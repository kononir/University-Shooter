package bsuir.vlad.universityshooter.activeobjects.characters.bot;

import java.util.concurrent.TimeUnit;

public class BotsGeneratorController {
    private BotsGenerator botsGenerator;

    public BotsGeneratorController(BotsGenerator botsGenerator) {
        this.botsGenerator = botsGenerator;
    }

    public void controlStartingBotsGeneration() {
        long generationSpeed = 5;

        botsGenerator.start(generationSpeed, TimeUnit.SECONDS);
    }

    public void controlStoppingBotsGeneration() {
        botsGenerator.shutdown();
    }
}
