package com.filip.simplegame;

/**
 * Created by RE1010 on 2017-09-30.
 */

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;
import com.filip.androidgames.framework.Pets;
import com.filip.androidgames.framework.Attacks;

import java.util.List;

public class MainMenuScreen extends Screen {

    private String title ;

    private static Pixmap background;
    private static Pixmap playButton;
    private static Pixmap titleImg;
    private static Pixmap leaderboardButton;

    private int playXPos;
    private int playYPos;

    private int leaderXPos;
    private int leaderYPos;

    private Attacks[] defAttacks = {new Attacks("Furball",30),new Attacks("DeathLaser", 100)};

    public MainMenuScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        //Initialize Pets

        pets[0] = new Pets(g.newPixmap("catportrait.png", Graphics.PixmapFormat.ARGB4444),0,0,100,200,5,defAttacks);
        pets[1] = new Pets(g.newPixmap("catportrait2.png", Graphics.PixmapFormat.ARGB4444),0,0,125,250,6,defAttacks);




        mainPet = pets[0];
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        playButton = g.newPixmap("PlayButton.png", Graphics.PixmapFormat.ARGB4444);
        titleImg = g.newPixmap("CatCountryTitle.png", Graphics.PixmapFormat.ARGB4444);
        leaderboardButton = g.newPixmap("LeaderButton.png", Graphics.PixmapFormat.ARGB4444);

        playXPos = g.getWidth() / 2 - playButton.getWidth() / 2;
        playYPos = g.getHeight() / 2 - playButton.getHeight() / 2;

        leaderXPos = g.getWidth()/ 2 - leaderboardButton.getWidth() / 2 - 100;
        leaderYPos = g.getHeight() / 2 - leaderboardButton.getHeight() / 2;
    }

    @Override
    public void update(float deltaTime){
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                if (inBounds(event, playXPos, playYPos , playButton.getWidth(), playButton.getHeight())){
                    game.setScreen(new GameScreen(game));
                    return;
                }
                if (inBounds(event, leaderXPos, leaderYPos, leaderboardButton.getWidth(), leaderboardButton.getHeight())){
                    // place link to the leaderboard screen here
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);
        g.drawPixmap(playButton, playXPos, playYPos );
        g.drawPixmap(leaderboardButton, leaderXPos, leaderYPos);
        g.drawPixmap(titleImg,g.getWidth()/2+250,g.getHeight()/2-titleImg.getHeight()/2);
        //g.drawPixmap(ads,playXPos,playYPos + 125);

    }

    @Override
    public void pause(){}
    @Override
    public void resume(){}
    @Override
    public void dispose(){}


}
