package com.example.thomas.qbz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


public class MainActivity extends AppCompatActivity {
//    private ViewGroup mContentView;
    private GridLayout mContentView;
    private int mScreenHeight;
    private int mScreenWidth;

    private CubeManager mCubeManager;

    private final static int CUBE_DIMENSION = 3;
    public final static int PADDING = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

Log.i("TAG", "I konstruktor, efter super.onCreate...");

//      Load the activity layout, which is an empty canvas
        setContentView(R.layout.activity_main);
Log.i("TAG", "I konstruktor, efter setContentView...");

//      Get background reference.
        mContentView = findViewById(R.id.content_view);
        mContentView.removeAllViews();
//        mContentView = (ViewGroup) findViewById(R.id.content_view);
        if (mContentView == null) throw new AssertionError();
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setToFullScreen();
                }
                return false;
            }
        });

        setToFullScreen();

        final Context context = this;

//      After the layout is complete, get screen dimensions from the layout.
        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                    mContentView.setRowCount(CUBE_DIMENSION);
                    mContentView.setColumnCount(CUBE_DIMENSION);

Log.i("TAG", "ScreenWidth: " + mScreenWidth + " screen height: " + mScreenHeight);
//                    mCubeManager = new CubeManager(mContentView, context,5,120 ,150,300);
//                    mCubeManager = new CubeManager(mContentView, context,5,(mScreenWidth / 5) - 40 ,50,mScreenHeight - 40);
                    mCubeManager = new CubeManager(mContentView, context,CUBE_DIMENSION,(mScreenWidth / CUBE_DIMENSION) - (CUBE_DIMENSION  - 1) * PADDING);
                 }
            });
        }
    }

    private void setToFullScreen() {
        //      Set full screen mode
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
