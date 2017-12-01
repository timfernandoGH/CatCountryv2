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
    private Pixmap healthbar[] = new Pixmap[totalOwnedPets];
    private Pixmap dmgbar[] = new Pixmap[totalOwnedPets];
    private static Pixmap back;
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
        healButton = g.newPixmap("Heal.png",Graphics.PixmapFormat.ARGB4444);
        back = g.newPixmap("BackButton.png", Graphics.PixmapFormat.ARGB4444);
        for(int i=0;i<totalOwnedPets;i++) {
            healthbar[i] = g.newPixmap("HealthBar.png", Graphics.PixmapFormat.ARGB4444);
            dmgbar[i] = g.newPixmap("DamageBar.png", Graphics.PixmapFormat.ARGB4444);
        }
        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
    }
    public void update(float deltaTime){
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, game.getGraphics().getWidth() - healButton.getWidth(),centerYPos-healButton.getHeight()/2,healButton.getWidth(),healButton.getHeight()))
                {
                    credits = this.credits - 1000;
                    score = "" + credits;
                    for(int j =0; j < totalOwnedPets;j++)
                    {
                        pets[j].setHp(pets[j].getMaxhp());
                    }
                    mainPet.setHp(mainPet.getMaxhp());
                }
                if(inBounds(event,0+back.getWidth(),centerYPos-back.getHeight()/2,back.getWidth(),back.getHeight()))
                {
                    game.setScreen(new GameScreen(game));
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
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        int pY = 100;

        g.drawPixmap(background,0,0);
        g.drawPixmap(mainPet.getPixmap(),g.getWidth()/2 + 200,g.getHeight()-mainPet.getPixmap().getWidth() - 50);
        for(int i = 0; i < totalOwnedPets; i++)
        {
            g.drawPixmap(pets[i].getPixmap(),g.getWidth()/2,pY);
            pets[i].setX(g.getWidth()/2);
            pets[i].setY(pY);
            double percentage =((double)pets[i].getHp()/(double)pets[i].getMaxhp());
            g.drawPixmap(dmgbar[i],pets[i].getX()+pets[i].getPixmap().getWidth()+10,pets[i].getY());
            g.drawPixmap(healthbar[i],pets[i].getX()+pets[i].getPixmap().getWidth()+10,pets[i].getY(),0,0,19 ,(int)(healthbar[i].getHeight() * percentage) );
            pY = pY+pets[i].getPixmap().getWidth()+60;
        }
        g.drawPixmap(healButton,g.getWidth() - healButton.getWidth(),centerYPos-healButton.getHeight()/2);
        g.drawPixmap(back,0+back.getWidth(),centerYPos-back.getHeight()/2);

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
