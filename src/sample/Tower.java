package sample;

import javafx.geometry.HPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tower extends Rectangle{

    Rectangle displayAreaTower;
    public int areaTower = 120;
    public int airId;

    public Tower(double positionX, double positionY, double width, double height, int airId) {
        super(positionX, positionY, width, height);
        this.airId = airId;
        displayAreaTower = new Rectangle(getX() - areaTower/2, getY() - areaTower/2,width + areaTower, height + areaTower);
    }
    public int shotMob = 0;
    public boolean shoting = false;

    public void physic(){
        shoting = false;
        for(int i = 0; i < GameLaunch.enemy.size(); i++){
            if(airId == Value.airTowerLaser) {
                if (GameLaunch.enemy.get(i).inGame) {
                    if (GameLaunch.enemy.get(i).intersects( displayAreaTower.getBoundsInLocal() )){
                        shoting = true;
                        shotMob = i;
                        break;
                    }
                }
            }
        }
    }

    public void  getMoney(int id){
        GameLaunch.coinage += Value.deadReward[id];
    }

    public void drawArea(GraphicsContext graphicsContext){
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.beginPath();
        graphicsContext.moveTo(displayAreaTower.getX(), displayAreaTower.getY());
        graphicsContext.lineTo(displayAreaTower.getX() + displayAreaTower.getWidth(), displayAreaTower.getY());
        graphicsContext.lineTo(displayAreaTower.getX() + displayAreaTower.getWidth(), displayAreaTower.getY() + displayAreaTower.getHeight());
        graphicsContext.lineTo(displayAreaTower.getX(), displayAreaTower.getY() + displayAreaTower.getHeight());
        graphicsContext.lineTo(displayAreaTower.getX(), displayAreaTower.getY());
        graphicsContext.stroke();

        if(shoting && shotMob != -1) {

            graphicsContext.setStroke(Color.RED);
            graphicsContext.beginPath();
            graphicsContext.moveTo(getX() + getWidth() / 2, getY() + getHeight()/2);
            graphicsContext.lineTo(GameLaunch.enemy.get(shotMob).getX() + GameLaunch.enemy.get(shotMob).getWidth()/2,
                    GameLaunch.enemy.get(shotMob).getY() + GameLaunch.enemy.get(shotMob).getHeight()/2);
            graphicsContext.stroke();

            GameLaunch.enemy.get(shotMob).loseHealth(1);
            if (GameLaunch.enemy.get(shotMob).isDead()) {
                shoting = false;
                getMoney(shotMob);
                GameLaunch.enemy.remove(shotMob);
                System.out.println(GameLaunch.coinage);
                Store.text2.setText("" + GameLaunch.coinage);
                shotMob = -1;
            }
        }
    }
    public void drawBlock(GraphicsContext graphicsContext) {

        if(airId != Value.airAir){
            graphicsContext.drawImage(GameLaunch.titlesAir[airId], getX(), getY(), getWidth(), getHeight());
        }
    }
}
