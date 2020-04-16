
package bomberman;

import java.awt.Color;

/**
 *
 * @author David Benes
 */
public enum PlayerColor {
    BLUE, GREEN, RED, WHITE;
    
    @Override
    public String toString() {
        switch (this) {
            case BLUE:
                return "blue";
            case GREEN:
                return "green";
            case RED:
                return "red";
            case WHITE:
                return "white";
        }
        return null;
    }
    
    public Color toColor() {
        switch (this) {
            case BLUE:
                return Color.blue;
            case GREEN:
                return Color.green;
            case RED:
                return Color.red;
            case WHITE:
                return Color.white;
        }
        return null;
    }
}
