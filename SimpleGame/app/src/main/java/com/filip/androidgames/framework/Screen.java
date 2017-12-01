package com.filip.androidgames.framework;

public abstract class Screen {
	
    protected final Game game;
    protected static int credits;
    protected static Pixmap mainPet;
    protected static Pixmap pets[] = new Pixmap[100];
    protected static Pixmap ownedPets[] = new Pixmap[100];
    protected static int totalOwnedPets = 2;
    protected static int totalPets =2;
    public Screen(Game game)
    {
        this.game = game;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

    protected boolean inBounds(Input.TouchEvent event,
                             int x, int y,
                             int width, int height) {
        if (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}


