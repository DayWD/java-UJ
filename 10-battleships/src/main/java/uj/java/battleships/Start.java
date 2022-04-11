package uj.java.battleships;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Start {
    private GameProtocol protocol;
    private int port;
    private String hostAddress;
    private String mapPath;

    public static void main(String[] args) {
        Start game = new Start();
        game.setParameters(args);
        game.startGame();
    }

    private void startGame() {

        try {
            BattleshipMap map = createMap();
            switch (protocol) {
                case SERVER -> {
                    InetAddress address = SrvUtil.findAddress();
                    GameServer server = new GameServer(address, port);
                    server.runServer(map);
                }
                case CLIENT -> {
                    GameClient client = new GameClient();
                    client.runClient(hostAddress, port, map);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private BattleshipMap createMap() throws IOException {
        String map = Files.readString(Path.of(mapPath));
        BattleshipMapParser parser = new BattleshipMapParser();
        Character[][] squareMap = parser.stringTo2DMap(map);
        List<Ship> ships = parser.findShips(squareMap);
        return new BattleshipMap(squareMap, ships);
    }

    private void setParameters(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode" -> {
                    if (args[i + 1].equals("server"))
                        protocol = GameProtocol.SERVER;
                    else
                        protocol = GameProtocol.CLIENT;
                }
                case "-port" -> port = Integer.parseInt(args[i + 1]);
                case "-address" -> hostAddress = args[i + 1];
                case "-map" -> mapPath = args[i + 1];
                default -> {
                    System.out.println("Wpisano niepoprawny parametr");
                    System.exit(0);
                }
            }
        }
    }
}
