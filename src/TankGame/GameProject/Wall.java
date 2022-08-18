package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Stationary implements Collidable{
    public Wall(int width, int height, BufferedImage image) {
        super(width, height, image);
    }

    public Rectangle getRectangle(){
        return super.getRectangle();
    }

    @Override
    public void handleCollision(Collidable obj) {

    }


}
