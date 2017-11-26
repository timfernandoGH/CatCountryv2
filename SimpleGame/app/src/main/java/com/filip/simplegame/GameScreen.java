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
public class GameScreen extends Screen {

    private static final float UPDATE_BLOB_TIME = 1.0f;

    private static Pixmap background;
    private static Pixmap numbers;
    //Buttons
    private static Pixmap challButton;
    private static Pixmap shopButton;
    private static Pixmap partyButton;
    private static Pixmap evolveButton;
    private static Pixmap map;

    private int centerXPos;
    private int centerYPos;

    private int numScore=100000;
    private String score = "100000";

    private float timePassed;


    public GameScreen(Game game){
        super(game);
        credits = numScore;
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        map = g.newPixmap("gamemap.png", Graphics.PixmapFormat.ARGB4444);
        //Buttons
        challButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        shopButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        partyButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);
        evolveButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);

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
                if(inBounds(event,centerXPos +350 ,centerYPos,challButton.getWidth(),challButton.getHeight()))
                {
                    game.setScreen(new GameChallengeScreen(game));
                    return;
                }
                if(inBounds(event, centerXPos - 350, centerYPos,shopButton.getWidth(),shopButton.getHeight()))
                {
                    game.setScreen(new GameHealScreen(game));
                    return;
                }
                if(inBounds(event, centerXPos -350, centerYPos+450,partyButton.getWidth(),partyButton.getHeight()))
                {
                    game.setScreen(new GameMapScreen(game));
                    return;
                }
                if(inBounds(event, centerXPos -350,centerYPos-450,evolveButton.getWidth(),evolveButton.getHeight()))
                {
                    game.setScreen(new GameMapScreen(game));
                    return;
                }
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
        g.drawPixmap(map,centerXPos-map.getWidth()/2,centerYPos-map.getHeight()/2);
        g.drawPixmap(challButton,centerXPos + 350 -challButton.getWidth()/2,centerYPos-challButton.getHeight() /2);
        g.drawPixmap(shopButton,centerXPos - 350 - shopButton.getWidth()/2 ,centerYPos-shopButton.getHeight()/2);
        g.drawPixmap(partyButton,centerXPos -350 - partyButton.getWidth()/2,centerYPos-partyButton.getHeight()/2+450);
        g.drawPixmap(evolveButton,centerXPos -350 - evolveButton.getWidth()/2,centerYPos-evolveButton.getHeight()/2 -450);






        drawText(g, score, g.getWidth() / 2 +350, 0);
    }

    public void drawText(Graphics g, String line, int x, int y){
        int len = line.length();
        for (int i = 0; i < len; i++){
            char character = line.charAt(i);

            if(character == ' '){
                y += 20;
                continue;            }

            int srcX;
            int srcWidth;
            if(character == '.'){
                srcX = 200;
                srcWidth = 10;
            }else {
                srcX = (character - '0') * 20;
                srcWidth = 32;
            }

            g.drawPixmap(numbers, x, y, 0, srcX, srcWidth, 20);
            y += srcWidth;
        }
    }



}
