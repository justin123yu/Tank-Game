package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall implements Collidable {

    public UnbreakableWall(int width, int height, BufferedImage x) {
        super(width,height,x);
    }


    @Override
    public void drawImage(Graphics2D tmp) {
        super.drawImage(tmp);
    }

    @Override
    public Rectangle getHitBox() {
        return super.getHitBox();
    }

    @Override
    public void handleCollision(Collidable obj) {

    }

}
