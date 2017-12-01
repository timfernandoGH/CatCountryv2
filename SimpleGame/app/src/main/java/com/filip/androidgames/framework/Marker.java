package com.filip.androidgames.framework;

/**
 * Created by Admiral AreoSpeedwag on 12/1/2017.
 */

public class Marker {
    private Pixmap pixmap;
    public int x, y;
    public boolean down;
    public Pixmap getPixmap(){return pixmap;}
    public void setPixmap(Pixmap pixmap){ this.pixmap = pixmap;}
    public boolean isDown(){return down;}
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public Marker(Pixmap pixmap,boolean down,int x, int y)
    {
        this.pixmap = pixmap;
        this.down = down;
        this.x = x;
        this.y = y;
    }

}
