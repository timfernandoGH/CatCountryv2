package com.filip.simplegame;

/**
 * Created by RE1010 on 2017-09-30.
 */

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;
import com.filip.androidgames.framework.DrawText;

import java.util.List;

public class MainMenuScreen extends Screen {

    private String title ;

    private static Pixmap background;
    private static Pixmap playButton;
    private static Pixmap titleImg;
    private static Pixmap ads;

    private int playXPos;
    private int playYPos;

    public MainMenuScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();

        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        playButton = g.newPixmap("PlayButton.png", Graphics.PixmapFormat.ARGB4444);
        titleImg = g.newPixmap("CatCountryTitle.png", Graphics.PixmapFormat.ARGB4444);
        ads = g.newPixmap("Ads.png", Graphics.PixmapFormat.ARGB4444);

        playXPos = g.getWidth() / 2 - playButton.getWidth() / 2;
        playYPos = g.getHeight() / 2 - playButton.getHeight() / 2;
    }

    @Override
    public void update(float deltaTime){
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                if (inBounds(event, playXPos, playYPos - 100, playButton.getWidth(), playButton.getHeight())){
                    game.setScreen(new GameScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);
        g.drawPixmap(playButton, playXPos, playYPos - 100);
        g.drawPixmap(titleImg,playXPos,playYPos - 500);
        g.drawPixmap(ads,playXPos,playYPos + 125);

    }

    @Override
    public void pause(){}
    @Override
    public void resume(){}
    @Override
    public void dispose(){}


}
