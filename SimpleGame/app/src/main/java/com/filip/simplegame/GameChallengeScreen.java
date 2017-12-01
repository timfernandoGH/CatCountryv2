package com.filip.simplegame;

/**
 * Created by Admiral AreoSpeedwag on 10/10/2017.
 */
import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;
import java.util.List;
public class GameChallengeScreen extends  Screen
{
    private static Pixmap background;
    private static Pixmap numbers;
    private static Pixmap enemyHealth;
    private static Pixmap escapeButton;
    private static Pixmap enemycatImg;
    private static Pixmap catImg;
    private static Pixmap healthImg;
    private static Pixmap ehealthImg;
    private static Pixmap enemyText;
    private static Pixmap playerText;
    private static Pixmap lvlText;
    private static Pixmap dmgText;
    private static Pixmap hitCount;
    private static Pixmap attachImg;
    private static Pixmap swipeHere;

    private static String health = "10";
    private static String hits = " ";

    private int centerXPos;
    private int centerYPos;

    private int combo = 0;
    private int enemycurrHealth = 10;

    public GameChallengeScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);

        //Images
        escapeButton = g.newPixmap("Escape.png", Graphics.PixmapFormat.ARGB4444);
        enemycatImg = g.newPixmap("enemycat.png", Graphics.PixmapFormat.ARGB4444);
        catImg = g.newPixmap("cat.png", Graphics.PixmapFormat.ARGB4444);
        healthImg = g.newPixmap("heart.png", Graphics.PixmapFormat.ARGB4444);
        ehealthImg = g.newPixmap("heart.png", Graphics.PixmapFormat.ARGB4444);
        enemyText = g.newPixmap("Text.png", Graphics.PixmapFormat.ARGB4444);
        playerText = g.newPixmap("Text.png", Graphics.PixmapFormat.ARGB4444);
        lvlText = g.newPixmap("Text.png", Graphics.PixmapFormat.ARGB4444);
        dmgText = g.newPixmap("Text.png", Graphics.PixmapFormat.ARGB4444);
        hitCount = g.newPixmap("hitcount.png", Graphics.PixmapFormat.ARGB4444);
        attachImg = g.newPixmap("attack.png", Graphics.PixmapFormat.ARGB4444);
        swipeHere = g.newPixmap("arrow.png", Graphics.PixmapFormat.ARGB4444);

        //Text
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        enemyHealth = g.newPixmap("numbershealthy.png" , Graphics.PixmapFormat.ARGB4444);

        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;

        enemycurrHealth = 10;
    }
    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                if (inBounds(event, centerXPos-250, centerYPos-600, escapeButton.getWidth(), escapeButton.getHeight())){
                    game.setScreen(new GameScreen(game));
                    return;
                }
                if(inBounds(event,centerXPos+250,centerYPos+400,hitCount.getWidth(),hitCount.getHeight()))
                {
                    combo++;
                    hits = "" + combo;
                    return;
                }
                if(inBounds(event,centerXPos-250,centerYPos+400,attachImg.getWidth(),attachImg.getHeight()))
                {
                    enemycurrHealth = enemycurrHealth - combo;
                    combo = 0;
                    hits = "" + combo;
                    health = "" + enemycurrHealth;
                    if(enemycurrHealth <= 0)
                    {
                        game.setScreen(new GameMapScreen(game));
                    }
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

        //Images
        g.drawPixmap(escapeButton, centerXPos - 250 - escapeButton.getWidth()/2,centerYPos - 600);
        g.drawPixmap(enemycatImg,centerXPos + 250 - enemycatImg.getWidth()/2,centerYPos - 500);
        g.drawPixmap(catImg,centerXPos -250 - catImg.getWidth()/2,centerYPos);
        g.drawPixmap(ehealthImg, centerXPos - 250 - healthImg.getWidth()/2,centerYPos -400);
        g.drawPixmap(healthImg,centerXPos + 250 - ehealthImg.getWidth()/2,centerYPos +100);
        g.drawPixmap(swipeHere,centerXPos - swipeHere.getWidth()/2,centerYPos +400);
        g.drawPixmap(hitCount,centerXPos +250 - hitCount.getWidth()/2,centerYPos + 400);
        g.drawPixmap(attachImg,centerXPos - 250 - attachImg.getWidth()/2,centerYPos + 400);

        //Text
        //g.drawPixmap(enemyText,centerXPos + 250 - enemyText.getWidth()/2,centerYPos - 150);
        g.drawPixmap(playerText,centerXPos -250 - playerText.getWidth()/2,centerYPos - 25);
        //g.drawPixmap(lvlText,centerXPos -250 - lvlText.getWidth()/2,centerYPos+370);
        //g.drawPixmap(dmgText,centerXPos +250 - dmgText.getWidth()/2,centerYPos+370);

        drawText(g,hits,centerXPos+250,centerYPos + 400-15);
        drawTextHealth(g,health,centerXPos + 250 - enemyText.getWidth()/2,centerYPos -150);
    }
    @Override
    public void pause(){}
    @Override
    public void resume(){}
    @Override
    public void dispose(){}

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
    public void drawTextHealth(Graphics g, String line, int x, int y){
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

            g.drawPixmap(enemyHealth, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
}
