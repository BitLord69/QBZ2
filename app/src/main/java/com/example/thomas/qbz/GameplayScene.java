package com.example.thomas.qbz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Thomas on 2018-10-06.
 */

public class GameplayScene implements Scene{


    private Rect r = new Rect(); // for drawCenterText function

    private boolean gameOver;
    private long gameOverTime;
    private CubeManager manager;

    public GameplayScene(){

        manager = new CubeManager(5,(Constants.SCREEN_WIDTH/5)-40 ,100,Constants.SCREEN_HEIGHT - Constants.SCREEN_WIDTH );

        gameOver = false;

    }

    public void reset(){
        gameOver = false;
    }

    @Override
    public void update() {
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        manager.draw(canvas);
    }

    @Override
    public void terminate() {

    }



    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver)// && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    manager.checkHit((int)event.getX(),(int)event.getY());
                   // movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime > 2000) {
                    reset();
                    gameOver = false;
                   // orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver)// && movingPlayer)
                   // playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
               // movingPlayer  = false;
                break;
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
