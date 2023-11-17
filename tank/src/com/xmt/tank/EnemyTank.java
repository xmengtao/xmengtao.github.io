package com.xmt.tank;

import java.util.Vector;

/**
 * @author 徐孟韬
 * @version 1.0
 */
@SuppressWarnings("all")
public class EnemyTank extends Tank implements Runnable{
    Boolean isLive = true;
    Vector<Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //可以将Mypanel里的坦克设置过来
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }
    //判断坦克是否发生碰撞
    public Boolean isTouchEnemyTank(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            switch (this.getDirect()){
                case 0://向上
                    //当前坦克不能跟自己作比较
                    if (enemyTank != this){
                        //如果敌人坦克上下移动
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+40]
                            //             [enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左上角坐标:this.getX(),this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右上角坐标:this.getX()+40,this.getY()
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克左右移动
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+60]
                            //             [enemyTank.getY(),enemyTank.getY()+40]
                            //当前坦克左上角坐标:this.getX(),this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右上角坐标:this.getX()+40,this.getY()
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                    break;
                case 1://右
                    if (enemyTank != this){
                        //如果敌人坦克上下移动
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+40]
                            //             [enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克右上角坐标:this.getX()+60,this.getY()
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标:this.getX()+60,this.getY()+40
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克左右移动
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+60]
                            //             [enemyTank.getY(),enemyTank.getY()+40]
                            //当前坦克右上角坐标:this.getX() + 60,this.getY()
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标:this.getX()+60,this.getY()+40
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() +40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                    break;
                case 2://下
                    if (enemyTank != this){
                        //如果敌人坦克上下移动
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+40]
                            //             [enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左下角坐标:this.getX(),this.getY()+60
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX()<= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标:this.getX()+40,this.getY()+60
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克左右移动
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+60]
                            //             [enemyTank.getY(),enemyTank.getY()+40]
                            //当前坦克左下角坐标:this.getX(),this.getY()+60
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标:this.getX()+40,this.getY()+60
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() +60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                    break;
                case 3://左
                    if (enemyTank != this){
                        //如果敌人坦克上下移动
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+40]
                            //             [enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左上角坐标:this.getX(),this.getY()
                            if (this.getX()>= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克左下角坐标:this.getX(),this.getY()+40
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克左右移动
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //敌人坦克坐标范围[enemyTank.getX(),enemyTank.getX()+60]
                            //             [enemyTank.getY(),enemyTank.getY()+40]
                            //当前坦克左上角坐标:this.getX(),this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克左下角坐标:this.getX(),this.getY()+40
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    public void run() {

        while (true) {
            //控制敌方坦克发射子弹数量
            if (isLive && shots.size() < 2){
                Shot s = null;
                switch (getDirect()){
                    case 0://向上
                        s = new Shot(getX()+20,getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX()+60,getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX(),getY()+20,3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }
            //让坦克随机移动
            switch (getDirect()) {
                case 0:
                        for (int i = 0; i < 30; i++) {
                            if (getY() > 0 && !isTouchEnemyTank()) {
                                moveUp();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                        for (int i = 0; i < 30; i++) {
                            if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                                moveDown();
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setDirect((int) (Math.random() * 4));
            if (!isLive){
                break;
            }
        }
    }
}