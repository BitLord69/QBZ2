package com.example.thomas.qbz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Thomas on 2018-10-06.
 */

public class Cube implements GameObject {
    private int color;
    private Rect rectangle;
//    private int xPos,yPos;
    private int size;
    private boolean empty = false;

    private int moveX,moveY;

    public Cube(int color, int startX, int startY, int size){
        this.color = color;
//        this.xPos = startX;
//        this.yPos = startY;
        this.size = size;
        moveX = startX;
        moveY = startY;

        rectangle = new Rect( startX, startY,startX+size,startY+size);


    }

    @Override
    public void draw(Canvas canvas) {
        if(!empty) {
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawRect(rectangle, paint);
        }
    }

    @Override
    public void update() {
        int xPos = rectangle.left;
        int yPos = rectangle.top;

        if(!empty) {
            if (xPos > moveX)
                xPos--;
            else if (xPos < moveX)
                xPos++;
            if (yPos > moveY)
                yPos--;
            else if (yPos < moveY)
                yPos++;

            rectangle.set(xPos,yPos,xPos+size,yPos-size);
        }

    }

    public void setEmpty(){
        empty = true;
    }
    public boolean isEmpty(){return empty;}

    public void setMove(int moveX,int moveY){
        this.moveX = moveX;
        this.moveY = moveY;
    }


    // getters/setters
    public int getColor(){return color;}
    public void setColor(int color){
        this.color = color;
    }

    public void setPos(int xPos, int yPos){
        rectangle.left = xPos;
        rectangle.top = yPos;
    }

    public Point getPos(){return new Point(rectangle.left,rectangle.top);    }

    public Rect getRectangle(){return rectangle;}
}
