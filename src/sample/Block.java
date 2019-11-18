package sample;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class Block extends Rectangle {

    public int groundId;
    public int airId;



    public Block(int positionX, int positionY, int width, int height, int groundId, int airId){
        super(positionX, positionY, width, height);
        this.groundId = groundId;
        this.airId = airId;
    }


    public void drawBlock(GraphicsContext graphicsContext) {

        if(groundId == Value.roadGrass){
            graphicsContext.drawImage(GameLaunch.titlesGround[Value.roadGrass], getX(), getY(), getWidth(), getHeight());
        }
        if(airId != Value.airAir){
            graphicsContext.drawImage(GameLaunch.titlesAir[airId], getX(), getY(), getWidth(), getHeight());
        }
    }
}
