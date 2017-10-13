package com.filip.simplegame;

/**
 * Created by RE1010 on 2017-09-30.
 */

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;

import java.util.List;
import java.util.Random;

public class GameScreen extends Screen {

    private static final float UPDATE_BLOB_TIME = 1.0f;

    private static Pixmap background;
    private static Pixmap numbers;
    //Buttons
    private static Pixmap mapButton;
    private static Pixmap challButton;
    private static Pixmap shopButton;
    private static Pixmap menuButton;
    private static Pixmap partyButton;
    private static Pixmap itemsButton;

    private int blobXPos;
    private int blobYPos;

    private int centerXPos;
    private int centerYPos;

    private int oldScore;
    private String score = "0";

    private float timePassed;


    public GameScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);

        //Buttons
        mapButton = g.newPixmap("Map.png",Graphics.PixmapFormat.ARGB4444);
        challButton = g.newPixmap("Challenges.png",Graphics.PixmapFormat.ARGB4444);
        shopButton = g.newPixmap("Shop.png",Graphics.PixmapFormat.ARGB4444);
        menuButton = g.newPixmap("Menu.png",Graphics.PixmapFormat.ARGB4444);
        partyButton = g.newPixmap("Party.png",Graphics.PixmapFormat.ARGB4444);
        itemsButton = g.newPixmap("Items.png",Graphics.PixmapFormat.ARGB4444);

        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
    }

    @Override
    public void update(float deltaTime){
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if(inBounds(event,0-mapButton.getWidth()/2 ,0-mapButton.getHeight()/2,mapButton.getWidth(),mapButton.getHeight()))
                {
                    game.setScreen(new GameMapScreen(game));
                    return;
                }
                if(inBounds(event,0+centerXPos+50-menuButton.getWidth()/2,centerYPos+500-menuButton.getWidth(),menuButton.getWidth(),menuButton.getHeight()))
                {
                    game.setScreen(new GameScreen(game));
                }
                if(inBounds(event,centerXPos+250-mapButton.getWidth()/2,centerYPos))
            }
        }

    }

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void dispose(){}

    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);

        //Draw Buttons
        g.drawPixmap(mapButton, centerXPos - 250 - mapButton.getWidth()/2,centerYPos - 600);
        g.drawPixmap(challButton,centerXPos + 250 -mapButton.getWidth()/2,centerYPos - 600);
        g.drawPixmap(shopButton,centerXPos - shopButton.getWidth()/2 ,centerYPos);
        g.drawPixmap(partyButton,centerXPos - partyButton.getWidth()/2,centerYPos + 500);
        g.drawPixmap(itemsButton,centerXPos - itemsButton.getWidth()-50,centerYPos + 500);
        g.drawPixmap(menuButton,centerXPos +50,centerYPos + 500);






        drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2, g.getHeight() - 42);
    }

    public void drawText(Graphics g, String line, int x, int y){
        int len = line.length();
        for (int i = 0; i < len; i++){
            char character = line.charAt(i);

            if(character == ' '){
                x += 20;
                continue;            }

            int srcX;
            int srcWidth;
            if(character == '.'){
                srcX = 200;
                srcWidth = 10;
            }else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }



}
