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
    private static Pixmap blob;
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

    private Random random = new Random();

    public GameScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        blob = g.newPixmap("blob.png", Graphics.PixmapFormat.ARGB4444);
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);

        //Buttons
        mapButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        challButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        shopButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        menuButton = g.newPixmap("Ads.png",Graphics.PixmapFormat.ARGB4444);
        partyButton = g.newPixmap("PlayButton.png",Graphics.PixmapFormat.ARGB4444);
        itemsButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);

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
                if(inBounds(event,0,0,mapButton.getWidth(),mapButton.getHeight()))
                {
                    game.setScreen(new GameMapScreen(game));
                    return;
                }
                if (inBounds(event, blobXPos, blobYPos, blob.getWidth(), blob.getHeight())) {
                    oldScore++;
                    score = "" + oldScore;
                } else {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
        timePassed += deltaTime;
        if(timePassed > UPDATE_BLOB_TIME){
            blobXPos = random.nextInt(game.getGraphics().getWidth()-blob.getWidth());
            blobYPos = random.nextInt(game.getGraphics().getHeight()-blob.getHeight());
            timePassed = 0;
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
        g.drawPixmap(blob, blobXPos, blobYPos);

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
