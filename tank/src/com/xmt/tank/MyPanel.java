package com.xmt.tank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author 徐孟韬
 * @version 1.0
 * 坦克大战绘图区域
 */
@SuppressWarnings("all")
public class MyPanel extends JPanel implements KeyListener,Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();//定义敌人坦克
    Vector<Bomb> bombs = new Vector<>();//定义爆炸/炸弹
    Vector<Node> nodes = new Vector<>();//用于恢复坦克坐标的集合
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    int enemyTankSize = 6;
    public MyPanel(String key){//选择新开游戏还是继续上局游戏
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("没有游戏记录,只能重新开启游戏");
            key = "2";
        }
        //将坦克数组设置给Record
        Recorder.setEnemyTanks(enemyTanks);
        //初始化我方坦克
        hero = new Hero(800,600);
        switch (key){
            case "1"://继续上局游戏
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    enemyTank.setDirect(node.getDirect());
                    //将enenmyTanks设置给enemyTank!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动敌人随机移动线程
                    new Thread(enemyTank).start();
                    //给坦克上子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    //启动敌方子弹发射线程
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
            break;
            case "2"://新开游戏
                Recorder.setAllEnemyTankNum(0);
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100*(i+1),0);
                    enemyTank.setDirect(2);
                    //将enenmyTanks设置给enemyTank!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动敌人随机移动线程
                    new Thread(enemyTank).start();
                    //给坦克上子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    //启动敌方子弹发射线程
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误");
        }
        //初始化敌人坦克
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        //开启音乐
        new AePlayWave("src\\111.wav").start();
    }
    //界面显示玩家击毁坦克数量
    public void showInfo(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        g.fillRect(0,0,1000,750);
        //画出敌人坦克
        for (int i = 0; i < enemyTanks.size() ; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断是否存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //画出子弹
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }

                }
            }

        }
        //画出自己坦克
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //画出子弹,从集合中遍历取出
        for(int j = 0; j < hero.shots.size(); j++){
            Shot shot = hero.shots.get(j);
            if (shot != null && shot.isLive){
            g.draw3DRect(shot.x,shot.y,2,2,false);
        }else {
                hero.shots.remove(shot);
            }
    }

        //画出爆炸
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if (bomb.life > 3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }else {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            bomb.lifeDown();
            if (bomb.life == 0){
                bombs.remove(bomb);
            }
        }
    }
    //判断集合中的每一个子弹是否击中敌方坦克
    public void hitEnemyTank(){
        for(int j = 0;j < hero.shots.size(); j++){
            Shot shot = hero.shots.get(j);
            if (shot != null && shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTanks(shot, enemyTank);
                }
            }
        }
    }
    //判断敌方坦克是否击中我方
    public void hitHero(){
        //遍历敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历敌方坦克的每发子弹
            for (int j = 0;j<enemyTank.shots.size();j++){
                Shot s = enemyTank.shots.get(j);
                //与我方坦克判断
                if (hero.isLive) {
                    hitTanks(s,hero);
                }
            }
        }
    }
    //判断子弹是否击中坦克
    public void hitTanks(Shot s,Tank tank){
        switch (tank.getDirect()){
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x < tank.getX()+40 &&
                s.y > tank.getY() && s.y < tank.getY() + 60){
                    tank.isLive = false;
                    s.isLive = false;
                    enemyTanks.remove(tank);
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //击中产生爆炸
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60 &&
                        s.y > tank.getY() && s.y < tank.getY() + 40){
                    tank.isLive = false;
                    s.isLive = false;
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //坦克被击中后,从数组里移除
                    enemyTanks.remove(tank);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }


    /**
     *画出坦克模型
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direct 坦克方向
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        switch (type){
            case 0: //Hero
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        switch (direct){
            case 0: //向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
                break;
            case 1: //向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边轮子
                g.fill3DRect(x , y+30, 60, 10, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y+20);//画出炮筒
                break;
            case 2: //向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y+60);//画出炮筒
                break;
            case 3: //向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边轮子
                g.fill3DRect(x , y+30, 60, 10, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x , y+20);//画出炮筒
                break;

            default:
                System.out.println("暂未做处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //按下WASD让坦克移动
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            if (hero.getY() > 0){
                hero.moveUp();
            }
        }else if (e.getKeyCode()==KeyEvent.VK_D){
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        }else if (e.getKeyCode()==KeyEvent.VK_S){
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        }else if (e.getKeyCode()==KeyEvent.VK_A){
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        //发射子弹
        if (e.getKeyCode()==KeyEvent.VK_J){
                hero.shotEnemyTank();
        }
        //让面板重绘
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100); //刷新地图,让子弹移动
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hitHero();
            hitEnemyTank();
            this.repaint();
        }
    }
}
