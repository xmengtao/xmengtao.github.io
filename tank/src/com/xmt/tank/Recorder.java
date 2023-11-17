package com.xmt.tank;

import java.io.*;
import java.util.Vector;

/**
 * @author 徐孟韬
 * @version 1.0
 * 该类用于记录相关信息,与文件交互
 */
public class Recorder {
    //定义变量,记录我方击毁敌人坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO对象
    private static BufferedWriter bw = null;
    public static BufferedReader br = null;

    public static String getRecordFile() {
        return recordFile;
    }

    private static String recordFile = "src\\myRecorde.txt";
    //定义Vector集合,将Mypanel中坦克数组传过来
    private static Vector<EnemyTank> enemyTanks = null;
    //定义Vector,将坦克信息放到nodes中
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    //将坦克信息记录在Node中
    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
            return nodes;
    }

    //击毁敌方坦克时,allEnemyTankNum+1
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
    //1.记录玩家击毁坦克数量
    //2.记录程序关闭时未死忘坦克坐标
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(Recorder.allEnemyTankNum + "\r\n");
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive){
                    bw.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect());
                    bw.newLine();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
}
