package com.filip.simplegame;

/**
 * Created by Admiral AreoSpeedwag on 10/9/2017.
 */

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;
import java.util.List;

public class GameMapScreen extends Screen {

    private static Pixmap background;
    private static Pixmap playButton;
    private static Pixmap gameMap;
    private static Pixmap menuButton;
    private static Pixmap partyButton;
    private static Pixmap itemsButton;

    private int playXPos;
    private int playYPos;

    private int centerXPos;
    private int centerYPos;

    public GameMapScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        playButton = g.newPixmap("Challenges.png", Graphics.PixmapFormat.ARGB4444);
        gameMap = g.newPixmap("gamemap.png",Graphics.PixmapFormat.ARGB4444);

        playXPos = g.getWidth() / 2 - playButton.getWidth() / 2;
        playYPos = g.getHeight() / 3 - playButton.getHeight() / 2;

        menuButton = g.newPixmap("Menu.png",Graphics.PixmapFormat.ARGB4444);
        partyButton = g.newPixmap("Party.png",Graphics.PixmapFormat.ARGB4444);
        itemsButton = g.newPixmap("Items.png",Graphics.PixmapFormat.ARGB4444);

        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
    }
    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                if (inBounds(event, playXPos, playYPos- 100, playButton.getWidth(), playButton.getHeight())){
                    game.setScreen(new GameChallengeScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();

        g.drawPixmap(background, 0, 0);
        g.drawPixmap(playButton, playXPos, playYPos - 100);
        g.drawPixmap(gameMap,g.getWidth()/2 - gameMap.getWidth()/2,g.getHeight()/2 - gameMap.getHeight()/2);

        g.drawPixmap(partyButton,centerXPos - partyButton.getWidth()/2,centerYPos + 500);
        g.drawPixmap(itemsButton,centerXPos - itemsButton.getWidth()-50,centerYPos + 500);
        g.drawPixmap(menuButton,centerXPos +50,centerYPos + 500);
    }
    @Override
    public void pause(){}
    @Override
    public void resume(){}
    @Override
    public void dispose(){}
}
