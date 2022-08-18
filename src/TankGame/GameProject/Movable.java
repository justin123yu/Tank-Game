package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Movable extends GameObject {

    private int x;
    private int y;
    private BufferedImage img;

    public Movable(){

    }
    @Override
    public void drawImage(Graphics2D tmp) {
        tmp.drawImage(this.img, this.x, this.y, null);
    }
}
