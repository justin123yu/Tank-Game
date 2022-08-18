package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Stationary implements Collidable{
    public PowerUp(int width, int height, BufferedImage image) {
        super(width, height, image);
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
    public void handleCollision(Collidable with) {
        
    }

}
