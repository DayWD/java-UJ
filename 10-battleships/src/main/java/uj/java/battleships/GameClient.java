package uj.java.battleships;

import java.io.IOException;
import java.net.Socket;

public class GameClient {

    public void runClient(String host, int port, BattleshipMap map) throws IOException {
        Socket socket = new Socket(host, port);
        GameSession session = new GameSession(socket, GameProtocol.CLIENT, map);
        session.runGame();
    }

}
