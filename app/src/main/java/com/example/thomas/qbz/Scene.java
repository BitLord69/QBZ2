package com.example.thomas.qbz;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Thomas on 2018-10-06.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
