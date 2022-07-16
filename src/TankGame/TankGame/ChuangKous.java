package TankGame.TankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;

//主窗口
public class ChuangKous extends JFrame {
    private BeiJing beiJing =null;
    public static void main(String[] args) {
        ChuangKous chuangKou =new ChuangKous();
    }
    public ChuangKous(){
        Scanner s=new Scanner(System.in);
        System.out.println("新游戏: 请输入0  继续上次游戏: 请输入其他");
        int key=s.nextInt();
        File file=new File(ShuJu.getPath());
        if(!file.exists()){
            key=0;
        }
        beiJing =new BeiJing(key);
        Thread thread=new Thread(beiJing);//让背景一直重画  才能实现子弹的移动显示
        thread.start();
        this.add(beiJing);//画板加入窗口
        this.addKeyListener(beiJing);//键盘监听
        this.setSize(1300,797);//窗口大小   这个窗口大小刚好和背景重合  1018 797
        this.setVisible(true);//窗口显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口程序结束
        this.addWindowListener(//关闭窗口时保存数据
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        ShuJu.savedata();
                        System.exit(0);
                    }
                }
        );
    }
}
