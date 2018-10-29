package com.example.thomas.qbz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Thomas on 2018-10-06.
 */

public class CubeManager implements Cube.CubeListener {
    private int m_nCubeSize;
    private int m_nCubeDimension; // size of side of cube
    private int m_nNumColors = 5;

    private GridLayout mContentView;

    private Context m_context;

    private ArrayList<Cube> cubes;
    private ArrayList<Integer> colorArray;
    private int m_nEmpty = 0;

    public CubeManager(GridLayout view, Context context, int nCubeDimension, int cubeSize){
        this.m_nCubeDimension = nCubeDimension;
        this.m_nCubeSize = cubeSize;
        this.m_context = context;
        mContentView = view;

Log.i("TAG", "I CubeManager.CubeManager - size: " + cubeSize);
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
        // Beräkna rad och kolumn för den klickade kuben
        int rad = (int)((cube.getNumber() - 1) / m_nCubeDimension) + 1;
        int kol = cube.getNumber() - ((rad - 1) * m_nCubeDimension);

        // Beräkna rad och kolumn för den tomma kuben
        int tomrad = (int)((m_nEmpty) / m_nCubeDimension) + 1;
        int tomkol = (m_nEmpty + 1) - ((tomrad - 1) * m_nCubeDimension);

        // Kolla om den tomma och klickade kuben är bredvid eller över och under varandra så att det går att byta
        if ((Math.abs(rad - tomrad) == 0 && Math.abs(kol - tomkol) == 1) ||
                (Math.abs(rad - tomrad) == 1 && Math.abs(kol - tomkol) == 0)) {

            // Byt färg på den tomma och den klickade rutorna
            cubes.get(m_nEmpty).setColorFilter(cubes.get(cube.getNumber() - 1).getColorFilter());
            m_nEmpty = cube.getNumber() - 1;
            cubes.get(m_nEmpty).setEmpty();
        }

        Log.i("TAG", "Tryckte visst på ruta " + cube.getNumber() + ", rad: " + rad + ", kolumn: " + kol + ", tom rad: " + tomrad + ", tom kol: " + tomkol);
        Log.i("TAG","Diff x: " + Math.abs(rad - tomrad) + ", diff y: " +  Math.abs(kol - tomkol) + ", storlek: " + cube.getMeasuredWidth());
    }

    public int getCubeSize(){
//        return cubes.get(0).getWidth();
        return m_nCubeSize;
    }
}
