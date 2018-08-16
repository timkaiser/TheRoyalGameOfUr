import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gamepanel extends JPanel {
    private BufferedImage img;
    private int x,y,s;

    public Gamepanel(BufferedImage img, int x, int y, int s){
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.s = s;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int offset_x = (int)(s*0.3);
        int offset_y = (int)(s*0.3);
        g.drawImage(img, x-offset_x, y+s-offset_y,s*8+2*offset_x,s*3+2*offset_y,this);
    }
}
