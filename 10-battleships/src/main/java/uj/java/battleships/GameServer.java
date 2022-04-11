package uj.java.battleships;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private final ServerSocket serverSocket;

    public GameServer(InetAddress address, int port) throws IOException {
        serverSocket = new ServerSocket(port, 10000, address);
        System.out.println("ADDRESS: " + address + ", PORT: " + port);
    }

    public void runServer(BattleshipMap map) throws IOException {
        Socket socket = serverSocket.accept();
        GameSession session = new GameSession(socket, GameProtocol.SERVER, map);
        session.runGame();
    }

}
