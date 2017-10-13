package com.filip.androidgames.framework.impl;

/**
 * Created by RE1010 on 2017-10-04.
 */


import com.filip.androidgames.framework.Animations;

import android.app.Activity;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;


public class AndroidAnimation extends Activity implements Animations {

    int animId;
    AndroidAnimation animator;
    boolean isPrepared = false;

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playing;
    Canvas canvas;
    Paint paint;
    long fps;
    private long timeThisFrame;

    Bitmap animBitmap;
    boolean isMoving = false;
    float walkSpeedPerSecond = 0;
    float animXPosition = 250;
    float boxYPosition = 500;

    private int frameWidth = 92;
    private int frameHeight = 222;
    private int frameCount = 5;

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMilliseconds = 200;

    private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
    RectF whereToDraw = new RectF(animXPosition,animXPosition,animXPosition + frameWidth, frameHeight);

    public AndroidAnimation(int _animId){
        this.animId = _animId;
    }

    @Override
    public void run(int animId) {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            // Calculate the fps this frame
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    @Override
    public void update() {
        if(isMoving){
            animXPosition = animXPosition + (walkSpeedPerSecond / fps);
        }
    }

    @Override
    public void getCurrentFrame(){
        long time  = System.currentTimeMillis();
        if(isMoving) {
            if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame ++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        //update the left and right values of the source of the next frame on the spritesheet
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }


    @Override
    // Draw the newly updated scene
    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();
            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));
            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  249, 129, 0));
            // Make the text a bit bigger
            paint.setTextSize(10);
            canvas.drawText("FPS:" + fps, 20, 40, paint);

            // Drawing
            whereToDraw.set((int)animXPosition,
                    100,
                    (int)animXPosition + frameWidth,
                    frameHeight);
            getCurrentFrame();
            canvas.drawBitmap(animBitmap,
                    frameToDraw,
                    whereToDraw, paint);
            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    // If Activity is paused/stopped
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    @Override
    // If Activity is started
    public void resume() {
        playing = true;
        gameThread = new Thread(String.valueOf(this));
        gameThread.start();
    }

    // Start gameView
    @Override
    protected void onResume() {
        super.onResume();
    }

    // Pause/Stop gameView
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void dispose() {
    }
}


