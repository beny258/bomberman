
package bomberman;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class containing all game map data.
 * @author David Benes
 */
public class GameMap {
    
    private final int xSize;
    
    private final int ySize;
    
    private final HashSet<Player> players;

    private final HashSet<Bomb> bombs;

    private final HashSet<Fire> fires;

    private final HashSet<Bonus> bonuses;
    
    private final HashSet<WoodenBox> woodenBoxes;
    
    private final HashSet<IronBox> ironBoxes;

    public GameMap(int xSize, int ySize) {
        this.players = new HashSet<>();
        this.bombs = new HashSet<>();
        this.fires = new HashSet<>();
        this.bonuses = new HashSet<>();
        this.woodenBoxes = new HashSet<>();
        this.ironBoxes = new HashSet<>();
        this.xSize = xSize;
        this.ySize = ySize;
    }
    
    /**
     * Getter for objects on specified coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return List of MapObjectTypes.
     * @throws Exception 
     */
    public ArrayList<MapObjectType> getMapObjects(int x, int y) throws Exception {
        ArrayList<MapObjectType> retval = new ArrayList<>();
        for (WoodenBox woodenbox:woodenBoxes) {
            if (woodenbox.getPos().getX()==x && woodenbox.getPos().getY()==y) retval.add(MapObjectType.WOODENBOX);
        }
        for (IronBox ironBox:ironBoxes) {
            if (ironBox.getPos().getX()==x && ironBox.getPos().getY()==y) retval.add(MapObjectType.IRONBOX);
        }
        for (Bomb bomb:bombs) {
            if (bomb.getPos().getX()==x && bomb.getPos().getY()==y) {
                switch(bomb.getType()) {
                    case BASIC:
                        retval.add(MapObjectType.BOMBBASIC);
                        break;
                    case LARGE:
                        retval.add(MapObjectType.BOMBLARGE);
                        break;
                }
            }
        }
        for (Bonus bonus:bonuses) {
            if (bonus.getPos().getX()==x && bonus.getPos().getY()==y) {
                switch(bonus.getType()) {
                    case BOOTS:
                        retval.add(MapObjectType.BONUSBOOTS);
                        break;
                    case EXTRALIFE:
                        retval.add(MapObjectType.BONUSEXTRALIFE);
                        break;
                    case LARGEBOMB:
                        retval.add(MapObjectType.BONUSBOMBLARGE);
                        break;
                }
            }
        }
        for (Fire fire:fires) {
            if (fire.getPos().getX()==x && fire.getPos().getY()==y) retval.add(MapObjectType.FIRE);
        }
        for (Player player:players) {
            if (player.getPos().getX()==x && player.getPos().getY()==y) {
                switch(player.getColor()) {
                    case BLUE:
                        switch(player.getDirection()) {
                            case UP:
                                retval.add(MapObjectType.PLAYERBLUEBACK);
                                break;
                            case DOWN:
                                retval.add(MapObjectType.PLAYERBLUEFRONT);
                                break;
                            case RIGHT:
                                retval.add(MapObjectType.PLAYERBLUERIGHT);
                                break;
                            case LEFT:
                                retval.add(MapObjectType.PLAYERBLUELEFT);
                                break;
                        }
                        break;
                    case GREEN:
                        switch(player.getDirection()) {
                            case UP:
                                retval.add(MapObjectType.PLAYERGREENBACK);
                                break;
                            case DOWN:
                                retval.add(MapObjectType.PLAYERGREENFRONT);
                                break;
                            case RIGHT:
                                retval.add(MapObjectType.PLAYERGREENRIGHT);
                                break;
                            case LEFT:
                                retval.add(MapObjectType.PLAYERGREENLEFT);
                                break;
                        }
                        break;
                    case RED:
                        switch(player.getDirection()) {
                            case UP:
                                retval.add(MapObjectType.PLAYERREDBACK);
                                break;
                            case DOWN:
                                retval.add(MapObjectType.PLAYERREDFRONT);
                                break;
                            case RIGHT:
                                retval.add(MapObjectType.PLAYERREDRIGHT);
                                break;
                            case LEFT:
                                retval.add(MapObjectType.PLAYERREDLEFT);
                                break;
                        }
                        break;
                    case WHITE:
                        switch(player.getDirection()) {
                            case UP:
                                retval.add(MapObjectType.PLAYERWHITEBACK);
                                break;
                            case DOWN:
                                retval.add(MapObjectType.PLAYERWHITEFRONT);
                                break;
                            case RIGHT:
                                retval.add(MapObjectType.PLAYERWHITERIGHT);
                                break;
                            case LEFT:
                                retval.add(MapObjectType.PLAYERWHITELEFT);
                                break;
                        }
                        break;
                }
            }
        }
        return retval;
    }
    
    /**
     * Getter for player with specified ID number. Returns null if no such player exists.
     * @param id ID number of a Player.
     * @return Player with specified ID number.
     */
    public Player getPlayerByID(int id) {
        for (Player player:players) {
            if (player.getID() == id) return player;
        }
        return null;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public HashSet<Bomb> getBombs() {
        return bombs;
    }

    public HashSet<Fire> getFires() {
        return fires;
    }

    public HashSet<Bonus> getBonuses() {
        return bonuses;
    }

    public HashSet<WoodenBox> getWoodenBoxes() {
        return woodenBoxes;
    }

    public HashSet<IronBox> getIronBoxes() {
        return ironBoxes;
    }

    public void addPlayer(int posX, int posY, PlayerColor color, Direction dir, int id, int lifes) {
        this.players.add(new Player(posX, posY, color, dir, id, lifes));
    }
    
    public void addBomb(int posX, int posY, Player player) {
        this.bombs.add(new Bomb(posX, posY, player));
    }
    
    public void addFire(int posX, int posY) {
        this.fires.add(new Fire(posX, posY));
    }
    
    public void addBonus(int posX, int posY, BonusType type) {
        this.bonuses.add(new Bonus(posX, posY, type));
    }
    
    public void addWoodenBox(int posX, int posY) {
        this.woodenBoxes.add(new WoodenBox(posX, posY));
    }
    
    public void addIronBox(int posX, int posY) {
        this.ironBoxes.add(new IronBox(posX, posY));
    }
    
    public void removeWoodenBox(WoodenBox woodenBox) {
        this.woodenBoxes.remove(woodenBox);
    }
    
    public void removePlayer(Player player) {
        this.players.remove(player);
    }
    
    public void removeBonus(Bonus bonus) {
        this.bonuses.remove(bonus);
    }
    
    public void removeFire(Fire fire) {
        this.fires.remove(fire);
    }
    
    public void removeBomb(Bomb bomb) {
        this.bombs.remove(bomb);
    }
}
