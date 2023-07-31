package game;


import javafx.scene.Group;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import game.Enemy;
import game.Platform;

/**
 * Level class used for creating various levels of the game.
 * Platforms and other objects are referenced from text files.
 * All objects are stored in type-specific ArrayLists
 * @author DA Chan
 * @version PX
 */
public class Level extends Group{

    //Variables to identify objects being added to level
    private static final int EMPTY_ID = 0;
    private static final int PLATFORM_TOP_ID = 1;
    private static final int PLATFORM_ID = 2;
    private static final int ENEMY_ID = 3;

    private static  final double WIDTH_RATIO = 5;

    private double width, height;

    //Stores all objects
    private ArrayList<Platform> platforms;
    private ArrayList<Knives> knives;
    private ArrayList<EnemyKnives> enemyKnives;
    private ArrayList<Enemy> enemies;

    private Random random;

    /**
     * Default constructor. Loads information from text file and creates objects referenced from the file.
     * Sets ratios relating to width and height.
     * @param number The level number
     * @param height Height of the level
     */
    public Level(int number, double height) {
        platforms = new ArrayList<Platform>();
        knives = new ArrayList<Knives>();
        enemyKnives = new ArrayList<EnemyKnives>();
        enemies = new ArrayList<Enemy>();

        random = new Random();

        //Gets text file as InputStream, adds objects to level based on text file
        InputStream levelsInfo = getClass().getClassLoader().getResourceAsStream("Levels/Level" + number + ".txt");

        Scanner reader = new Scanner(levelsInfo);

        //Numbers referencing width and height of level information from text file
        int widthInPlatform = reader.nextInt();
        int heightInPlatform = reader.nextInt();

        //Height and width of level
        this.height = height;
        this.width = widthInPlatform / heightInPlatform * this.height * WIDTH_RATIO;

        //Sets platform ratios
        double platformHeightRatio = height / heightInPlatform; 
        double platformWidthRatio = platformHeightRatio * WIDTH_RATIO; 

        //For-each loop creating objects (platforms, enemy sprites, etc.) referenced from text file
        for (int y = 0; y < heightInPlatform; y++) {
            for (int x = 0; x < widthInPlatform; x++) {
                int checkID = reader.nextInt();

                if (checkID == EMPTY_ID) {
                    continue;
                } else if (checkID == PLATFORM_TOP_ID) {

                    //Create platforms and add them to Level
                    Platform p = new Platform(platformWidthRatio * x, platformHeightRatio * y, platformWidthRatio, platformHeightRatio, PLATFORM_TOP_ID);
                    platforms.add(p);
                    getChildren().add(p);
                } else if (checkID == PLATFORM_ID) {
                    Platform p = new Platform(platformWidthRatio * x, platformHeightRatio * y, platformWidthRatio, platformHeightRatio, PLATFORM_ID);
                    platforms.add(p);
                    getChildren().add(p);
                } else if (checkID == ENEMY_ID) {

                    //Place enemies in a random location around the middle of the platform
                    double xAxis = (x * platformWidthRatio) + platformWidthRatio/2 + (((random.nextInt(101 + 100) - 100)/100) * (platformWidthRatio / 2));
                    double yAxis = (y * platformHeightRatio);
                    Enemy enemy = new Enemy(xAxis, yAxis, this);
                    enemies.add(enemy);
                    getChildren().add(enemy);
                }

            }

        }

        reader.close();
    }

    //Accessors

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Knives> getKnives() {
        return knives;
    }

    public ArrayList<EnemyKnives> getEnemyKnives() {
        return enemyKnives;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public double getWidth() {
        return width;
    }
}
