package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import sample.Enemy.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameLaunch {

    public static int myWidth = 1120;
    public static int myHeight = 600;

    public boolean isFirst = true;

    public static Image[] titlesGround = new Image[10];
    public static Image[] titlesAir = new Image[10];
    public static Image[] titlesIcon = new Image[10];
    public static Image[] titlesEnemy = new Image[10];

    public Room room;
    public Save save;

    public Store store;
    public static List<Enemy> enemy = new ArrayList<Enemy>();
    public static List<Tower> towers = new ArrayList<Tower>();

    public static int health = 2;
    public static int coinage = 100;

    public static Point2D mse;

    public GameLaunch() throws FileNotFoundException, InterruptedException {
        define();
        
    }

    public void define() {
        try {
            titlesGround[0] = Draw.loadImage(Value.pathMap + "Map1.jpg");
            titlesGround[1] = Draw.loadImage("C:\\Users\\QD\\Documents\\TowerDefense(Demo)\\road1.png");
            titlesGround[2] = Draw.loadImage(Value.pathMap + "Map2.jpg");
            titlesGround[3] = Draw.loadImage(Value.pathMap + "Map3.jpg");
            titlesGround[4] = Draw.loadImage(Value.pathMap + "start.jpg");

            titlesAir[0] = Draw.loadImage( Value.pathTower + "target.png");
            titlesAir[1] = Draw.loadImage(Value.pathTower + "recycle.png");
            titlesAir[2] = Draw.loadImage( Value.pathTower + "tower1.png");
            titlesAir[3] = Draw.loadImage(Value.pathTower + "tower2.png");
            titlesAir[4] = Draw.loadImage( Value.pathTower + "tower3.png");
            titlesAir[5] = Draw.loadImage(Value.pathTower + "tower4.png");
            titlesAir[6] = Draw.loadImage("C:\\Users\\QD\\Documents\\TowerDefense(Demo)\\tower5.png");
            titlesAir[7] = Draw.loadImage( Value.pathTower + "dolar.png");
            titlesAir[8] = Draw.loadImage(Value.pathTower + "update.png");


            titlesIcon[0] = Draw.loadImage(Value.pathIcon + "cell.png");
            titlesIcon[3] = Draw.loadImage(Value.pathIcon + "button.png");
            titlesIcon[1] = Draw.loadImage(Value.pathIcon + "coin.png");
            titlesIcon[2] = Draw.loadImage(Value.pathIcon + "health.png");



            titlesEnemy[0] = Draw.loadImage(Value.pathEnemy + "EnemyNormal.png");
            titlesEnemy[1] = Draw.loadImage(Value.pathEnemy + "EnemySpeed.png");
            titlesEnemy[2] = Draw.loadImage(Value.pathEnemy + "EnemyTanker.png");
            titlesEnemy[3] = Draw.loadImage(Value.pathEnemy + "EnemyBoss.png");
        }
        catch (Exception e){
            System.out.println("Canot load Image");
        }
        }

    public void drawGameLaunch(Group group, GraphicsContext graphicsContext) throws FileNotFoundException {

        room = new Room();
        save = new Save();

        save.loadSave(new File("Map1"), room.blocks);

        System.out.println(room.blocks[3][27].airId);

        store = new Store();
        store.draw(group, room.blocks);
        
        for(int i = 0; i < 1; i++) {
            enemy.add(new NomalEnemy());
            enemy.add(new SpeedEnemy());
            enemy.add(new TankerEnemy());
            enemy.add(new BossEnemy());
        }

    }

    public void paintComponent(Group group, GraphicsContext graphicsContext) {
        try {

            if (isFirst) {
                try {
                    drawGameLaunch(group, graphicsContext);
                    isFirst = false;
                }
                catch (Exception e){
                    System.out.println("ngoaij le");
                }
            }
            graphicsContext.clearRect((GameStart.WIDTH - myWidth) / 2, 0 , myWidth, myHeight);

            graphicsContext.drawImage(titlesGround[0], (GameStart.WIDTH - myWidth) / 2, 0, myWidth, myHeight);
            room.drawRoom(graphicsContext, towers);
            try {
                for (int i = 0; i < enemy.size(); i++) {
                    if (enemy.get(i).inGame) {
                        enemy.get(i).draw(graphicsContext);
                    }
                }
            }
            catch (Exception e){
                System.out.println("zo");
            }

            if(health < 1){
                graphicsContext.drawImage(titlesGround[4], (GameStart.WIDTH - myWidth) / 2, 0, myWidth, myHeight);
                graphicsContext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                graphicsContext.strokeText("You were lose!", 20, 20);
                System.out.println("zo");
            }
        }
        catch (Exception e){
            System.out.println("Error: Paint Component");
        }
    }

    public int spawnTime = 100, spawnFrame = 0;
    boolean create = true;
    public void modSpawner(){
        if(spawnFrame >= spawnTime){
            for(int i =0; i < enemy.size(); i++){
                if(!enemy.get(i).inGame) {
                    enemy.get(i).spawnMob(room.blocks, Value.modGreeny);
                    break;
                }
                if(enemy.get(enemy.size() - 1).inGame){
                    create = false;
                }
            }

            spawnFrame = 0;
        }
        else{
            spawnFrame += 1;
        }
        //System.out.println(spawnFrame);
    }



    public void start(Group group,GraphicsContext graphicsContext){

        try {
            paintComponent(group, graphicsContext);
            room.physic(towers);
            if(create && health > 0){
                modSpawner();
            }
            for(int i=0;i<enemy.size();i++){
                if(enemy.get(i).inGame){
                    enemy.get(i).physic(room.blocks);
                }
            }


        }
        catch (Exception e){
            System.out.println("Error: start GameLaunch");
        }
    }
}
