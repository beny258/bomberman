
package bomberman;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author David Benes
 */
public interface ClientCallback {
    
    public void updateBonuses(HashMap<Integer, HashMap<BonusType, Boolean>> bonuses);
    
    public void updateLifes(HashMap<Integer, Integer> lifes);
    
    public void updateMap(HashMap<Position, ArrayList<String>> map);
    
    public void updateNames(HashMap<Integer, String> names);
    
    public void updateColors(HashMap<Integer, PlayerColor> colors);
    
    public void updateIDs(ArrayList<Integer> ids);
    
    public void setStatus(String status);
    
    public void gameOver(int winnerID);
    
    public void closeWindow();
    
}
