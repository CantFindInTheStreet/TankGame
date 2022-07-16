package TankGame.TankGame;

import java.io.*;
import java.util.Vector;

public class ShuJu {
    private static int shaDiShu=0;
    private static String path="src\\picture\\shuju.txt";

    private static Vector<DiTank> diTanks=new Vector<>();
    private static MyTank mytank=null;

    public static Vector<DiTank> getDiTanks() { return diTanks; }
    public static void setDiTanks(Vector<DiTank> diTanks) { ShuJu.diTanks = diTanks; }
    public static MyTank getMyTank() { return mytank; }
    public static void setMyTank(MyTank tank) { ShuJu.mytank = tank; }

    public static String getPath() { return path; }

    //读数据
    private static BufferedReader in=null;
    //写数据
    private static BufferedWriter out=null;

    public static int getShaDiShu() { return shaDiShu; }
    public static void setShaDiShu(int shaDiShu) { ShuJu.shaDiShu = shaDiShu; }
    public static void addShaDiShu(){
        setShaDiShu(getShaDiShu()+1);
    }
    public static void qvdata(){
        try {
            in=new BufferedReader(new FileReader(path));
            setShaDiShu(Integer.parseInt(in.readLine()));
            String[] wotank=in.readLine().split(" ");
            mytank=new MyTank(Integer.parseInt(wotank[0]),Integer.parseInt(wotank[1]),Integer.parseInt(wotank[2]),Integer.parseInt(wotank[3]));
            String s=null;
            while((s=in.readLine())!=null){
                String[] strings=s.split(" ");
                diTanks.add(new DiTank(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]),Integer.parseInt(strings[2]),Integer.parseInt(strings[3])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void savedata(){
        try {
            out=new BufferedWriter(new FileWriter(path));
            out.write(getShaDiShu()+"\r\n");// \r\n为下一行  也可以 out.newLine()
            out.write(getMyTank().getX()+" "+getMyTank().getY()+" "+getMyTank().getFangxiang()+" "+getMyTank().getSpeed());
            //以上写入我方坦克的x,y,fangxiang 用 一个空格 隔开
            out.newLine();
            for(int i=0;i<diTanks.size();i++){
                DiTank diTank=diTanks.get(i);
                if(diTank.isLive()){
                    out.write(diTank.getX()+" "+diTank.getY()+" "+diTank.getFangxiang()+" "+diTank.getSpeed());
                    //以上写入我方坦克的x,y,fangxiang 用 一个空格 隔开
                    out.newLine();
                }
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void cleanShaDiShu(){//清空杀敌数
        setShaDiShu(0);
    }
}
