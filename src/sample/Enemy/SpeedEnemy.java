package sample.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.GameLaunch;

public class SpeedEnemy extends  Enemy{
        public void draw(GraphicsContext graphicsContext){
            scaleHealth = (int) Math.ceil( healthEnemy/ 100 * modSize);
            //System.out.println(scaleHealth);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(getX(), getY() - (healthSpace + healthSpace), healthEnemy, healthHeight);
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.strokeRect(getX(), getY() - (healthSpace + healthSpace), healthEnemy, healthHeight);

            graphicsContext.drawImage(GameLaunch.titlesEnemy[1], getX(), getY(), getWidth(), getHeight());
        }
}
