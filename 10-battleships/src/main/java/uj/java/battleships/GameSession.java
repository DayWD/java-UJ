package uj.java.battleships;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GameSession {
    private final GameProtocol mode;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ArtificialIntelligence player;
    private String targetCoordinate;
    private Coordinate numericTargetCoordinate;
    private final EnemyMap enemyMap;
    private final BattleshipMap myMap;
    private final CoordinatesTranslator translator;
    private int errorCounter;

    public GameSession(Socket socket, GameProtocol mode, BattleshipMap map) throws IOException {
        socket.setSoTimeout(1000);
        this.mode = mode;
        this.targetCoordinate = "Z0";
        this.player = new ArtificialIntelligence();
        this.enemyMap = new EnemyMap();
        this.myMap = map;
        this.translator = new CoordinatesTranslator();
        this.errorCounter = 0;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void runGame() {
        try {
            myMap.showMap();
            System.out.println();

            if (mode == GameProtocol.CLIENT) {
                String toSend = "start;" + takeNextTarget();
                System.out.println(mode + " SEND: " + toSend);
                out.write(toSend);
                out.newLine();
                out.flush();
            }

            while (true) {
                String inputLine = in.readLine();

                if (inputLine.equals("ostatni zatopiony")) {
                    System.out.println("Wygrana");
                    showMaps();
                    break;
                }

                int semicolonIndex = inputLine.indexOf(";");
                String command = inputLine.substring(0, semicolonIndex);
                String coordinate = inputLine.substring(semicolonIndex + 1);
                System.out.println(mode + "  GET: " + command + ";" + coordinate);

                String commandToSend = updateMyMapAndGetFeedback(coordinate);
                updateEnemyMap(command);

                if (myMap.ships.isEmpty()) {
                    String toSend = "ostatni zatopiony";
                    System.out.println(mode + " SEND: " + toSend);
                    System.out.println("Przegrana");
                    showMaps();
                    out.write(toSend);
                    out.newLine();
                    out.flush();
                    break;
                }

                String toSend = commandToSend + ";" + takeNextTarget();
                System.out.println(mode + " SEND: " + toSend);
                out.write(toSend);
                out.newLine();
                out.flush();
            }
        } catch (SocketTimeoutException ex) {
            errorCounter++;
            if (errorCounter >= 3) {
                System.out.println("Błąd komunikacji");
                System.exit(0);
            } else {
                runGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMaps() {
        enemyMap.showMap();
        System.out.println();
        myMap.showMap();
    }

    private String updateMyMapAndGetFeedback(String coordinates) {
        Coordinate numericCoordinate = translator.originalToNumeric(coordinates);
        return myMap.updateChunkAndGetFeedback(numericCoordinate.row(), numericCoordinate.column());
    }

    private void updateEnemyMap(String command) {
        switch (command) {
            case "pudło" -> enemyMap.missed(numericTargetCoordinate);
            case "trafiony" -> enemyMap.hit(numericTargetCoordinate);
            case "trafiony zatopiony" -> enemyMap.sunken(numericTargetCoordinate);
        }
    }

    private String takeNextTarget() {
        this.targetCoordinate = player.nextTarget();
        this.numericTargetCoordinate = translator.originalToNumeric(targetCoordinate);
        return this.targetCoordinate;
    }
}