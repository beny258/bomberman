
package bomberman;

/**
 *
 * @author David Benes
 */
public class MapObject {
    
    protected Position pos;
    
    public Position getPos() {
        return this.pos;
    }

    public MapObject(int posX, int posY) {
        this.pos = new Position(posX, posY);
    }
}
