package com.example.thomas.qbz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Thomas on 2018-10-06.
 */

public class CubeManager {
    private ArrayList<Cube> cubes;
    private int cubeSize;
    private int n_x_nCube; // size of side of cube
    private int drawPosX,drawPosY;
    private ArrayList<Integer> colorArray;
    private int nbrOfColors = 5;

    public CubeManager(int n_x_nCube, int cubeSize, int drawPosX, int drawPosY){
        this.n_x_nCube = n_x_nCube;
        this.cubeSize = cubeSize;
        this.drawPosX = drawPosX;
        this.drawPosY = drawPosY;

        createArray();

    }

    private void createColorArray(){
        colorArray = new ArrayList<Integer>();
        for(int i = 0; i<n_x_nCube*n_x_nCube; i++){
            if((i%nbrOfColors) == 0)
                colorArray.add(Color.BLUE);
            else if((i%nbrOfColors) == 1)
                colorArray.add(Color.RED);
            else if((i%nbrOfColors) == 2)
                colorArray.add(Color.GREEN);
            else if((i%nbrOfColors) == 3)
                colorArray.add(Color.YELLOW);
            else if((i%nbrOfColors) == 4)
                colorArray.add(Color.MAGENTA);
        }
        Collections.shuffle(colorArray);
    }

    private void createArray(){
        cubes = new ArrayList<>();
        createColorArray();

        for(int i=0;i<n_x_nCube;i++){
            for(int j=0;j<n_x_nCube;j++) {
                Cube c = new Cube(colorArray.get(j + n_x_nCube*i), drawPosX + (j*cubeSize), drawPosY + (i*cubeSize), cubeSize);
                cubes.add(c);
            }
        }
        cubes.get(0).setEmpty();
    }

    public void draw(Canvas canvas){
        for(Cube cu : cubes)
            cu.draw(canvas);
    }

    public void update(){
        for(Cube cu : cubes)
            cu.update();
    }

    public void checkHit(int xPos, int yPos){
        int i = 0;
        boolean canMove = false;
        for(Cube cu : cubes) {
            boolean test = false;

            int testX = cu.getPos().x;
            int testY  = cu.getPos().y;
            int testWidth = cu.getRectangle().width();
            if(xPos > testX && xPos < testX + testWidth && yPos > testY && yPos < testY + testWidth)
                test = true;
            //test = cu.getRectangle().contains(testX+5,testY+5);
            //cu.getRectangle().contains(xPos, yPos)



            if(test){

                if(cubes.get(i - 1)!= null){
                    if(cubes.get(i - 1).isEmpty()) {
                        cu.setMove(cubes.get(i - 1).getPos().x, cubes.get(i - 1).getPos().y);
                        cubes.get(i - 1).setPos(cu.getPos().x,cu.getPos().y);
                    }
                }else if(cubes.get(i + 1)!= null) {
                    if (cubes.get(i + 1).isEmpty()) {
                        cu.setMove(cubes.get(i + 1).getPos().x, cubes.get(i + 1).getPos().y);
                        cubes.get(i + 1).setPos(cu.getPos().x, cu.getPos().y);
                    }
                }else if(cubes.get(i - n_x_nCube)!= null){
                    if(cubes.get(i-n_x_nCube).isEmpty()) {
                        cu.setMove(cubes.get(i - n_x_nCube).getPos().x, cubes.get(i - n_x_nCube).getPos().y);
                        cubes.get(i - n_x_nCube).setPos(cu.getPos().x, cu.getPos().y);
                    }
                }else if(cubes.get(i + n_x_nCube)!= null) {
                    if (cubes.get(i + n_x_nCube).isEmpty()){
                        cu.setMove(cubes.get(i + n_x_nCube).getPos().x, cubes.get(i + n_x_nCube).getPos().y);
                        cubes.get(i + n_x_nCube).setPos(cu.getPos().x, cu.getPos().y);
                    }
                }



            }
            i++;

        }
    }


}
