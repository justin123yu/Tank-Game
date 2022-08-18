package TankGame.GameProject;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceManager {
        private static HashMap<String, BufferedImage> images = new HashMap();
        private static HashMap<String, Clip> sounds = new HashMap();
        private static HashMap<String, ArrayList<BufferedImage>> animations = new HashMap();

        public static BufferedImage getImage(String key) {
                return ResourceManager.images.get(key);
        }

        public static Clip getSound(String key) {
                return ResourceManager.sounds.get(key);
        }

        public static ArrayList<BufferedImage> getAnimations(String key) {
                return ResourceManager.animations.get(key);
        }

        public static void initImages() {
                try {
                        ResourceManager.images.put("tank1", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank/tank1.png"))));
                        ResourceManager.images.put("tank2", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank/tank2.png"))));
                        ResourceManager.images.put("bullet", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("bullet/Bullet.jpg"))));
                        ResourceManager.images.put("title", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("title.png"))));
                        ResourceManager.images.put("background", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("background/Background.bmp"))));
                        ResourceManager.images.put("normalWall", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("walls/Wall1.png"))));
                        ResourceManager.images.put("crackedWall", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("walls/Wall2.png"))));
                        ResourceManager.images.put("speed", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("powerUp/speed.JPG"))));
                        ResourceManager.images.put("shield", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("powerUp/shield.png"))));
                        ResourceManager.images.put("health", ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("powerUp/health.JPG"))));
                } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                }
        }

        public static void initSound() {
                AudioInputStream as;
                Clip clip;
                try {
                        as = AudioSystem.getAudioInputStream(ResourceManager.class.getClassLoader().getResource("music/explosion.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(as);
                        ResourceManager.sounds.put("explosion", clip);

                        as = AudioSystem.getAudioInputStream(ResourceManager.class.getClassLoader().getResource("music/music.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(as);
                        ResourceManager.sounds.put("music", clip);

                        as = AudioSystem.getAudioInputStream(ResourceManager.class.getClassLoader().getResource("music/bullet.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(as);
                        ResourceManager.sounds.put("bullet", clip);

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        System.err.println(e);
                        e.printStackTrace();
                        System.exit(-2);
                }
        }

        public static void initResourceManager(){
                initImages();
                initSound();
        }

}
