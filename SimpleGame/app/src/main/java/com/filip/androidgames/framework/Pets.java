package com.filip.androidgames.framework;

/**
 * Created by Admiral AreoSpeedwag on 12/1/2017.
 */

public class Pets {
    private Pixmap pixmap;
    public int x, y;
    private int hp;
    private int maxhp;
    private int attack;
    private Attacks[] attackList = new Attacks[4];
    public Pixmap getPixmap(){return pixmap;}
    public int getHp(){return hp;}
    public int getMaxhp(){return  maxhp;}
    public int getAttack(){return attack;}
    public Attacks[] getAttackList(){return attackList;}
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public Pets(Pixmap pixmap,int x, int y, int hp ,int maxhp,int attack, Attacks[] learnedAttacks){
        this.pixmap = pixmap;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.attack = attack;
        this.attackList = learnedAttacks;
    }
}
