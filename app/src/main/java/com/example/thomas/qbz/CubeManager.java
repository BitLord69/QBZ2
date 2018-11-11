package com.example.thomas.qbz;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Thomas on 2018-10-06.
 */

public class CubeManager implements Cube.CubeListener {
    private int m_nCubeSize;
    private int m_nCubeDimension; // size of side of cube
    private int m_nNumColors = 5;

    private GridLayout mContentView;

    private boolean m_bActivePlayer;

    private Context m_context;

    private ArrayList<Cube> cubes;
    private ArrayList<Integer> colorArray;
    private int m_nEmpty = 0;

    public CubeManager(GridLayout view, Context context, int nCubeDimension, int cubeSize, boolean bActivePlayer){
        this.m_nCubeDimension = nCubeDimension;
        this.m_nCubeSize = cubeSize;
        this.m_context = context;
        mContentView = view;
        m_bActivePlayer = bActivePlayer;

        m_nEmpty = new Random().nextInt(nCubeDimension * nCubeDimension);

Log.i("TAG", "I CubeManager.CubeManager - size: " + cubeSize + ", m_nEmpty: " + m_nEmpty);
        createArray();
    }

    private void createColorArray(){
        colorArray = new ArrayList<Integer>();
        for(int i = 0; i < m_nCubeDimension * m_nCubeDimension; i++){
            if((i% m_nNumColors) == 0)
                colorArray.add(Color.BLUE);
            else if((i% m_nNumColors) == 1)
                colorArray.add(Color.RED);
            else if((i% m_nNumColors) == 2)
                colorArray.add(Color.GREEN);
            else if((i% m_nNumColors) == 3)
                colorArray.add(Color.YELLOW);
            else if((i% m_nNumColors) == 4)
                colorArray.add(Color.MAGENTA);
        }
        Collections.shuffle(colorArray);
    }

    private void createArray(){
        cubes = new ArrayList<>();
        createColorArray();

        int nRowHeight, nColWidth;

        nRowHeight = nColWidth = m_nCubeSize * m_nCubeDimension;

        for(int i = 0; i < m_nCubeDimension; i++){
            for(int j = 0; j < m_nCubeDimension; j++) {
                Cube c = new Cube(m_context ,this, colorArray.get(j + m_nCubeDimension * i), m_nCubeSize, j + (i * m_nCubeDimension) + 1);
//                c.setX(m_nDrawPosX + (j * m_nCubeSize));
//                c.setY(m_nDrawPosY + (i * m_nCubeSize));
                cubes.add(c);
                mContentView.addView(c);
            }
        }
        cubes.get(m_nEmpty).setEmpty();
    }

    @Override
    public void cubePressed(Cube cube) {
        if (!m_bActivePlayer) return;

        // Beräkna rad och kolumn för den klickade kuben
        int rad = (int)((cube.getNumber() - 1) / m_nCubeDimension) + 1;
        int kol = cube.getNumber() - ((rad - 1) * m_nCubeDimension);

        // Beräkna rad och kolumn för den tomma kuben
        int tomrad = (int)((m_nEmpty) / m_nCubeDimension) + 1;
        int tomkol = (m_nEmpty + 1) - ((tomrad - 1) * m_nCubeDimension);

        Cube cEmpty = cubes.get(m_nEmpty);
        int nEmptyNum = cEmpty.getNumber();
        int nClickedNum = cube.getNumber();

        Log.i("TAG", "cubePressed före: -------------------------------------------------------------------- m_nEmpty:" + m_nEmpty);
        Log.i("TAG", "Tryckte visst på ruta " + cube.getNumber() + ", rad: " + rad + ", kolumn: " + kol + ", tom rad: " + tomrad + ", tom kol: " + tomkol);
        Log.i("TAG", "Innan animering!!! Pos Y: " + cube.getY() + ", tom y: " + cEmpty.getY() + ", kub nummer: " + cube.getNumber() + ", tomkub nummer: " + nEmptyNum);

        // Kolla om den tomma och klickade kuben är bredvid eller över och under varandra så att det går att byta
        if ((Math.abs(rad - tomrad) == 0 && Math.abs(kol - tomkol) == 1) ||
                (Math.abs(rad - tomrad) == 1 && Math.abs(kol - tomkol) == 0)) {

            AnimatorSet set = new AnimatorSet();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(cube, "y", cube.getY(), cEmpty.getY());
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(cEmpty, "y", cEmpty.getY(), cube.getY());
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(cube, "x", cube.getX(), cEmpty.getX());
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(cEmpty, "x", cEmpty.getX(), cube.getX());

//            set.setInterpolator(new AnticipateOvershootInterpolator());
            set.setInterpolator(new AnticipateInterpolator());
            set.playTogether(animator1, animator2, animator3, animator4);
            set.setDuration(500);
            set.start();

            // Byt plats/nummer på den tomma och klickade kuben
            cubes.set(m_nEmpty, cube);
            cubes.set(cube.getNumber() - 1, cEmpty);
            m_nEmpty = cube.getNumber() - 1;
            cubes.get(m_nEmpty).setNumber(nClickedNum);
            cube.setNumber(nEmptyNum);
        }

//        Log.i("TAG","Diff x: " + Math.abs(rad - tomrad) + ", diff y: " +  Math.abs(kol - tomkol) + ", storlek: " + cube.getMeasuredWidth());
        Log.i("TAG", "Efter animering!!! Pos Y: " + cube.getY() + ", tom y: " + cubes.get(m_nEmpty).getY() + ", kub nummer: " + (cube.getNumber()) + ", tomkub nummer: " + cubes.get(m_nEmpty).getNumber());
        Log.i("TAG", "cubePressed efter: -------------------------------------------------------------------- m_nEmpty:" + m_nEmpty);
    }

    public int getCubeSize(){
        return m_nCubeSize;
    }
}
