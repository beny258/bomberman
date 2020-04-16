
package bomberman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the game logic.
 * @author David Benes
 */
public class GameLogic {
    
    /**
     * Determines how many lifes will players have at the start of a game.
     */
    private final int PLAYER_START_LIFES = 3;
    
    /**
     * Determines how long it takes bomb to boom.
     */
    private final int BOMB_TIME = 20000;
    
    /**
     * Determines how long it takes fire to disappear.
     */
    private final int FIRE_TIME = BOMB_TIME / 3;
    
    /**
     * Determines how long after a player moves is that player unable to move again.
     */
    private final int PLAYER_MOVE_TIME = 500;
    
    /**
     * Determines how long after a player loses life is that player invulnerable.
     */
    private final int PLAYER_LIFE_LOST_TIME = FIRE_TIME + 15000;
    
    /**
     * Determines how many fields around a basic bomb will be in fire after the bomb booms.
     */
    private final int BOMB_RANGE = 3;
    
    /**
     * Determines how many fields around a large bomb will be in fire after the bomb booms.
     */
    private final int LARGE_BOMB_RANGE = 6;
    
    /**
     * Determines how many bombs may have been dropped by a single player at the same time.
     */
    private final int PLAYER_MAX_BOMB_COUNT = 2;
    
    /**
     * Determines the probability of bonus occurrence. In every X (bonus probability value) wooden boxes should be 3 bonuses (one of each type).
     */
    private final int BONUS_PROBABILITY = 150;
    
    /**
     * Determines the probability of wooden box occurrence. On every X (wooden box probability value) fields should be X-1 wooden boxes.
     */
    private final int WOODENBOX_PROBABILITY = 8;
    
    /**
     * HashMap of all updates on game map. It's a pair of position and ArrayList of map objects.
     */
    private final HashMap<Position, ArrayList<MapObjectType>> mapUpdates = new HashMap<>();
    
    /**
     * HashMap of all updates of each player's life count. It's a pair of player's ID and life count.
     */
    private final HashMap<Integer, Integer> lifesUpdates = new HashMap<>();

    /**
     * HashMap of all updates of each player's bonuses. It's a pair of player's ID and HashMap of bonus type and boolean.
     */
    private final HashMap<Integer, HashMap<BonusType, Boolean>> bonusesUpdates = new HashMap<>();
    
    /**
     * Variable containing an instance of class GameMap.
     */
    private GameMap gameMap;
    
    /**
     * Creates new game map and fills it with players, wooden boxes and iron boxes. Returns player colors.
     * @param xSize number of fields in a row (map width), the value must be odd and at least 5.
     * @param ySize number of fields in a column (map height), the value must be odd and at least 5.
     * @param playersID set of player IDs.
     * @return HashMap of pairs player ID number and PlayerColor.
     * @throws Exception 
     */
    public HashMap<Integer, PlayerColor> newMap(int xSize, int ySize, HashSet<Integer> playersID) throws Exception {
        HashMap<Integer, PlayerColor> retval = new HashMap<>();
        if (xSize < 5 || ySize < 5 || xSize%2 == 0 || ySize%2 == 0) throw new Exception("Map sizes should be odd and at least 5x5.");
        gameMap = new GameMap(xSize, ySize);
        for (int x=0; x<xSize; x++) {
            for (int y=0; y<ySize; y++) {
                if (x == 0 || y == 0 || x == xSize-1 || y == ySize-1 || ((x)%2 == 0 && (y)%2 == 0)) gameMap.addIronBox(x, y);
                else if ((x > 2 && x < xSize-3) || (y > 2 && y < ySize-3)) {
                    int rnd = ThreadLocalRandom.current().nextInt(0, WOODENBOX_PROBABILITY);
                    if (rnd > 0) gameMap.addWoodenBox(x, y);
                }
            }
        }
        int j = 0;
        for (Integer i:playersID) {
            switch(j) {
                case 0:
                    gameMap.addPlayer(1, 1, PlayerColor.BLUE, Direction.RIGHT, i, PLAYER_START_LIFES);
                    retval.put(i, PlayerColor.BLUE);
                    break;
                case 1:
                    gameMap.addPlayer(xSize-2, ySize-2, PlayerColor.GREEN, Direction.LEFT, i, PLAYER_START_LIFES);
                    retval.put(i, PlayerColor.GREEN);
                    break;
                case 2:
                    gameMap.addPlayer(1, ySize-2, PlayerColor.RED, Direction.DOWN, i, PLAYER_START_LIFES);
                    retval.put(i, PlayerColor.RED);
                    break;
                case 3:
                    gameMap.addPlayer(xSize-2, 1, PlayerColor.WHITE, Direction.UP, i, PLAYER_START_LIFES);
                    retval.put(i, PlayerColor.WHITE);
                    break;
                default:
                    throw new Exception("Too many players!");
            }
            j++;
        }
        return retval;
    }
    
    /**
     * Getter for game map.
     * @return HashMap containing pairs of position and ArrayList of map objects.
     * @throws Exception 
     */
    public HashMap<Position, ArrayList<MapObjectType>> getMap() throws Exception {
        HashMap<Position, ArrayList<MapObjectType>> retval = new HashMap<>();
        for (int x=0; x<gameMap.getXSize(); x++) {
            for (int y=0; y<gameMap.getYSize(); y++) {
                retval.put(new Position(x, y), gameMap.getMapObjects(x, y));
            }
        }
        return retval;
    }
    
    /**
     * Getter for each player's life count.
     * @return HashMap containing pairs of player's ID and player's life count.
     */
    public HashMap<Integer, Integer> getLifes() {
        HashMap<Integer, Integer> retval = new HashMap<>();
        for (Player player:gameMap.getPlayers()) {
            retval.put(player.getID(), player.getLifes());
        }
        return retval;
    }
    
    /**
     * Getter for each player's bonuses.
     * @return HashMap containing pairs of player's ID and HashMap of BonusType and Boolean.
     */
    public HashMap<Integer, HashMap<BonusType, Boolean>> getBonuses() {
        HashMap<Integer, HashMap<BonusType, Boolean>> retval = new HashMap<>();
        for (Player player:gameMap.getPlayers()) {
            HashMap<BonusType, Boolean> bonuses = new HashMap<>();
            bonuses.put(BonusType.BOOTS, player.hasBonusBoots());
            bonuses.put(BonusType.LARGEBOMB, player.getBombType().equals(BombType.LARGE));
            retval.put(player.getID(), bonuses);
        }
        return retval;
    }
    
    /**
     * Checks if the game should end.
     * @return true if there is 1 player or less in the game.
     */
    public boolean isEnd() {
        return gameMap.getPlayers().size() <= 1;
    }
    
    /**
     * Checks if player may make move in some direction.
     * @param player player whose move should be checked.
     * @param direction direction of the move.
     * @return true if there is nothing in the path and the move may be done.
     * @throws Exception 
     */
    private boolean checkMove(Player player, Direction direction) throws Exception {
        int playerX = player.getPos().getX();
        int playerY = player.getPos().getY();
        ArrayList<MapObjectType> mapObjects = new ArrayList<>();
        switch(direction) {
            case UP:
                mapObjects = gameMap.getMapObjects(playerX, playerY-1);
                break;
            case DOWN:
                mapObjects = gameMap.getMapObjects(playerX, playerY+1);
                break;
            case RIGHT:
                mapObjects = gameMap.getMapObjects(playerX+1, playerY);
                break;
            case LEFT:
                mapObjects = gameMap.getMapObjects(playerX-1, playerY);
                break;
        }
        for (MapObjectType mapObj:mapObjects) {
            if (mapObj.isPlayer() || mapObj.isBomb() || mapObj == MapObjectType.IRONBOX) return false;
        }
        if (mapObjects.contains(MapObjectType.WOODENBOX)) return player.hasBonusBoots();
        return true;
    }
    
    /**
     * Checks if player may drop bomb at his location.
     * @param player player who wants to drop a bomb.
     * @return true if there is no bomb already on the field.
     */
    private boolean checkBomb(Player player) throws Exception {
        ArrayList<MapObjectType> objects = gameMap.getMapObjects(player.getPos().getX(), player.getPos().getY());
        return (objects.size() == 1 && objects.get(0).isPlayer());
    }
    
    /**
     * Increases the value of all bomb alarms. Creates new fires.
     * @throws java.lang.Exception
     */
    public void boom() throws Exception {
        HashSet<Bomb> bombsToRemove = new HashSet<>();
        for (Bomb bomb:gameMap.getBombs()) {
            bomb.alarmClock();
            if (bomb.getAlarm() >= BOMB_TIME) {
                int range = (bomb.getType() == BombType.LARGE) ? LARGE_BOMB_RANGE : BOMB_RANGE;
                int bombX = bomb.getPos().getX();
                int bombY = bomb.getPos().getY();
                
                // up
                for (int i=1; i<=range; i++) {
                    if (gameMap.getMapObjects(bombX, bombY-i).contains(MapObjectType.IRONBOX)) break;
                    gameMap.addFire(bombX, bombY-i);
                    mapUpdates.put(new Position(bombX, bombY-i), gameMap.getMapObjects(bombX, bombY-i));
                    if (gameMap.getMapObjects(bombX, bombY-i).contains(MapObjectType.WOODENBOX)) break;
                }
                
                // down
                for (int i=1; i<=range; i++) {
                    if (gameMap.getMapObjects(bombX, bombY+i).contains(MapObjectType.IRONBOX)) break;
                    gameMap.addFire(bombX, bombY+i);
                    mapUpdates.put(new Position(bombX, bombY+i), gameMap.getMapObjects(bombX, bombY+i));
                    if (gameMap.getMapObjects(bombX, bombY+i).contains(MapObjectType.WOODENBOX)) break;
                }
                
                // right
                for (int i=1; i<=range; i++) {
                    if (gameMap.getMapObjects(bombX+i, bombY).contains(MapObjectType.IRONBOX)) break;
                    gameMap.addFire(bombX+i, bombY);
                    mapUpdates.put(new Position(bombX+i, bombY), gameMap.getMapObjects(bombX+i, bombY));
                    if (gameMap.getMapObjects(bombX+i, bombY).contains(MapObjectType.WOODENBOX)) break;
                }
                
                // left
                for (int i=1; i<=range; i++) {
                    if (gameMap.getMapObjects(bombX-i, bombY).contains(MapObjectType.IRONBOX)) break;
                    gameMap.addFire(bombX-i, bombY);
                    mapUpdates.put(new Position(bombX-i, bombY), gameMap.getMapObjects(bombX-i, bombY));
                    if (gameMap.getMapObjects(bombX-i, bombY).contains(MapObjectType.WOODENBOX)) break;
                }
                
                bomb.getOwner().bombBoom();
                gameMap.addFire(bombX, bombY);
                bombsToRemove.add(bomb);
            }
        }
        for (Bomb bomb:bombsToRemove) {
            int bombX = bomb.getPos().getX();
            int bombY = bomb.getPos().getY();
            gameMap.removeBomb(bomb);
            mapUpdates.put(new Position(bombX, bombY), gameMap.getMapObjects(bombX, bombY));
        }
    }
    
    /**
     * Drops a life of all players standing on a fire. Removes all players with 0 or less lifes.
     * @throws Exception 
     */
    public void die() throws Exception {
        HashSet<Player> playersToRemove = new HashSet<>();
        for (Player player:gameMap.getPlayers()) {
            if (player == null) return;
            int playerX = player.getPos().getX();
            int playerY = player.getPos().getY();
            if (gameMap.getMapObjects(playerX, playerY).contains(MapObjectType.FIRE) && !player.isLoosingLife()) {
                player.loseLife();
                player.setLifeLostAlarm(PLAYER_LIFE_LOST_TIME);
                lifesUpdates.put(player.getID(), player.getLifes());
            }
            if (player.getLifes() <= 0) {
                playersToRemove.add(player);
                mapUpdates.put(new Position(playerX, playerY), gameMap.getMapObjects(playerX, playerY));
            }
        }
        for (Player player:playersToRemove) {
            int playerX = player.getPos().getX();
            int playerY = player.getPos().getY();
            gameMap.removePlayer(player);
            mapUpdates.put(new Position(playerX, playerY), gameMap.getMapObjects(playerX, playerY));
        }
    }
    
    /**
     * Removes all bonuses on fires and any player standing on some bonus picks up that bonus.
     * @throws Exception 
     */
    public void pickupBonuses() throws Exception {
        HashSet<Bonus> bonusesToRemove = new HashSet<>();
        for (Bonus bonus:gameMap.getBonuses()) {
            for (Player player:gameMap.getPlayers()) {
                if (player.getPos().equals(bonus.getPos())) {
                    switch(bonus.getType()) {
                        case BOOTS:
                            player.setBonusBoots(true);
                            break;
                        case LARGEBOMB:
                            player.setBombType(BombType.LARGE);
                            break;
                        case EXTRALIFE:
                            player.addLife();
                            lifesUpdates.put(player.getID(), player.getLifes());
                            break;
                    }
                    HashMap<BonusType, Boolean> playerBonuses = new HashMap<>();
                    playerBonuses.put(BonusType.BOOTS, player.hasBonusBoots());
                    playerBonuses.put(BonusType.LARGEBOMB, player.getBombType()==BombType.LARGE);
                    bonusesUpdates.put(player.getID(), playerBonuses);
                    bonusesToRemove.add(bonus);
                }
            }
            if (gameMap.getMapObjects(bonus.getPos().getX(), bonus.getPos().getY()).contains(MapObjectType.FIRE)) {
                bonusesToRemove.add(bonus);
            }
        }
        for (Bonus bonus:bonusesToRemove) {
            int bonusX = bonus.getPos().getX();
            int bonusY = bonus.getPos().getY();
            gameMap.removeBonus(bonus);
            mapUpdates.put(new Position(bonusX, bonusY), gameMap.getMapObjects(bonusX, bonusY));
        }
    }
    
    /**
     * Removes all fires that have been on map for too long.
     * @throws java.lang.Exception
     */
    public void removeFires() throws Exception {
        HashSet<Fire> fires = gameMap.getFires();
        if (fires.isEmpty()) return;
        HashSet<Fire> firesToRemove = new HashSet<>();
        HashSet<WoodenBox> boxesToRemove = new HashSet<>();
        for (Fire fire:fires) {
            fire.alarmClock();
            if (fire.getAlarm() >= FIRE_TIME) {
                int fireX = fire.getPos().getX();
                int fireY = fire.getPos().getY();
                for (WoodenBox woodenBox:gameMap.getWoodenBoxes()) {
                    if (fireX == woodenBox.getPos().getX() && fireY == woodenBox.getPos().getY()) {
                        boxesToRemove.add(woodenBox);
                    }
                }
                firesToRemove.add(fire);
            }
        }
        for (WoodenBox woodenBox:boxesToRemove) {
            int rnd = ThreadLocalRandom.current().nextInt(0, BONUS_PROBABILITY / gameMap.getPlayers().size());
            switch(rnd) {
                case 0:
                    gameMap.addBonus(woodenBox.getPos().getX(), woodenBox.getPos().getY(), BonusType.BOOTS);
                    break;
                case 1:
                    gameMap.addBonus(woodenBox.getPos().getX(), woodenBox.getPos().getY(), BonusType.EXTRALIFE);
                    break;
                case 2:
                    gameMap.addBonus(woodenBox.getPos().getX(), woodenBox.getPos().getY(), BonusType.LARGEBOMB);
                    break;
            }
            gameMap.removeWoodenBox(woodenBox);
        }
        for (Fire fire:firesToRemove) {
            int fireX = fire.getPos().getX();
            int fireY = fire.getPos().getY();
            gameMap.removeFire(fire);
            mapUpdates.put(new Position(fireX, fireY), gameMap.getMapObjects(fireX, fireY));
        }
    }
    
    /**
     * Moves with all player that want to move.
     * @throws Exception 
     */
    public void movePlayers() throws Exception {
        for (Player player:gameMap.getPlayers()) {
            player.alarmClock();
            if (player.isMoving() && player.isMovable()) {
                if (checkMove(player, player.getDirection())) {
                    int plX = player.getPos().getX();
                    int plY = player.getPos().getY();
                    player.move(player.getDirection());
                    player.setMoveAlarm(PLAYER_MOVE_TIME);
                    mapUpdates.put(new Position(plX, plY), gameMap.getMapObjects(plX, plY));
                    mapUpdates.put(player.getPos(), gameMap.getMapObjects(player.getPos().getX(), player.getPos().getY()));
                }
                player.setMoving(false);
            }
        }
    }
    
    /**
     * Drops all bombs of players that want to drop a bomb.
     * @throws java.lang.Exception
     */
    public void dropBombs() throws Exception {
        for (Player player:gameMap.getPlayers()) {
            if (player.isBombDropping() && player.getBombDropCounter() < PLAYER_MAX_BOMB_COUNT) {
                if (checkBomb(player)) {
                    gameMap.addBomb(player.getPos().getX(), player.getPos().getY(), player);
                    player.bombDrop();
                    mapUpdates.put(player.getPos(), gameMap.getMapObjects(player.getPos().getX(), player.getPos().getY()));
                }
                player.setBombDropping(false);
            }
        }
    }
    
    /**
     * Sets a player to state of moving.
     * @param id
     * @param direction
     * @throws Exception 
     */
    public void movePlayer(int id, Direction direction) throws Exception {
        Player player = gameMap.getPlayerByID(id);
        if (player != null) {
            player.setDirection(direction);
            player.setMoving(true);
        }
    }
    
    /**
     * Sets a player to state of dropping a bomb.
     * @param id
     * @throws Exception 
     */
    public void dropBomb(int id) throws Exception {
        Player player = gameMap.getPlayerByID(id);
        if (player != null) player.setBombDropping(true);
    }

    public HashMap<Position, ArrayList<MapObjectType>> getMapUpdates() {
        return mapUpdates;
    }

    public HashMap<Integer, Integer> getLifesUpdates() {
        return lifesUpdates;
    }

    public HashMap<Integer, HashMap<BonusType, Boolean>> getBonusesUpdates() {
        return bonusesUpdates;
    }
    
    /**
     * Clears all updates maps.
     */
    public void clearUpdates() {
        mapUpdates.clear();
        lifesUpdates.clear();
        bonusesUpdates.clear();
    }
    
    /**
     * Getter for ID number of the last living player. Returns -1 if there isn't exactly 1 player in game.
     * @return integer representing the ID number of a player winning a game.
     */
    public int getWinnerID() {
        HashSet<Player> players = gameMap.getPlayers();
        if (players.size() == 1) {
            for (Player pl:players) {
                return pl.getID();
            }
        }
        return -1;
    }
}
