
package bomberman;

/**
 *
 * @author David Benes
 */
public interface ServerCallback {
    
    public void log(String text);
    
    public void updateStatus(String text);
    
    public void updatePlayersCount();
    
}
