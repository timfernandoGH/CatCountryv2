package com.filip.androidgames.framework;

/**
 * Created by RE1010 on 2017-10-04.
 */

public interface Animations {

    public void run(int animID);
    public void update();
    public void getCurrentFrame();
    public void draw();
    public void pause();
    public void resume();
    public void dispose();
}
