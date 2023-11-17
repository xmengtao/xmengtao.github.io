package com.xmt.tank;

/**
 * @author 徐孟韬
 * @version 1.0
 * 发射子弹
 */
public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0;
    int speed = 4;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    Boolean isLive = true;
    @Override
    public void run() {//子弹位移
      while (isLive){
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          switch (direct){
              case 0:   //向上
                  y -= speed;
                  break;
              case 1:   //向右
                  x += speed;
                  break;
              case 2:   //向下
                  y += speed;
                  break;
              case 3:   //向左
                  x -= speed;
                  break;
          }
//          System.out.println("子弹 X=" + x +"  子弹Y="+y);
          //子弹出界就进行销毁
          if ( !(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)){
              isLive = false;
              break;
          }
      }
    }
}
