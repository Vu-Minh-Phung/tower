package sample;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

import javafx.scene.text.Text;

public class Store {
    public static int shopWidth = 8;
    public int buttonSize = 52;
    public int cellSpace = 3;
    public int awayFromRoom = 29;
    public int iconSize = 20;
    public int spaceIcon = 10;
    public int itemIn = 10;

    public static int helpId = -1;
    public boolean clickSceen = false;

    public int[] buttonId = {Value.airTowerLaser, 3, 4, 5, 6, 7, 8, 1};
    public int[] buttonPrice = {10, 15, 20, 25, 20, 0 , 0};



    public Rectangle[] position = new Rectangle[shopWidth];

    public boolean holdItem = false;

    public ImageView buttonImage[] = new ImageView[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;

    public static Text text1 = new Text("" + GameLaunch.health);
    public static Text text2 = new Text("" + GameLaunch.coinage);

    public Store(){
        define();
    }

    public void define(){
        for(int i = 0 ; i < position.length; i++){
            buttonImage[i] = new ImageView(GameLaunch.titlesAir[buttonId[i]]);

            position[i] = new Rectangle(GameLaunch.myWidth / 2 - (shopWidth * buttonSize) / 2  + i * (buttonSize + cellSpace), GameLaunch.myHeight + awayFromRoom , buttonSize, buttonSize);
        }

        buttonHealth = new Rectangle((GameStart.WIDTH - GameLaunch.myWidth) / 2, position[0].getY(), iconSize, iconSize);
        buttonCoins = new Rectangle((GameStart.WIDTH - GameLaunch.myWidth) / 2, position[0].getY() + buttonSize - iconSize + cellSpace, iconSize, iconSize);
    }

    public void draw(Group group, Block[][] blocks){

        for (int i = 0; i < position.length; i++){

            position[i].setFill(Color.grayRgb(100));
            position[i].setStroke(Color.BROWN);
            position[i].setStrokeWidth(1);
            group.getChildren().add(position[i]);


            int finalI1 = i;
            group.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for(int k = 0; k < position.length ; k++){
                        if(position[k].contains(GameLaunch.mse)) {
                            for (int j = 0; j <= 7; j++)
                                if (holdItem == true && buttonId[helpId] == buttonId[j]) {
                                    buttonImage[helpId].setX(position[helpId].getX() + itemIn / 2);
                                    buttonImage[helpId].setY(position[helpId].getY() + itemIn / 2);
                                    helpId = Value.airAir;
                                    holdItem = false;
                                }
                            if (buttonId[k] == Value.airTrashCan && holdItem == true) {
                                    buttonImage[helpId].setX(position[helpId].getX() + itemIn / 2);
                                    buttonImage[helpId].setY(position[helpId].getY() + itemIn / 2);

                                    helpId = Value.airAir;
                                    holdItem = false;
                                } else if (buttonId[k] != 1) {
                                    helpId = k;
                                    holdItem = true;
                                }
                        }
                    }

                    if(holdItem) {
                        if (GameLaunch.coinage >= buttonPrice[helpId]) {

                            for (int y = 0; y < blocks.length; y++) {
                                for (int x = 0; x < blocks[y].length; x++) {
                                    if (blocks[y][x].contains(GameLaunch.mse)) {
                                        if(buttonId[helpId] == 7
                                        && (blocks[y][x].airId == 2 || blocks[y][x].airId == 3
                                        || blocks[y][x].airId == 4 || blocks[y][x].airId == 5 || blocks[y][x].airId == 6)){

                                            GameLaunch.coinage += buttonPrice[blocks[y][x].airId - 2]/2;;
                                            Store.text2.setText("" + GameLaunch.coinage);
                                            blocks[y][x].airId = -1;
                                            System.out.println("Hello");
                                        }
                                        else if (blocks[y][x].groundId != Value.roadGrass && blocks[y][x].airId == Value.airAir) {

                                            position[finalI1].setFill(Color.RED);

                                            buttonImage[helpId].setX(position[helpId].getX() + itemIn / 2);
                                            buttonImage[helpId].setY(position[helpId].getY() + itemIn / 2);

                                            if(buttonId[helpId] == 2 || buttonId[helpId] == 3 || buttonId[helpId] == 4 || buttonId[helpId] == 5 || buttonId[helpId] == 6){
                                                blocks[y][x].airId = buttonId[helpId];
                                                GameLaunch.towers.add(new Tower(blocks[y][x].getX(), blocks[y][x].getY(), blocks[y][x].getWidth(), blocks[y][x].getHeight(), blocks[y][x].airId));
                                            }


                                            holdItem = false;
                                            GameLaunch.coinage -= buttonPrice[helpId];
                                            text2.setText("" + GameLaunch.coinage);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        });

        group.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameLaunch.mse = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                if(holdItem){
                    //System.out.println(Store.helpId);
                    buttonImage[helpId].setX(GameLaunch.mse.getX() - iconSize );
                    buttonImage[helpId].setY(GameLaunch.mse.getY() - iconSize );
                }
            }
        });

        int finalI = i;
        buttonImage[i].setOnMouseMoved(e->moveMouse(finalI, group));
        buttonImage[i].setOnMouseExited(e->exitMouse(finalI, group));

        Draw.drawImage(position[i].getX(), position[i].getY(), position[i].getWidth(), position[i].getHeight(), GameLaunch.titlesIcon[0], group, true);
        Draw.drawImageView(position[i].getX() + itemIn/2, position[i].getY() + itemIn/2, position[i].getWidth() - itemIn , position[i].getHeight() - itemIn, buttonImage[i], group, true);

        if(i < buttonPrice.length)
            Draw.writeText(new Text("" + buttonPrice[i]), position[i].getX() + itemIn + cellSpace, position[i].getY() + itemIn, group, 10, Color.WHITE);
    }
        Draw.writeText(text1, buttonHealth.getX() + iconSize + spaceIcon, buttonHealth.getY() + iconSize/2  + Value.FontSize/2, group, Value.FontSize, Color.BROWN);
        Draw.writeText(text2, buttonCoins.getX() + iconSize + spaceIcon, buttonCoins.getY() + iconSize/2 + Value.FontSize/2, group, Value.FontSize, Color.BROWN);
        Draw.drawImage(buttonHealth.getX(), buttonHealth.getY(), buttonHealth.getWidth(), buttonHealth.getHeight(), GameLaunch.titlesIcon[2],group, true);
        Draw.drawImage(buttonCoins.getX(), buttonCoins.getY(), buttonCoins.getWidth(), buttonCoins.getHeight(), GameLaunch.titlesIcon[1],group, true);
    }

    public void moveMouse(int id, Group group){
        position[id].setFill(Color.WHITE);
        position[id].setStroke(Color.RED);
    }

    public void exitMouse(int id, Group group){
        position[id].setFill(Color.grayRgb(100));
        position[id].setStroke(Color.grayRgb(150));
    }

}
