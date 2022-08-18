package TankGame.GameProject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Movable implements Collidable{

    private Rectangle hitbox;
    private int damage;

    private boolean colided = false;

    private float x;
    private float y;
    private float angle;

    private float R = 5;

    private int id;


    private BufferedImage img;



    Bullet(int id,float x, float y,  float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
        this.id = id;
        this.damage = 1;
    }

    public Rectangle getHitBox(){
        return this.hitbox.getBounds();
    }

    public int getDamage(){
        return this.damage;
    }


    public boolean colided(){
        return this.colided;
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof Wall){
            if(obj instanceof BreakableWall){
                obj.handleCollision(this);
            }
            GameWorld.colliding.remove(this);
            this.colided = true;
        }
        if(obj instanceof Tank){
            if(((Tank) obj).getId() != this.id){
                ((Tank) obj).setHealth(((Tank) obj).damage(this.damage));
                GameWorld.colliding.remove(this);
                this.colided = true;
            }
        }
    }

    void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        this.hitbox.setLocation((int)x, (int)y);
    }


    void update() {
        moveForwards();
        setPosition(this.x, this.y);
    }

    private void moveForwards() {
        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        this.hitbox.setLocation((int)x, (int)y);
    }




    @Override
    public String toString() {
        return "id= "+ id+ " x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);
        g.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

    }
}
