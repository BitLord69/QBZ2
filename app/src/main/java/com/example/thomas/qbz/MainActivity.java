package com.example.thomas.qbz;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;

        setContentView(new GamePanel(this));
    }
}
