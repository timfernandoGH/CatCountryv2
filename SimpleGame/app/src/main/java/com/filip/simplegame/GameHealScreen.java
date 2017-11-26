package com.filip.simplegame;

/**
 * Created by Admiral AreoSpeedwag on 11/25/2017.
 */
import android.view.ViewDebug;

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;

import java.util.List;

public class GameHealScreen extends Screen{
    private static Pixmap numbers;
    private static Pixmap healButton;
    private static Pixmap background;
    private String score;
    private int centerXPos;
    private int centerYPos;
    public GameHealScreen(Game game)
    {
        super(game);

        score = "" + credits;

        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        healButton = g.newPixmap("Button.png",Graphics.PixmapFormat.ARGB4444);

        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
    }
    public void update(float deltaTime){
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, centerXPos,centerYPos,healButton.getWidth(),healButton.getHeight()))
                {
                    credits = this.credits - 1000;
                    score = "" + credits;
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
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(background,0,0);
        g.drawPixmap(healButton,centerXPos - healButton.getWidth()/2,centerYPos-healButton.getHeight()/2);

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
