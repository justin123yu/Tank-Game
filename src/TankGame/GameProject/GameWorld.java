/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.GameProject;


import TankGame.GameConstants;
import TankGame.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;

    private Background background;
    private Tank t1;
    private Tank t2;
    protected static ArrayList<Collidable> colliding = new ArrayList<>();

    protected static ArrayList<Wall> wall = new ArrayList<>();
    protected static ArrayList<PowerUp> powerUp = new ArrayList<>();
    private Launcher lf;

    /**
     *
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }
    @Override
    public void run() {
        try {
            while (true) {
                this.t1.update(); // update tank
                this.t2.update();

                for(int i = 0; i < colliding.size(); i++){
                    Collidable c = colliding.get(i);
                    if(c instanceof Wall) continue;
                    for(int j = 0; j < colliding.size(); j++){
                        if(j == i) continue;
                        Collidable co = colliding.get(j);
                        if(c.getHitBox().intersects(co.getHitBox())){
                            c.handleCollision(co);
                        }
                    }
                }


                this.repaint();   // redraw game\\

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
                if (t1.getHealth() == 0 || t2.getHealth() == 0) {
                    this.lf.setFrame("end");
                    return;
                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        /*
         * note class loaders read files from the out folder (build folder in Netbeans) and not the
         * current working directory. When running a jar, class loaders will read from withing the jar.
         */
        ResourceManager.initSound();
        new Sound(ResourceManager.getSound("music")).run();
        BufferedImage t1img = ResourceManager.getImage("tank1");
        BufferedImage t2img = ResourceManager.getImage("tank2");
        this.background = new Background(ResourceManager.getImage("background"));
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        t1 = new Tank(1,300, 300, 0, 0, (short) 0, t1img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        t2 = new Tank(2,GameConstants.WORLD_WIDTH-100, GameConstants.WORLD_HEIGHT-100, 0, 0, (short) 0, t2img);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_0);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("map/map.txt"))))){
            String[] size = mapReader.readLine().split(",");
            int numberOfRows = Integer.parseInt(size[0]);
            int numberOfColumn = Integer.parseInt(size[0]);
            for(int i = 0; mapReader.ready(); i++){
                String[] items = mapReader.readLine().split(",");
                for(int j = 0; j < items.length; j++ ){
                    switch(items[j]){
                        //Power Ups Needs adding
                        case "1" ->{
                            PowerUp hp = new HealthPowerUp(i*30,j*30, ResourceManager.getImage("health"));
                            this.powerUp.add(hp);
                        }
                        case "2" ->{
                            PowerUp speed = new SpeedPowerUp(i*30,j*30, ResourceManager.getImage("speed"));
                            this.powerUp.add(speed);
                        }
                        case "3"->{
                            PowerUp shield = new DodgePowerUp(i*30,j*30, ResourceManager.getImage("shield"));
                            this.powerUp.add(shield);
                        }
                        case "8"->  {
                            Wall w1 = new BreakableWall(i*30,j*30, ResourceManager.getImage("crackedWall"));
                            this.wall.add(w1);
                        }
                        case "9" -> {
                            Wall w2 = new UnbreakableWall(i*30,j*30, ResourceManager.getImage("normalWall"));
                            this.wall.add(w2);
                        }

                    }
                }
            }
            colliding.add(this.t1);
            colliding.add(this.t2);
            powerUp.forEach((powerUp) -> this.colliding.add(powerUp));
            wall.forEach((wall) -> this.colliding.add(wall));

        } catch(IOException e ){
            System.out.println(e);
            System.exit(-2);
        }
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        //Change Later to background
        this.background.drawImage(buffer);
        for(int i = 0; i < wall.size(); i++){
            wall.get(i).drawImage(buffer);
        }
        for(int i = 0; i < powerUp.size(); i++){
            powerUp.get(i).drawImage(buffer);
        }
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        //screen
        BufferedImage leftHalf = world.getSubimage(this.t1.getScreen_x(), this.t1.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(this.t2.getScreen_x(), this.t2.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(leftHalf ,0 ,0 ,null);
        g2.drawImage(rightHalf ,GameConstants.GAME_SCREEN_WIDTH/2+1 ,0 ,null);
        //MiniMap
        BufferedImage mm = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        g2.scale(0.1,0.1);
        g2.drawImage(mm, (int)(1000/0.1),(int)(10/0.1), null);
    }

}
