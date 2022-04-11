package uj.java.battleships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record ArtificialIntelligence(List<String> targets) {

    public ArtificialIntelligence() {
        this(new ArrayList<>());
        for (int i = 1; i < 11; i++) {
            for (char j = 'A'; j < 'K'; j++) {
                targets.add(j + String.valueOf(i));
            }
        }
    }

    public String nextTarget() {
        Random rand = new Random();
        return targets.remove(rand.nextInt(targets.size()));
    }
}
