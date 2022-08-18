package TankGame.GameProject;
import TankGame.GameConstants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Movable implements Collidable{
    private int health;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float screen_x;

    private float screen_y;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    private float fireDelay = 150f;
    private float cooldown = 0f;
    private float rateOfFire = 1f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private boolean shootPressed;

    private boolean shield = false;


    private Rectangle hitbox;

    private ArrayList<Bullet> ammo = new ArrayList<>();
    private Bullet b;

    private int id;


    Tank(int id,float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.id = id;
        this.health = 3;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
        this.shield = false;
    }

    public int getHealth(){
        return this.health;
    }

    public int getId(){
        return this.id;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed(){ this.shootPressed = true;}

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed(){ this.shootPressed = false;}

    void update() {
        center_screen();
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.shootPressed && this.cooldown >= this.fireDelay) {
            this.cooldown = 0;
            new Sound(ResourceManager.getSound("bullet")).playSound();
            createBullet();
            GameWorld.colliding.add(b);

        }
        this.cooldown += this.rateOfFire;
        for(int i = 0; i < ammo.size(); i++){
            if(ammo.get(i).colided()){
                ammo.remove(i);
            } else {
                ammo.get(i).update();
            }
        }
        if(this.angle > 360){
            this.angle = 0;
        }

    }

    public void createBullet(){
        this.b = new Bullet(this.getId(),this.setBulletStartX(),this.setBulletStartY(), angle, ResourceManager.getImage("bullet"));
        this.ammo.add(b);
    }
    public void setHealth(int health){
        this.health = health;
    }


    public void setShield( boolean tmp){
        this.shield = tmp;
    }

    public boolean getShield(){
        return this.shield;
    }

    public int damage(int damage){
        if(getShield()){
            return (int) (getHealth()-Math.round(Math.random()));
        }
        return getHealth()-damage;
    }





    private int setBulletStartX(){
        float cx = 29f*(float)Math.cos(Math.toRadians(angle));
        return (int)x+this.img.getWidth()/2 + (int) cx-4;
    }

    private int setBulletStartY(){
        float cy = 29f*(float)Math.sin(Math.toRadians(angle));
        return (int)y+this.img.getWidth()/2 + (int) cy-4;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
       this.hitbox.setLocation((int)x, (int)y);
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
    }

    public void setSpeed(int speed){
        this.R = speed;
    }


    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public int getScreen_x(){
        return (int) this.screen_x;
    }

    public int getScreen_y(){
        return (int) this.screen_y;
    }

    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if(obj instanceof Wall){
            if(obj.getHitBox().intersects(this.getHitBox())){
                Rectangle intersection = this.hitbox.intersection(((Wall) obj).getRectangle());
                if(intersection.height > intersection.width  && this.x < intersection.x){ //left
                    this.x = ((Wall) obj).getX() - (intersection.width+ 60) ;
                }
                else if(intersection.height > intersection.width  && this.x > ((Wall) obj).getRectangle().x){ //right
                    this.x = ((Wall) obj).getX()+ (intersection.width+ 60);
                }
                else if(intersection.height < intersection.width  && this.y < intersection.y){ //up
                    this.y = ((Wall) obj).getY()- (intersection.height+60);
                }
                else if(intersection.height < intersection.width  && this.y > ((Wall) obj).getRectangle().y){ //down
                    this.y = ((Wall) obj).getY()+ (intersection.height+60);
                }


            }
        }
        if(obj instanceof Tank){
            if(obj.getHitBox().intersects(this.getHitBox())) {
                Rectangle intersection = this.hitbox.intersection(((Tank) obj).hitbox);
                if (intersection.height > intersection.width && this.x < intersection.x) { //left
                    this.x = ((Tank) obj).x - (intersection.width + 60);
                } else if (intersection.height > intersection.width && this.x > ((Tank) obj).x) { //right
                    this.x = ((Tank) obj).x + (intersection.width + 60);
                } else if (intersection.height < intersection.width && this.y < intersection.y) { //up
                    this.y = ((Tank) obj).y - (intersection.height + 60);
                } else if (intersection.height < intersection.width && this.y > ((Tank) obj).y) { //down
                    this.y = ((Tank) obj).y + (intersection.height + 60);
                }
            }
        }
        if(obj instanceof PowerUp){
            if(obj.getHitBox().intersects(this.getHitBox())){
                if(obj instanceof HealthPowerUp){
                    obj.handleCollision(this);
                }
                else if(obj instanceof SpeedPowerUp){
                    obj.handleCollision(this);
                }
                else if(obj instanceof DodgePowerUp){
                    obj.handleCollision(this);
                }

            }
        }




    }



    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 88) {
            y = GameConstants.WORLD_HEIGHT - 88;
        }

    }

    public void center_screen(){
        this.screen_x = (int) this.getX() - GameConstants.GAME_SCREEN_WIDTH /4;
        this.screen_y = (int) this.getY() - GameConstants.GAME_SCREEN_HEIGHT/2;

        if(this.screen_x < 0){
            this.screen_x = 0;
        }

        if(this.screen_y < 0){
            this.screen_y = 0;
        }

        if(screen_x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH /2){
            screen_x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH /2;
        }

        if(screen_y > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            screen_y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }



    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);
        drawHealthBar(g);
        this.ammo.forEach((b) -> b.drawImage(g));
        g.setColor(Color.RED);

    }

    public void drawHealthBar(Graphics2D g){
        for(int i = 0; i < this.health; i++){
            g.setColor(Color.GREEN);
            g.drawOval((int)x + (i * 25),(int)y-25,25,25);
            g.fillOval((int)x + (i * 25),(int)y-25,25,25);
        }
    }
}
