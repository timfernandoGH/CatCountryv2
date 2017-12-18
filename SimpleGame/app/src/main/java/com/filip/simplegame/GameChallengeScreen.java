package com.filip.simplegame;

/**
 * Created by Admiral AreoSpeedwag on 10/10/2017.
 */
import com.filip.androidgames.framework.Attacks;
import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Pixmap;
import com.filip.androidgames.framework.Screen;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class GameChallengeScreen extends  Screen
{
    private static Pixmap background;
    private static Pixmap numbers;
    private static Pixmap enemyHealth;
    private static Pixmap escapeButton;
    private static Pixmap enemycatImg;
    private static Pixmap catImg;
    public static String catChosen = "StarterCatBack01.png";
    private static Pixmap[] attacks = new Pixmap[2];
    private static Pixmap[] hpbar = new Pixmap[2];
    private static Pixmap[] dmgbar = new Pixmap[2];
    private static Pixmap victoryImg;
    private static Pixmap defeatImg;
    private static Pixmap dmgImg;
    private static boolean victory = false;
    private static boolean defeat = false;
    private static boolean damage = false;
    public static boolean animate = true;
    private static String health = "10";
    private static String hits = " ";
    private static final float m_updateAnim = 0.5f;
    private float timePassed;
    private float dmgDelay;
    private int catYSrc = 0;
    private int enemyYSrc = 0;

    private int centerXPos;
    private int centerYPos;

    private int combo = 0;
    private int enemycurrHealth = 200;
    private int enemyMaxHealth = 200;

    private Timer t;

    public GameChallengeScreen(Game game){
        super(game);
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        t = new Timer();
        // Images
        victoryImg = g.newPixmap("Victory.png", Graphics.PixmapFormat.ARGB4444);
        defeatImg = g.newPixmap("Defeat.png", Graphics.PixmapFormat.ARGB4444);
        dmgImg = g.newPixmap("DMG.png", Graphics.PixmapFormat.ARGB4444);
        escapeButton = g.newPixmap("BackButton.png", Graphics.PixmapFormat.ARGB4444);
        enemycatImg = g.newPixmap("Cathulu.png", Graphics.PixmapFormat.ARGB4444);
        catImg = g.newPixmap(catChosen, Graphics.PixmapFormat.ARGB4444);

        //Text
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        //enemyHealth = g.newPixmap("numbershealthy.png" , Graphics.PixmapFormat.ARGB4444);

        //Buttons
            attacks[0] = g.newPixmap("FurBallButton.png",Graphics.PixmapFormat.ARGB4444);
            attacks[1] = g.newPixmap("DeathLaserButton.png", Graphics.PixmapFormat.ARGB4444);

            hpbar[0] = g.newPixmap("HealthBar.png", Graphics.PixmapFormat.ARGB4444);
            hpbar[1] = g.newPixmap("HealthBar.png", Graphics.PixmapFormat.ARGB4444);

            dmgbar[0] = g.newPixmap("DamageBar.png", Graphics.PixmapFormat.ARGB4444);
            dmgbar[1] = g.newPixmap("DamageBar.png", Graphics.PixmapFormat.ARGB4444);

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
                Attacks[] moves = new Attacks[2];
                moves = mainPet.getAttackList();
                if(victory)
                {
                    game.setScreen(new GameScreen(game));
                    victory = false;
                    enemycurrHealth = 200;
                    GameScreen.challengeActive = false;
                    return;
                }
                if(defeat)
                {
                    game.setScreen(new GameScreen(game));
                    defeat = false;
                    enemycurrHealth = 200;
                    GameScreen.challengeActive = false;
                    return;
                }
                if(inBounds(event,0,20,attacks[0].getWidth(),attacks[0].getHeight()))
                {
                    enemycurrHealth = enemycurrHealth - moves[0].damage;
                    damage = true;
                    //run AI
                    runAI();
                }
                else if(inBounds(event,0,attacks[1].getHeight() + 10,attacks[1].getWidth(),attacks[1].getHeight()))
                {
                    enemycurrHealth = enemycurrHealth - moves[1].damage;
                    damage = true;
                    //run AI
                    runAI();
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
        if(damage)
        {
            g.drawPixmap(dmgImg, g.getWidth() - enemycatImg.getWidth(), g.getHeight() - enemycatImg.getHeight() / 3);
            dmgDelay += deltaTime;
            if(dmgDelay > m_updateAnim/2)
            {
                dmgDelay = 0;
                damage = false;
            }
        }
        g.drawPixmap(escapeButton, g.getWidth() - escapeButton.getWidth(),0);
        g.drawPixmap(attacks[0],0,20);
        g.drawPixmap(attacks[1],0,attacks[1].getHeight() + 10);
        double playerperc = (double)mainPet.getHp()/(double)mainPet.getMaxhp();
        double enemyperc = (double)enemycurrHealth/(double)enemyMaxHealth;
        g.drawPixmap(dmgbar[0],80,30);
        g.drawPixmap(dmgbar[1], g.getWidth() -enemycatImg.getWidth(),g.getHeight()-enemycatImg.getHeight()/3);
        g.drawPixmap(hpbar[0],80,30,0,0,hpbar[0].getWidth(),(int)(hpbar[0].getHeight() * playerperc));
        g.drawPixmap(hpbar[1], g.getWidth() -enemycatImg.getWidth(),g.getHeight()-enemycatImg.getHeight()/3,0,0,hpbar[1].getWidth(),(int)(hpbar[1].getHeight() * enemyperc));


        //g.drawPixmap(catImg,centerXPos -250 - catImg.getWidth()/2,centerYPos);
        //g.drawPixmap(ehealthImg, centerXPos - 250 - healthImg.getWidth()/2,centerYPos -400);
        //g.drawPixmap(healthImg,centerXPos + 250 - ehealthImg.getWidth()/2,centerYPos +100);
        //g.drawPixmap(swipeHere,centerXPos - swipeHere.getWidth()/2,centerYPos +400);
        //g.drawPixmap(hitCount,centerXPos +250 - hitCount.getWidth()/2,centerYPos + 400);
        //g.drawPixmap(attachImg,centerXPos - 250 - attachImg.getWidth()/2,centerYPos + 400);

        //Text
        //g.drawPixmap(enemyText,centerXPos + 250 - enemyText.getWidth()/2,centerYPos - 150);
        //g.drawPixmap(playerText,centerXPos -250 - playerText.getWidth()/2,centerYPos - 25);
        //g.drawPixmap(lvlText,centerXPos -250 - lvlText.getWidth()/2,centerYPos+370);
        //g.drawPixmap(dmgText,centerXPos +250 - dmgText.getWidth()/2,centerYPos+370);

        //drawText(g,hits,centerXPos+250,centerYPos + 400-15);
        //drawTextHealth(g,health,centerXPos + 250 - enemyText.getWidth()/2,centerYPos -150);

        // Animations
        if(animate){
            timePassed += deltaTime;
            if(timePassed > m_updateAnim)
            {
                catYSrc += catImg.getHeight()/8;
                enemyYSrc += enemycatImg.getHeight()/3;
                timePassed = 0;

                if(catYSrc == catImg.getHeight()){
                    catYSrc = 0;
                }

                if(enemyYSrc == enemycatImg.getHeight()){
                    enemyYSrc = 0;
                }
            }
        }

            g.drawPixmap(catImg, 90, 30, 0, catYSrc, catImg.getWidth(), catImg.getHeight() / 8);
            g.drawPixmap(enemycatImg, g.getWidth() - enemycatImg.getWidth(), g.getHeight() - enemycatImg.getHeight() / 3, 0, enemyYSrc, enemycatImg.getWidth(), enemycatImg.getHeight() / 3);

        if(victory)
        {
            animate = false;
            g.drawPixmap(victoryImg,centerXPos-victoryImg.getWidth()/2,centerYPos-victoryImg.getHeight()/2);
        }

        else if(defeat)
        {
            animate = false;
            g.drawPixmap(defeatImg, centerXPos-defeatImg.getWidth()/2, centerYPos-defeatImg.getHeight()/2);
        }
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
    public void runAI()
    {
        mainPet.setHp(mainPet.getHp() - 40);
        if(enemycurrHealth <= 0)
        {
            victory = true;
        }

        if(mainPet.getHp() <= 0)
        {
            defeat = true;
        }

    }
}
