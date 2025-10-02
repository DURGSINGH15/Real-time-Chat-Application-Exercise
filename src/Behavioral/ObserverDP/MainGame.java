package Behavioral.ObserverDP;

public class MainGame {
    public static void main(String[] args) {
        OnlineGameServer gameServer = new OnlineGameServer();

        Player player1 = new OnlinePlayer("Alice");
        Player player2 = new OnlinePlayer("Bob");
        Player player3 = new OnlinePlayer("Charlie");

        gameServer.addPlayer(player1);
        gameServer.addPlayer(player2);
        gameServer.addPlayer(player3);

        // Update game state and notify players
        GameState newState = new GameState();
        // Set player scores and positions in newState
        gameServer.updateGameState(newState);
    }
}