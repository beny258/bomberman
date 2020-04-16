
package bomberman;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Benes
 */
public class Server extends Thread {
    
    /**
     * ArrayList of all active server threads.
     */
    private final HashSet<ServerThread> serverThreadList;
    
    private ServerSocket welcomeSocket;
    
    private final GameLogic gameLogic;
    
    private final int portNumber;
    
    private final int maxPlayers;
    
    private final int mapSizeX;
    
    private final int mapSizeY;
    
    private ServerCallback callback;
    
    private String status;

    /**
     * Creates the Server.
     * @param portNumber number of port on which communication is made.
     * @param maxPlayers number of players in game.
     * @param mapSizeX width of the game map (number of fields).
     * @param mapSizeY height of the game map (number of fields).
     * @throws IOException 
     */
    public Server(int portNumber, int maxPlayers, int mapSizeX, int mapSizeY) throws IOException {
        this.gameLogic = new GameLogic();
        this.serverThreadList = new HashSet<>();
        this.portNumber = portNumber;
        this.maxPlayers = maxPlayers;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        if (callback != null) callback.updateStatus("Starting the server");
        if (portNumber < 1000 || portNumber > 9999) {
            throw new IOException("Port number must be between 1000 and 9999!");
        }
        if (maxPlayers < 2 || maxPlayers > 4) {
            throw new IOException("Maximum of players must be between 2 and 4!");
        }
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getStatus() {
        return status;
    }

    public int getPortNumber() {
        return portNumber;
    }
    
    public void registerCallback(ServerCallback c) {
        this.callback = c;
    }
    
    /**
     * Checks if ID assigning to some client is not already in use.
     * @param id ID number to check.
     * @return false if ID is used by another client.
     */
    private boolean checkID(int id) {
        for (ServerThread serverThread:serverThreadList) {
            if (serverThread.getClientID() == id) return false;
        }
        return true;
    }
    
    /**
     * Removes a thread from from ArrayList 'serverThreadList'.
     * @param st ServerThread to remove.
     */
    public void removeServerThread(ServerThread st) {
        this.serverThreadList.remove(st);
        if (callback != null) callback.updatePlayersCount();
    }

    public HashSet<ServerThread> getServerThreadList() {
        return this.serverThreadList;
    }
    
    @Override
    public void run() {
        try {
            super.run();
            
            this.welcomeSocket = new ServerSocket(portNumber);
            
            if (callback != null) {
                callback.updateStatus("Waiting for players");
                callback.log("Server running. Waiting for players to connect.");
            }
            
            HashSet<Integer> playersID = new HashSet<>();
            
            // client accept loop
            while (serverThreadList.size() < maxPlayers) {
                Socket connectionSocket = welcomeSocket.accept();
                int id;
                do {
                    id = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE); // this is not an atomic operation, error may occure
                } while (!checkID(id));
                playersID.add(id);
                ServerThread newThread = new ServerThread(connectionSocket, "player" + id, this, id);
                this.serverThreadList.add(newThread);
                newThread.start();
                if (callback != null) {
                    callback.log("New player connected. ID: "+newThread.getClientID()+".");
                    callback.updatePlayersCount();
                }
            }
            
            if (callback != null) {
                callback.log("All players ready. Preparing the game map.");
                callback.updateStatus("Preparing the game map");
            }
            
            HashMap<Integer, PlayerColor> playerColors = gameLogic.newMap(mapSizeX, mapSizeY, playersID);
            for (ServerThread st:serverThreadList) {
                st.sendInfo(playerColors);
                st.sendData(false);
            }
            
            if (callback != null) {
                callback.log("Game map ready. Starting the game.");
                callback.updateStatus("Game running");
            }
            
            // main game loop
            do {
                gameLogic.movePlayers();
                gameLogic.removeFires();
                gameLogic.boom();
                gameLogic.die();
                gameLogic.pickupBonuses();
                gameLogic.dropBombs();
                
                for (ServerThread st:serverThreadList) {
                    st.sendData(true);
                }
                gameLogic.clearUpdates();
            } while (!gameLogic.isEnd());
            
            if (callback != null) {
                callback.log("Game has ended.");
                callback.updateStatus("Game over");
            }
            
            for (ServerThread st:serverThreadList) {
                st.gameOver(gameLogic.getWinnerID());
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}