package com.example.thomas.qbz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.thomas.utils.qbz.PixelHelper;


/**
 * Created by Thomas on 2018-10-06.
 */

public class Cube extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {

    private int m_number;
    private CubeListener m_cListener;
    private boolean empty = false;

    public Cube(Context context){
        super(context);
    }

    public Cube(Context context, CubeListener listener, int nColor, int nRawHeight, int nNumber){
        super(context);

        m_number = nNumber;
        this.m_cListener = listener;
        this.setImageResource(R.drawable.rectangle);

        this.setColorFilter(nColor);

        int dpHeight = PixelHelper.pixelsToDp(nRawHeight, context);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(MainActivity.PADDING ,MainActivity.PADDING,MainActivity.PADDING,MainActivity.PADDING);
        params.width = params.height = nRawHeight;
//        params.width = params.height = dpHeight;

//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpHeight, dpHeight);

        setLayoutParams(params);

        setOnTouchListener(this);
    }

    public void setEmpty(){
        empty = true;
        this.setColorFilter(Color.DKGRAY);
    }
    public boolean isEmpty(){ return empty; }

    public int getNumber(){ return m_number;}

    public void setNumber(int nNew) { m_number = nNew; }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            m_cListener.cubePressed(this);
        }
        return true;
    }

    public interface CubeListener{
        void cubePressed(Cube cube);
    }
}
