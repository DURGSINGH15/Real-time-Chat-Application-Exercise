package Behavioral.ObserverDP;

import java.util.ArrayList;
import java.util.List;

//Concrete Subject
class OnlineGameServer implements GameServer {
    private final List<Player> players;
    private GameState gameState;

    public OnlineGameServer() {
        players = new ArrayList<>();
        gameState = new GameState();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public void notifyPlayers() {
        for (Player player : players) {
            player.update(gameState);
        }
    }

    public void updateGameState(GameState newState) {
        gameState = newState;
        notifyPlayers();
    }
}

