
package bomberman;

/**
 *
 * @author David Benes
 */
public enum MapObjectType {
    BOMBBASIC, BOMBLARGE, BONUSBOOTS, BONUSEXTRALIFE, BONUSBOMBLARGE, FIRE, IRONBOX, 
    PLAYERBLUEFRONT, PLAYERBLUEBACK, PLAYERBLUERIGHT, PLAYERBLUELEFT,
    PLAYERGREENFRONT, PLAYERGREENBACK, PLAYERGREENRIGHT, PLAYERGREENLEFT,
    PLAYERREDFRONT, PLAYERREDBACK, PLAYERREDRIGHT, PLAYERREDLEFT,
    PLAYERWHITEFRONT, PLAYERWHITEBACK, PLAYERWHITERIGHT, PLAYERWHITELEFT,
    WOODENBOX;
    
    public boolean isPlayer() {
        return this == PLAYERBLUEFRONT || this == PLAYERBLUEBACK || this == PLAYERBLUERIGHT || this == PLAYERBLUELEFT ||
    this == PLAYERGREENFRONT || this == PLAYERGREENBACK || this == PLAYERGREENRIGHT || this == PLAYERGREENLEFT ||
    this == PLAYERREDFRONT || this == PLAYERREDBACK || this == PLAYERREDRIGHT || this == PLAYERREDLEFT ||
    this == PLAYERWHITEFRONT || this == PLAYERWHITEBACK || this == PLAYERWHITERIGHT || this == PLAYERWHITELEFT;
    }
    
    public boolean isBomb() {
        return this == BOMBBASIC || this == BOMBLARGE;
    }

    @Override
    public String toString() {
        switch(this) {
            case BOMBBASIC:
                return "bomb-basic";
            case BOMBLARGE:
                return "bomb-large";
            case BONUSBOOTS:
                return "bonus-boots";
            case BONUSEXTRALIFE:
                return "bonus-extralife";
            case BONUSBOMBLARGE:
                return "bonus-bomb-large";
            case FIRE:
                return "fire";
            case IRONBOX:
                return "ironbox";
            case PLAYERBLUEFRONT:
                return "player-blue-front";
            case PLAYERBLUEBACK:
                return "player-blue-back";
            case PLAYERBLUERIGHT:
                return "player-blue-right";
            case PLAYERBLUELEFT:
                return "player-blue-left";
            case PLAYERGREENFRONT:
                return "player-green-front";
            case PLAYERGREENBACK:
                return "player-green-back";
            case PLAYERGREENRIGHT:
                return "player-green-right";
            case PLAYERGREENLEFT:
                return "player-green-left";
            case PLAYERREDFRONT:
                return "player-red-front";
            case PLAYERREDBACK:
                return "player-red-back";
            case PLAYERREDRIGHT:
                return "player-red-right";
            case PLAYERREDLEFT:
                return "player-red-left";
            case PLAYERWHITEFRONT:
                return "player-white-front";
            case PLAYERWHITEBACK:
                return "player-white-back";
            case PLAYERWHITERIGHT:
                return "player-white-right";
            case PLAYERWHITELEFT:
                return "player-white-left";
            case WOODENBOX:
                return "woodenbox";
        }
        return null;
    }
}
