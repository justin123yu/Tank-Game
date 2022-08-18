package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedPowerUp extends PowerUp implements Collidable{
    public SpeedPowerUp(int width, int height, BufferedImage image) {
        super(width, height, image);
    }

    @Override
    public void drawImage(Graphics2D tmp) {
        super.drawImage(tmp);
    }

    @Override
    public void handleCollision(Collidable with) {
        if(with instanceof Tank){
            ((Tank) with).setSpeed(10);
            GameWorld.powerUp.remove(this);
            GameWorld.colliding.remove(this);
        }
    }
}
