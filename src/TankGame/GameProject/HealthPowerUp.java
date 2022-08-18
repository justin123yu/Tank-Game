package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends PowerUp implements Collidable {

    public HealthPowerUp(int width, int height, BufferedImage image) {
        super(width, height, image);
    }

    @Override
    public void drawImage(Graphics2D tmp) {
        super.drawImage(tmp);
    }

    @Override
    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).setHealth(((Tank) with).getHealth() +1);
            GameWorld.powerUp.remove(this);
            GameWorld.colliding.remove(this);
        }
    }
}
