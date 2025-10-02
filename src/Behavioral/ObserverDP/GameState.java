package Behavioral.ObserverDP;

import java.util.Arrays;

//Game State
class GameState {
    private int[] playerScores;
    private int[] playerPositions;

    public GameState() {
        playerScores = new int[]{10, 20, 15};
        playerPositions = new int[]{5, 10, 8};
    }

    @Override
    public String toString() {
        return "Scores: " + Arrays.toString(playerScores) + ", Positions: " + Arrays.toString(playerPositions);
    }
}