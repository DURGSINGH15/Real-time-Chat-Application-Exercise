package Behavioral.ObserverDP;

//Subject Interface
interface GameServer {
    void addPlayer(Player player);
    void removePlayer(Player player);
    void notifyPlayers();
}