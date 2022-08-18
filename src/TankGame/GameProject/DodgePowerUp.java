package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DodgePowerUp extends PowerUp{

    public DodgePowerUp(int width, int height, BufferedImage image) {
        super(width, height, image);
    }

    @Override
    public void drawImage(Graphics2D tmp) {
        super.drawImage(tmp);
    }

    @Override
    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).setShield(true);
            GameWorld.colliding.remove(this);
            GameWorld.powerUp.remove(this);
        }
    }
}
