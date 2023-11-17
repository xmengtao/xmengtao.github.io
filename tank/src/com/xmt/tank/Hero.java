package com.xmt.tank;

import java.util.Vector;

/**
 * @author 徐孟韬
 * @version 1.0
 */
public class Hero extends Tank{
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }
    public void shotEnemyTank(){
        //控制一次子弹发射数量
        if (shots.size() == 5){
            return;
        }
        //根据自己坦克方向写出子弹射击时方向
        switch (getDirect()){
            case 0:
                shot = new Shot(getX()+20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2:
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        shots.add(shot);
        //发射子弹线程
        Thread thread = new Thread(shot);
        thread.start();
    }
}
