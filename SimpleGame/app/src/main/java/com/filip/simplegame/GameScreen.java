package com.filip.simplegame;

/**
 * Created by RE1010 on 2017-09-30.
 */

import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Graphics;
import com.filip.androidgames.framework.Input.TouchEvent;
import com.filip.androidgames.framework.Marker;
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
    private static Pixmap markerUp;
    private static Pixmap markerDown;
    private static Pixmap line;

    private int centerXPos;
    private int centerYPos;



    private int numScore=100000;
    private String score = "100000";
    private String battlesString = "0";
    private String timerString = "0";
    private static Pixmap numberBattles;

    private static Pixmap catWalk;
    private static final float UPDATE_CAT_WALK = 0.5f;
    private float timePassed;
    private int catWalkHeight = 51;
    private int catWalkYSrc = 0;
    private int catWalkXSrc = 0;
    private int catWalkWidth = 25;

    private Marker marker1,marker2;
    private static Marker[] towns = new Marker[8];
    private static int numberOfTowns=2;
    private static Pixmap catAvatar;
    private static float decimaloffsetX;
    private static float decimaloffsetY;
    private static float avatarStepX;
    private static float avatarStepY;
    private static int avatarPosX;
    private static int avatarPosY;



    public GameScreen(Game game){
        super(game);
        credits = numScore;
        Graphics g = game.getGraphics();
        background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        map = g.newPixmap("gamemap.png", Graphics.PixmapFormat.ARGB4444);
        catAvatar = g.newPixmap("catavatar.png", Graphics.PixmapFormat.ARGB4444);
        numberBattles = g.newPixmap("numbersbattle.png",Graphics.PixmapFormat.ARGB4444);
        //Buttons
        challButton = g.newPixmap("Challenges.png",Graphics.PixmapFormat.ARGB4444);
        shopButton = g.newPixmap("Heal.png",Graphics.PixmapFormat.ARGB4444);
        partyButton = g.newPixmap("PartyButton.png",Graphics.PixmapFormat.ARGB4444);
        evolveButton = g.newPixmap("EvolveButton.png",Graphics.PixmapFormat.ARGB4444);
        //Markers Pixmap
        markerUp = g.newPixmap("Marker.png", Graphics.PixmapFormat.ARGB4444);
        markerDown = g.newPixmap("DarkerMarker.png", Graphics.PixmapFormat.ARGB4444);

        marker1 = new Marker(markerUp,true,0,0);
        marker2 = new Marker(markerUp,false,0,0);
        towns[0] = marker1;
        towns[1] = marker2;
        //Lines
        line = g.newPixmap("townlines.png", Graphics.PixmapFormat.ARGB4444);
        centerXPos = g.getWidth() /2;
        centerYPos = g.getHeight() /2;
        towns[0].setX(centerXPos-markerUp.getWidth()/2 - 100+map.getWidth()/2);
        towns[0].setY(centerYPos-markerUp.getWidth()/2+ 100-map.getHeight()/2);
        towns[1].setX(centerXPos-markerUp.getWidth()/2 - 100+map.getWidth()/2);
        towns[1].setY(centerYPos-markerUp.getWidth()/2-100+map.getHeight()/2);
        avatarPosX = towns[0].getX();
        avatarPosY = towns[0].getY();

    }

    @Override
    public void update(float deltaTime){

        if(isWalking)
        {
            walkTime = walkTime - deltaTime;

            timerString = "" + walkTime;
            //decimaloffsetX = decimaloffsetX + avatarStepX*deltaTime;
            //decimaloffsetY = decimaloffsetY + avatarStepY*deltaTime;
            //if((int)Math.abs(decimaloffsetX) > 0){
            //    avatarPosX = avatarPosX + (int)decimaloffsetX;
            //    avatarPosY = avatarPosY + (int)decimaloffsetY;
            //    decimaloffsetX = decimaloffsetX - (int)decimaloffsetX;
            //    decimaloffsetY = decimaloffsetY - (int)decimaloffsetY;
            //}
            avatarPosY = avatarPosY + (int)deltaTime+1* (int)(avatarStepY / Math.abs(avatarStepY));
            if((avatarPosY >= towns[town].getY()) && (avatarPosY <= towns[town].getY() + towns[town].getPixmap().getHeight()))
            {
                isWalking = false;
                outstandingBattles = 3;
                isBattleReady = true;
            }


            if(walkTime<= 0){
                isWalking = false;

            }
        }
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if(inBounds(event,centerXPos +350-challButton.getWidth()/2 ,centerYPos-challButton.getHeight()/2,challButton.getWidth(),challButton.getHeight()))
                {
                    game.setScreen(new GameChallengeScreen(game));
                    return;
                }
                if(inBounds(event, centerXPos - 350-shopButton.getWidth()/2, centerYPos-shopButton.getHeight()/2,shopButton.getWidth(),shopButton.getHeight()))
                {
                    game.setScreen(new GameHealScreen(game));
                    return;
                }
                if(inBounds(event, centerXPos -350-partyButton.getWidth()/2, centerYPos+450-partyButton.getHeight()/2,partyButton.getWidth(),partyButton.getHeight()))
                {
                    game.setScreen(new GamePartyScreen(game,false));
                    return;
                }
                if(inBounds(event, centerXPos -350-evolveButton.getWidth()/2,centerYPos-450-partyButton.getHeight()/2,evolveButton.getWidth(),evolveButton.getHeight()))
                {
                    game.setScreen(new GameEvolveScreen(game));
                    return;
                }
                for(int j = 0; j < numberOfTowns;j++) {
                    if(town != j) {
                        if (inBounds(event, towns[j].getX(), towns[j].getY()-30, towns[j].getPixmap().getWidth(), towns[j].getPixmap().getHeight())) {
                            if (!isWalking) {
                                towns[j].setPixmap(markerDown);
                                towns[j].down = true;
                                walkTime = 10000;
                                outstandingBattles = 0;
                                isWalking = true;
                                timerString = "" + walkTime;
                                avatarStepX = ((towns[j].getX() - towns[town].getX()) / walkTime);
                                avatarStepY = ((towns[j].getY() - towns[town].getY()) / walkTime);
                                //avatarPosX = towns[j].getX();
                                //avatarPosY = towns[j].getY();
                                town = j;
                                //do the timer
                            }
                        }
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

    @Override
    public void present(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawPixmap(background, 0, 0);

        //Draw Main Pet
        g.drawPixmap(mainPet.getPixmap(),g.getWidth()/2 + 200,g.getHeight()-mainPet.getPixmap().getWidth() - 50);

        //Draw Map
        g.drawPixmap(map,centerXPos-map.getWidth()/2,centerYPos-map.getHeight()/2);

        //Draw Lines
        g.drawPixmap(line, centerXPos-line.getWidth()/2-100+map.getWidth()/2,centerYPos-line.getWidth()/2+100-map.getHeight()/2);
        //Draw Markers

        g.drawPixmap(towns[0].getPixmap(),towns[0].getX(),towns[0].getY());


        g.drawPixmap(towns[1].getPixmap(),towns[1].getX(),towns[1].getY());
        //Draw Buttons
        if(isBattleReady) {
            g.drawPixmap(challButton, centerXPos + 350 - challButton.getWidth() / 2, centerYPos - challButton.getHeight() / 2);
        }
        g.drawPixmap(catAvatar,avatarPosX,avatarPosY);
        g.drawPixmap(shopButton,centerXPos - 350 - shopButton.getWidth()/2 ,centerYPos-shopButton.getHeight()/2);
        g.drawPixmap(partyButton,centerXPos -350 - partyButton.getWidth()/2,centerYPos-partyButton.getHeight()/2+450);
        g.drawPixmap(evolveButton,centerXPos -350 - evolveButton.getWidth()/2,centerYPos-evolveButton.getHeight()/2 -450);

        drawText(g, score, g.getWidth() / 2 +350, 0,numbers);
        drawText(g,battlesString,centerXPos+350 - numberBattles.getWidth()/2,centerYPos-numberBattles.getHeight()/2,numberBattles);
    }

    public void drawText(Graphics g, String line, int x, int y,Pixmap p){
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

            g.drawPixmap(p, x, y, 0, srcX, srcWidth, 20);
            y += srcWidth;
        }
    }



}
