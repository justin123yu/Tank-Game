package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall implements Collidable{

    private int health;
    BreakableWall(int width, int height , BufferedImage x){
        super(width,height,x);
        this.health = 2;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health){
        if(this.health == 0){
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    @Override
    public void drawImage(Graphics2D tmp) {
        super.drawImage(tmp);
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof Bullet){
            this.setHealth(this.getHealth()-((Bullet) obj).getDamage());
            if(this.health == 0){
                GameWorld.colliding.remove(this);
                GameWorld.wall.remove(this);
            }
        }

    }

}
