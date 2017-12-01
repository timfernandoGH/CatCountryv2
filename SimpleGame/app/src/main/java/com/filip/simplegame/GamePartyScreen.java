package com.filip.simplegame;

import com.filip.androidgames.framework.Screen;
import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;

import java.util.List;

/**
 * Created by Admiral AreoSpeedwag on 11/30/2017.
 */



public class GamePartyScreen extends Screen {
    private static Pixmap numbers;
    private static Pixmap healButton;
    private static Pixmap background;

    private String score;
    private int centerXPos;
    private int centerYPos;

    private int posY = 100;
    public GamePartyScreen(Game game)
    {
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png",Graphics.PixmapFormat.RGB565);

        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
    }
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                for(int j = 0; j < totalOwnedPets;j++) {
                    if (inBounds(event, pets[j].getX(), pets[j].y, pets[j].getPixmap().getWidth(), pets[j].getPixmap().getHeight())) {
                        mainPet = pets[j];
                        return;
                    }
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
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        int pY = posY;
        g.drawPixmap(background,0,0);
        g.drawPixmap(mainPet.getPixmap(),g.getWidth()/2 + 200,g.getHeight()-mainPet.getPixmap().getWidth() - 50);
        for(int i = 0; i < totalOwnedPets; i++)
        {
            g.drawPixmap(pets[i].getPixmap(),g.getWidth()/2,pY);
            pets[i].setX(g.getWidth()/2);
            pets[i].setY(pY);
            pY = pY+pets[i].getPixmap().getWidth()+50;
        }
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
