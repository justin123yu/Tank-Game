package TankGame.GameProject;

import java.awt.*;

public interface Collidable {
    public Rectangle getHitBox();
    void handleCollision(Collidable obj);
}
