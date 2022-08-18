package TankGame.GameProject;


import TankGame.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends Stationary{

    public Background(BufferedImage img){
        super(img);
    }



    public void drawImage(Graphics2D g){
        for(int i = 0; i <GameConstants.WORLD_WIDTH; i+= this.getImage().getWidth()){
            for(int j = 0; j < GameConstants.WORLD_HEIGHT; j+= this.getImage().getHeight()){
                g.drawImage(this.getImage(), i, j, null);
            }
        }
    }

}
