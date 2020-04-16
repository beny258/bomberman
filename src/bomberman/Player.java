
package bomberman;

/**
 *
 * @author David Beneš
 */
class Player extends MapObject {
    
    private final PlayerColor color;
    
    private Direction direction;
    
    private int lifes;
    
    private boolean bonusBoots;
    
    private BombType bombType;
    
    private final int id;
    
    private boolean bombDropping;
    
    private boolean moving;
    
    private int moveAlarm;
    
    private int lifeLostAlarm;
    
    private int bombDropCounter;
    
    public Player(int posX, int posY, PlayerColor color, Direction direction, int id, int lifes) {
        super(posX, posY);
        this.color = color;
        this.direction = direction;
        this.lifes = lifes;
        this.bonusBoots = false;
        this.bombType = BombType.BASIC;
        this.id = id;
        
        this.bombDropping = false;
        this.moving = false;
        
        this.moveAlarm = 0;
        this.lifeLostAlarm = 0;
        this.bombDropCounter = 0;
    }

    public PlayerColor getColor() {
        return color;
    }
    
    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean hasBonusBoots() {
        return this.bonusBoots;
    }

    public void setBonusBoots(boolean bonusBoots) {
        this.bonusBoots = bonusBoots;
    }

    public BombType getBombType() {
        return this.bombType;
    }

    public void setBombType(BombType bombType) {
        this.bombType = bombType;
    }
    
    public int getID() {
        return this.id;
    }
    
    public void addLife() {
        this.lifes++;
    }
    
    public void loseLife() {
        this.lifes--;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifeLostAlarm(int lifeLostAlarm) {
        this.lifeLostAlarm = lifeLostAlarm;
    }
    
    public boolean isLoosingLife() {
        return this.lifeLostAlarm > 0;
    }

    public boolean isBombDropping() {
        return bombDropping;
    }

    public void setBombDropping(boolean bombDropping) {
        this.bombDropping = bombDropping;
    }

    public int getBombDropCounter() {
        return bombDropCounter;
    }
    
    public void bombDrop() {
        this.bombDropCounter++;
    }
    
    public void bombBoom() {
        this.bombDropCounter--;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public void alarmClock() {
        if (this.moveAlarm > 0) this.moveAlarm--;
        if (this.lifeLostAlarm > 0) this.lifeLostAlarm--;
    }

    public void setMoveAlarm(int moveCounter) {
        this.moveAlarm = moveCounter;
    }
    
    public boolean isMovable() {
        return this.moveAlarm <= 0;
    }

    public void move(Direction direction) {
        switch(direction) {
            case UP:
                super.pos.changeY(-1);
                break;
            case DOWN:
                super.pos.changeY(1);
                break;
            case RIGHT:
                super.pos.changeX(1);
                break;
            case LEFT:
                super.pos.changeX(-1);
                break;
        }
    }
}
