
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
 * Manages the thread of server which directly comunicates with just one client.
 * @author David Benes
 */
public class ServerThread extends Thread {

    /**
     * Client's connection socket.
     */
    private final Socket connectionSocket;
    
    private final BufferedReader inFromClient;
    
    private final DataOutputStream outToClient;

    /**
     * Client's name.
     */
    private String clientName;
    
    /**
     * Client's ID number.
     */
    private final int clientID;
    
    /**
     * Server to communicate with.
     */
    private final Server server;
    
    /**
     * Determines if a thread should end or keep running.
     */
    private boolean logout = false;

    /**
     * Sets variable 'logout' to false. Informs the client that game has ended and who won.
     * @param winnerID ID number of game winner.
     * @throws java.io.IOException
     */
    public void gameOver(int winnerID) throws IOException {
        this.logout = true;
        outToClient.writeBytes("END "+winnerID+"\n");
    }
    
    /**
     * Returns the value of variable 'clientName'.
     * @return 
     */
    public String getClientName() {
        return this.clientName;
    }
    
    /**
     * Returns the client's ID (value of variable 'clientID').
     * @return 
     */
    public int getClientID() {
        return this.clientID;
    }

    /**
     * Sets the value of variable 'clientName'.
     * @param clientName 
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    /**
     * Sends the starting informations to client (players IDs, names and colors).
     * @param colors HashMap of pairs of player ID and player color.
     * @throws IOException 
     */
    public void sendInfo(HashMap<Integer, PlayerColor> colors) throws IOException {
        String clientOut = "INFO";
        for (ServerThread st:server.getServerThreadList()) {
            clientOut += " "+st.getClientID()+"_"+st.getClientName()+"_"+colors.get(st.getClientID()).toString();
        }
        outToClient.writeBytes(clientOut + "\n");
    }
    
    /**
     * Sends game data (map, bonuses and lifes) to client.
     * @param onlyUpdates if true, only last updates will be sent, instead if all the data.
     * @throws IOException
     * @throws Exception 
     */
    public void sendData(boolean onlyUpdates) throws IOException, Exception {
        String clientOut = "DATA";
        HashMap<Integer, Integer> playersLifes = onlyUpdates ? server.getGameLogic().getLifesUpdates() : server.getGameLogic().getLifes();
        HashMap<Integer, HashMap<BonusType, Boolean>> playersBonuses = onlyUpdates ? server.getGameLogic().getBonusesUpdates() : server.getGameLogic().getBonuses();
        HashMap<Position, ArrayList<MapObjectType>> map = onlyUpdates ? server.getGameLogic().getMapUpdates() : server.getGameLogic().getMap();
        for (Integer playerID:playersLifes.keySet()) {
            clientOut += " L_"+playerID.toString()+"_"+playersLifes.get(playerID).toString();
        }
        for (Integer playerID:playersBonuses.keySet()) {
            clientOut += " B_"+playerID.toString()+"_"+playersBonuses.get(playerID).get(BonusType.BOOTS)+"_"+playersBonuses.get(playerID).get(BonusType.LARGEBOMB);
        }
        for (Position pos:map.keySet()) {
            clientOut += " M_"+pos.getX()+"_"+pos.getY();
            for (MapObjectType currentObject:map.get(pos)) {
                clientOut += "_"+currentObject.toString();
            }
        }
        outToClient.writeBytes(clientOut + "\n");
    }

    /**
     * Constructor of class ServerThread.
     * @param connectionSocket
     * @param clientName name of the client.
     * @param server 
     * @param clientID ID number of the client.
     * @throws java.io.IOException 
     */
    public ServerThread(Socket connectionSocket, String clientName, Server server, int clientID) throws IOException {
        this.connectionSocket = connectionSocket;
        this.outToClient = new DataOutputStream(this.connectionSocket.getOutputStream());
        this.inFromClient = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
        this.clientName = clientName;
        this.server = server;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            String clientIn;
            super.run();
            this.outToClient.writeBytes("HELLO "+clientID+"\n");
            while (!logout) {
                clientIn = inFromClient.readLine();
                if (logout) break;
                if (!(clientIn == null) && !clientIn.isEmpty()) {
                    String[] clientInArray = clientIn.split(" ");
                    switch (clientInArray[0]) {
                        case "MOVEUP":
                            server.getGameLogic().movePlayer(this.clientID, Direction.UP);
                            break;
                        case "MOVEDOWN":
                            server.getGameLogic().movePlayer(this.clientID, Direction.DOWN);
                            break;
                        case "MOVERIGHT":
                            server.getGameLogic().movePlayer(this.clientID, Direction.RIGHT);
                            break;
                        case "MOVELEFT":
                            server.getGameLogic().movePlayer(this.clientID, Direction.LEFT);
                            break;
                        case "DROPBOMB":
                            server.getGameLogic().dropBomb(this.clientID);
                            break;
                        case "GETDATA":
                            this.sendData(false);
                            break;
                        case "LOGOUT":
                            logout = true;
                            break;
                        case "HI_MY_NAME_IS":
                            clientName = clientInArray[1];
                            break;
                        case "BYE":
                            break;
                        default:
                            break;
                    }
                }
            }
            connectionSocket.close();
            server.removeServerThread(this);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
