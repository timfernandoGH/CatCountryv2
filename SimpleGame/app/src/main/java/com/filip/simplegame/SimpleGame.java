package com.filip.simplegame;

/**
 * Created by RE1010 on 2017-09-30.
 */
import com.filip.androidgames.framework.Game;
import com.filip.androidgames.framework.Screen;
import com.filip.androidgames.framework.impl.AndroidGame;

public class SimpleGame extends AndroidGame{

    @Override
    public Screen getStartScreen(){

        return new MainMenuScreen(this);
    }
    @Override
    public void onSignInFailed(){}
    @Override
    public void onSignInSucceeded(){}
}