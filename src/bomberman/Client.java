
package bomberman;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages an input from server.
 * @author David Benes
 */
public class Client extends Thread {
    
    private Socket clientSocket;
    
    private DataOutputStream outToServer;
    
    private final int portNumber;
    
    private final String serverAdress;
    
    private int playerID;
    
    private final String playerName;
    
    private ClientCallback callback;
    
    private boolean logout;

    public Client(int portNumber, String serverAdress, String playerName) {
        this.portNumber = portNumber;
        this.serverAdress = serverAdress;
        this.playerName = playerName;
        logout = false;
    }

    public int getPlayerID() {
        return playerID;
    }
    
    public void registerCallback(ClientCallback c) {
        callback = c;
    }
    
    /**
     * Sends a requirement of moving a player to server.
     * @param dir direction of the movement.
     * @throws IOException 
     */
    public void move(Direction dir) throws IOException {
        if (logout) {
            sendBye();
            return;
        }
        outToServer.writeBytes("MOVE"+dir.toString()+"\n");
    }
    
    /**
     * Sends a requirement of dropping a bomb to server.
     * @throws IOException 
     */
    public void dropBomb() throws IOException {
        if (logout) {
            sendBye();
            return;
        }
        outToServer.writeBytes("DROPBOMB\n");
    }
    
    /**
     * Sends a requirement of getting data to server.
     * @throws IOException 
     */
    public void getData() throws IOException {
        if (logout) {
            sendBye();
            return;
        }
        outToServer.writeBytes("GETDATA\n");
    }

    /**
     * Sends a simple string and closes the socket.
     * @throws IOException 
     */
    public void sendBye() throws IOException {
        outToServer.writeBytes("BYE\n");
        clientSocket.close();
        if (callback != null) callback.closeWindow();
    }
    
    /**
     * Runs the client. Activates a loop for checking for input from server. 
     */
    @Override
    public void run() {
        try {
            super.run();
            String serverIn;
            this.clientSocket = new Socket(serverAdress, portNumber);
            this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (!logout) {
                serverIn = inFromServer.readLine();
                if (!serverIn.isEmpty()) {
                    String[] serverInField = serverIn.split(" ");
                    switch(serverInField[0]) {
                        case "DATA":
                            HashMap<Integer, Integer> dataLifes = new HashMap<>();
                            HashMap<Integer, HashMap<BonusType, Boolean>> dataBonuses = new HashMap<>();
                            HashMap<Position, ArrayList<String>> dataMap = new HashMap<>();
                            if (serverInField.length <= 1) break;
                            for (int i=1; i<serverInField.length; i++) {
                                String[] currentData = serverInField[i].split("_");
                                switch(currentData[0]) {
                                    case "L":
                                        dataLifes.put(Integer.parseInt(currentData[1]), Integer.parseInt(currentData[2]));
                                        break;
                                    case "B":
                                        HashMap<BonusType, Boolean> bonuses = new HashMap<>();
                                        bonuses.put(BonusType.BOOTS, "true".equals(currentData[2]));
                                        bonuses.put(BonusType.LARGEBOMB, "true".equals(currentData[3]));
                                        dataBonuses.put(Integer.parseInt(currentData[1]), bonuses);
                                        break;
                                    case "M":
                                        Position pos = new Position(Integer.parseInt(currentData[1]), Integer.parseInt(currentData[2]));
                                        ArrayList<String> mapObjects = new ArrayList<>();
                                        if (currentData.length > 3) {
                                            for (int j=3; j<currentData.length; j++) {
                                                mapObjects.add(currentData[j]);
                                            }
                                        }
                                        dataMap.put(pos, mapObjects);
                                }
                            }
                            if (!dataLifes.isEmpty()) callback.updateLifes(dataLifes);
                            if (!dataBonuses.isEmpty()) callback.updateBonuses(dataBonuses);
                            if (!dataMap.isEmpty()) callback.updateMap(dataMap);
                            break;
                        case "INFO":
                            ArrayList<Integer> infoIDs = new ArrayList<>();
                            HashMap<Integer, String> infoNames = new HashMap<>();
                            HashMap<Integer, PlayerColor> infoColors = new HashMap<>();
                            for (int i=1; i<serverInField.length; i++) {
                                String[] currentInfo = serverInField[i].split("_");
                                infoIDs.add(Integer.parseInt(currentInfo[0]));
                                infoNames.put(Integer.parseInt(currentInfo[0]), currentInfo[1]);
                                infoColors.put(Integer.parseInt(currentInfo[0]), PlayerColor.valueOf(currentInfo[2].toUpperCase()));
                            }
                            if (!infoIDs.isEmpty()) callback.updateIDs(infoIDs);
                            if (!infoNames.isEmpty()) callback.updateNames(infoNames);
                            if (!infoColors.isEmpty()) callback.updateColors(infoColors);
                            break;
                        case "HELLO":
                            callback.setStatus("Connected");
                            playerID = Integer.parseInt(serverInField[1]);
                            if (!playerName.equals("")) outToServer.writeBytes("HI_MY_NAME_IS "+playerName+"\n");
                            break;
                        case "END":
                            callback.gameOver(Integer.parseInt(serverInField[1]));
                            logout = true;
                            break;
                    }
                }
            }
            callback.setStatus("Game over");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
