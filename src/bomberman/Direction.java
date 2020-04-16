
package bomberman;

/**
 *
 * @author David Benes
 */
public enum Direction {
    UP, DOWN, RIGHT, LEFT;
    
    public String toString() {
        switch(this) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case RIGHT:
                return "RIGHT";
            case LEFT:
                return "LEFT";
            default:
                return null;
        }
    }
}
