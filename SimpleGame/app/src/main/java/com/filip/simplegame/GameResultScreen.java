package com.filip.simplegame;

import com.filip.androidgames.framework.Attacks;
import com.filip.androidgames.framework.Pets;
import com.filip.androidgames.framework.Screen;
import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;

import java.util.List;
import java.util.Random;

/**
 * Created by Admiral AreoSpeedwag on 12/16/2017.
 */


public class GameResultScreen extends Screen {
    //Victory Aseets
    private Pets petReward;
    private Pixmap petRewardPixmap;
    private Pixmap victoryTitle;
    private Pixmap victoryText;
    private Pixmap numbers2;
    private Attacks[] petAttacks = {new Attacks("Furball",30), new Attacks("DeathLaser", 50)};
    private float moneyReward;
    private String moneyString = "";

    //BaseScreen
    private Pixmap background;
    private Pixmap numbers;
    private String score ="";

    //Defeat Assets
    private Pixmap defeatTitle;
    private Pixmap defeatText;
    private Pixmap continueButton;
    private Pixmap EscapeButton;

    private int continuePosX;
    private int continuePosY;

    private int escapePosX;
    private int escapePosY;

    private boolean isVictorious;


    public GameResultScreen(Game game, boolean isVictorious)
    {
        super(game);
        score = "" + credits;
        Graphics g = game.getGraphics();
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);

        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        this.isVictorious = isVictorious;
        if(isVictorious) {
            Random r = new Random(100);
            moneyReward = r.nextFloat() * 10 + 500;
            petRewardPixmap = g.newPixmap("cat.png", Graphics.PixmapFormat.ARGB4444);
            victoryTitle = g.newPixmap("victorytitle.png", Graphics.PixmapFormat.ARGB4444);
            victoryText = g.newPixmap("rewardstext.png", Graphics.PixmapFormat.ARGB4444);
            numbers2 = g.newPixmap("numbers.png",Graphics.PixmapFormat.ARGB4444);
            moneyString = "" + moneyReward;

            petReward = new Pets(petRewardPixmap, 0, 0, 100, 100, 2, petAttacks);
            ownedPets[totalOwnedPets] = petReward;
            totalOwnedPets++;
        } else{
            defeatTitle = g.newPixmap("defeated.png",Graphics.PixmapFormat.ARGB4444);
            defeatText = g.newPixmap("resultInstructions.png",Graphics.PixmapFormat.ARGB4444);
            continueButton = g.newPixmap("ContinueButton.png",Graphics.PixmapFormat.ARGB4444);
            EscapeButton = g.newPixmap("EscapeButton.png", Graphics.PixmapFormat.ARGB4444);

            continuePosX = 0;
            continuePosY = 0;

            escapePosX = 0;
            escapePosY = g.getHeight();
        }

    }

    public void update(float deltaTime)
    {
        if(isWalking)
        {
            walkTime = walkTime - deltaTime;
        }

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                if(!isVictorious) {
                    if (inBounds(event, continuePosX, continuePosY, continueButton.getWidth(), continueButton.getHeight())) {
                        game.setScreen(new GamePartyScreen(game, true));
                        return;
                    }
                    if (inBounds(event, escapePosX, escapePosY, EscapeButton.getWidth(), EscapeButton.getHeight())) {
                        outstandingBattles--;
                        game.setScreen(new GameScreen(game));
                    }
                } else {
                    credits = credits + (int)moneyReward;
                    game.setScreen(new GameScreen(game));
                }
            }
        }
    }

    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);
        if(!isVictorious)
        {
            g.drawPixmap(defeatTitle,g.getWidth()-defeatTitle.getWidth()/2,g.getHeight()/2);
            g.drawPixmap(defeatText,g.getWidth()/2,g.getHeight()/2);
            g.drawPixmap(continueButton,continuePosX,continuePosY);
            g.drawPixmap(EscapeButton,escapePosX,escapePosY);
        }
        else
        {
            g.drawPixmap(victoryTitle,g.getWidth()-victoryTitle.getWidth()/2,g.getHeight()/2);
            g.drawPixmap(victoryText,g.getWidth()-victoryTitle.getWidth()-victoryText.getWidth()/2,g.getHeight()/2);
            g.drawPixmap(petRewardPixmap,g.getWidth()-victoryTitle.getWidth()-victoryText.getWidth()-petRewardPixmap.getWidth()/2,g.getHeight()/2);
            drawText(g, moneyString,g.getWidth()-victoryTitle.getWidth()-victoryText.getWidth()-petRewardPixmap.getWidth()-numbers2.getWidth()/2,g.getHeight()/2,numbers2);
        }

        drawText(g, score, g.getWidth() / 2 +350, 0,numbers);


    }

    public void drawText(Graphics g, String line, int x, int y,Pixmap p) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                y += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 32;
            }

            g.drawPixmap(p, x, y, 0, srcX, srcWidth, 20);
            y += srcWidth;
        }
    }

    public void pause(){}

    public void resume(){}

    public void dispose(){}


}
