package com.xmt.tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 徐孟韬
 * @version 1.0
 */
public class XmtTank01 extends JFrame {
     MyPanel mp =null;

     Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        XmtTank01 xmtTank01 = new XmtTank01();
    }
    public  XmtTank01(){
        System.out.println("1:继续上局游戏,2:新开游戏");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300,900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        //程序关闭时,记录玩家成绩到文件中
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
            }
        });
    }

}
