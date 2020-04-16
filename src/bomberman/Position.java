
package bomberman;

/**
 *
 * @author David Benes
 */
public class Position {
    
    private int x;
    
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void changeX(int change) {
        this.x += change;
    }
    
    public void changeY(int change) {
        this.y += change;
    }

    public boolean equals(Position pos) {
        return this.getX() == pos.getX() && this.getY() == pos.getY();
    }
}
