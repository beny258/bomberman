
package bomberman;

/**
 *
 * @author David Benes
 */
class Bomb extends MapObject {
    
    private final BombType type;
    
    private int alarm;
    
    private final Player owner;

    public BombType getType() {
        return type;
    }

    public int getAlarm() {
        return alarm;
    }
    
    public void alarmClock() {
        this.alarm++;
    }

    public Player getOwner() {
        return owner;
    }

    public Bomb(int posX, int posY, Player owner) {
        super(posX, posY);
        this.type = owner.getBombType();
        this.alarm = 0;
        this.owner = owner;
    }
}
