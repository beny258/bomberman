
package bomberman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author David Benes
 */
public class GraphicWidget extends JComponent {
    
    /**
     * Name of image of grass (field with no objects).
     */
    private final String NO_OBJECT_IMAGE_NAME;
    
    private final HashMap<String, BufferedImage> IMAGES;
    
    private BufferedImage IMG_NONE;
    
    /**
     * Size of one side of field in pixels (should equal to size of the source image).
     */
    private final int POINT_SIDE;

    private final HashMap<Position, ArrayList<String>> map;
    
    private boolean end;
    
    public GraphicWidget() {
        end = false;
        map = new HashMap<>();
        NO_OBJECT_IMAGE_NAME = "grass.png";
        POINT_SIDE = 32;
        IMAGES = new HashMap<>();
    }
    
    private void loadFiles() throws IOException {
        IMG_NONE = ImageIO.read(new File(NO_OBJECT_IMAGE_NAME));
        for (MapObjectType mot:MapObjectType.values()) {
            IMAGES.put(mot.toString(), ImageIO.read(new File(mot.toString()+".png")));
        }
    }
    
    public void updateMap(HashMap<Position, ArrayList<String>> updates) {
        for (Position pos:updates.keySet()) {
            for (Position p:map.keySet()) {
                if (p.equals(pos)) {
                    map.remove(p);
                    break;
                }
            }
            map.put(pos, updates.get(pos));
        }
        this.repaint();
    }
    
    public void setEnd(boolean end) {
        this.end = end;
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IMAGES.isEmpty()) try {
            loadFiles();
        } catch (IOException ex) {
            Logger.getLogger(GraphicWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (map == null) return;
        BufferedImage img;
        for (Position pos:((HashMap<Position, ArrayList<String>>)(map.clone())).keySet()) {
            ArrayList<String> currentField = this.map.get(pos);
            if (currentField == null) continue;
            if (!currentField.contains(MapObjectType.WOODENBOX.toString()) && !currentField.contains(MapObjectType.IRONBOX.toString())) {
                img = IMG_NONE;
                ((Graphics2D) g).drawImage(img, pos.getX()*POINT_SIDE, pos.getY()*POINT_SIDE, null);
            }
            for (String mapObj:currentField) {
                img = IMAGES.get(mapObj);
                ((Graphics2D) g).drawImage(img, pos.getX()*POINT_SIDE, pos.getY()*POINT_SIDE, null);
            }
        }
        if (end) {
            g.setColor(Color.red);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
            g.drawString("GAME", this.getWidth() / 2 - 135, this.getHeight() / 2);
            g.drawString("OVER", this.getWidth() / 2 - 125, this.getHeight() / 2 + 70);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("press any button", this.getWidth() / 2 - 100, this.getHeight() / 2 + 100);
        }
    }
}
