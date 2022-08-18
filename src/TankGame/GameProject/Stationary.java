package TankGame.GameProject;

import java.awt.*;
import java.awt.image.BufferedImage;

//SETTING UP OBJECT FOR MAP

public class Stationary extends GameObject {

    private int x;
    private int y;

    private Rectangle hitbox;
    private BufferedImage image;

    public Stationary(int width, int height, BufferedImage image){
        this.x = width;
        this.y = height;
        this.image = image;
        this.hitbox = new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
    public Stationary( BufferedImage image){
        this.image = image;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Rectangle getRectangle(){
        return this.hitbox;
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public Rectangle getHitBox(){
        return this.hitbox.getBounds();
    }

    @Override
    public void drawImage(Graphics2D tmp) {
        tmp.drawImage(this.image,this.getX(), this.getY(), null);
    }
}
