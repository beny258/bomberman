
package bomberman;

/**
 *
 * @author David Benes
 */
class Bonus extends MapObject {
    
    private final BonusType type;

    public BonusType getType() {
        return type;
    }

    public Bonus(int posX, int posY, BonusType type) {
        super(posX, posY);
        this.type = type;
    }
}
