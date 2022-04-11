package uj.java.w3;

public interface BattleshipGenerator {

    String generateMap();

    static BattleshipGenerator defaultInstance() {
        return new BattleshipGeneratorClass();
    }

}

// Lista punktow na których znajduje się część statku, usuwamy je na bierząco razem z punktami na których jest border.