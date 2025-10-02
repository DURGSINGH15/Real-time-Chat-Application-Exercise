package Behavioral.ObserverDP;

//Concrete Observer
class OnlinePlayer implements Player {
    private final String name;
    private GameState currentState;

    public OnlinePlayer(String name) {
        this.name = name;
    }

    @Override
    public void update(GameState gameState) {
        currentState = gameState;
        System.out.println("Player " + name + " received game state update: " + currentState);
    }
}

