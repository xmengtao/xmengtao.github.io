package com.xmt.tank;

/**
 * @author 徐孟韬
 * @version 1.0
 * 爆炸
 */
public class Bomb {
    int x,y;
    int life = 9;
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){//出现动画效果
        if (life > 0){
            life --;
        }else {
            isLive = false;
        }
    }
}
