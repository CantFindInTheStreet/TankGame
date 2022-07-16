package TankGame.TankGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//面板
class BeiJing extends JPanel implements KeyListener, Runnable{
    private static int beiJingwidth = 1000;
    private static int beiJingheight = 750;
    //设置 获取背景 宽高
    public static int getBeiJingwidth() {
        return beiJingwidth;
    }
    public static void setBeiJingwidth(int beiJingwidth) {
        BeiJing.beiJingwidth = beiJingwidth;
    }
    public static int getBeiJingheight() {
        return beiJingheight;
    }
    public static void setBeiJingheight(int beiJingheight) {
        BeiJing.beiJingheight = beiJingheight;
    }
    //提供 我方坦克 与 敌方坦克集的访问方法
    public MyTank getMyTank() {
        return myTank;
    }
    public Vector<DiTank> getDiTanks() {
        return diTanks;
    }
    //我方坦克的设置
    public MyTank myTank =null;
    private int wx=500; //500
    private int wy=300; //300
    private int wfangxiang=8;
    private int wspeed=5;
    public static int wzidan=5;   //我方坦克最多可发射的子弹数
    //敌方坦克的设置
    public Vector<DiTank> diTanks =new Vector<>();
    private int shuliang=4;//敌方数量!!!!!!!!!!!!!!!!!!!!!!!!!!
    private int dx=50;
    private int dy=0;//敌方坦克生成的y坐标
    private int dfangxiang=2;
    private int dspeed=5;
    public BeiJing(){}
    public BeiJing(int key) {
        if(key==0){
            //new 我方坦克
            myTank =new MyTank(wx,wy,wfangxiang,wspeed);
            //new 敌方坦克
            for (int i = 0; i < shuliang; i++) {
                DiTank diTank =new DiTank(dx+i*250,dy,dfangxiang,dspeed);
                new Thread(diTank).start();//实现敌方坦克随机移动
                //diTank.openFire();//一发子弹
                //创建敌方坦克的子弹--------多发-----------------
                DiFangFire diFangFire=new DiFangFire(diTank);
                new Thread(diFangFire).start();
                //---------------------------
                diTanks.add(diTank);
                //碰撞对象载入!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                diTank.setWoTank(myTank);
                diTank.setDirenTanks(diTanks);
            }
        }else{
            ShuJu.qvdata();
            myTank=ShuJu.getMyTank();
            diTanks=ShuJu.getDiTanks();
            for (int i = 0; i <diTanks.size(); i++) {
                DiTank diTank=diTanks.get(i);
                new Thread(diTank).start();//实现敌方坦克随机移动
                //diTank.openFire();//一发子弹
                //创建敌方坦克的子弹--------多发-----------------
                DiFangFire diFangFire=new DiFangFire(diTank);
                new Thread(diFangFire).start();

                //碰撞对象载入!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                diTank.setWoTank(myTank);
                diTank.setDirenTanks(diTanks);
            }
        }

        //碰撞对象载入我方
        myTank.setWoTank(myTank);
        myTank.setDirenTanks(diTanks);
        //把对象载入保存数据
        ShuJu.setMyTank(myTank);
        ShuJu.setDiTanks(diTanks);

        //把爆炸图片加载进来
        image1=Toolkit.getDefaultToolkit().getImage(BeiJing.class.getResource("/picture/bomb_1.gif"));
        image2=Toolkit.getDefaultToolkit().getImage(BeiJing.class.getResource("/picture/bomb_2.gif"));
        image3=Toolkit.getDefaultToolkit().getImage(BeiJing.class.getResource("/picture/bomb_3.gif"));
        new AePlayWave("src\\picture\\music.wav").start();
    }
    //用于存放爆炸效果
    public static Vector<Boom> booms=new Vector<>();
    //爆炸的图片
    Image image1=null;
    Image image2=null;
    Image image3=null;
    //设置一个变量  使游戏结束提示输出一次----------------------------
    private int cishu=1;
    private int dicishu=1;
    //写一个方法 来显示我方累计杀敌数
    public void xianShi(Graphics g){
        Font font=new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("累计击毁坦克数量",1020,30);
        drawtank(1020,60,2,8,g);
        g.setColor(Color.black);
        g.drawString(ShuJu.getShaDiShu()+"",1140,100);
    }
    //重写paint方法   画面
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        xianShi(g);//显示杀敌数
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, beiJingwidth, beiJingheight);//背景填充
        //画我方坦克 根据是否存活来画
        if(myTank.isLive()){
            //活着
            drawtank(myTank.getX(), myTank.getY(), 0, myTank.getFangxiang(),g);
            //drawtank(myTank.getX()+50, myTank.getY(), 1,8,g);
        }else{
            //我方坦克死亡游戏结束
            //-------------------------------------!!!!!!!!!!!!!!!!
            if(cishu==1){
                cishu--;
                /*Font font=new Font("宋体",Font.BOLD,25);
                g.setFont(font);
                g.setColor(Color.black);
                g.drawString("哈哈，你输了！",1020,300);*/
                System.out.println("oh 你凉西皮了！ GameOver!!!!!!!!!!");
                 /*try {
                     Thread.sleep(2000);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                   }
                    System.exit(0);//强制关闭窗口*/
            }
        }

        //当敌方坦克为零 游戏结束
        if(diTanks.size()==0){
            if(dicishu==1){
                dicishu--;
                /*Font font=new Font("宋体",Font.BOLD,25);
                g.setFont(font);
                g.setColor(Color.black);
                g.drawString("哇！你赢了！",1020,300);*/
                System.out.println("You Win!!!!!!!!!!!!!!!!!!!!!!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.exit(0);//强制关闭窗口

            }

        }
        //画敌方坦克   判断是否存活  活才画
        for (int i = 0; i < diTanks.size(); i++) {
            DiTank diTank = diTanks.get(i);
            if(diTank.isLive()){
                //敌方坦克还存在 则画坦克
                drawtank(diTank.getX(), diTank.getY(), 2, diTank.getFangxiang(),g);
            }else if(!diTank.isLive() && diTank.fires.size()==0){
                //敌方坦克被消灭 且 场上没有子弹 则移除该坦克
                ShuJu.addShaDiShu();//消灭坦克+1
                diTanks.remove(diTank);
            }
            for(int j=0;j<diTank.fires.size();j++){
                BiuBiuBiu biuBiuBiu= diTank.fires.get(j);
                //判断子弹是否存在  存在则画 不存在则移除
                if(biuBiuBiu.isLive()){
                    g.setColor(Color.red);
                    g.fillOval(biuBiuBiu.getX(),biuBiuBiu.getY(),2,2);//炮弹宽度
                }else{
                    diTank.fires.remove(biuBiuBiu);
                }
            }
        }

        //画我方子弹  后面还得修改!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //这里为一个子弹时的代码
        /*if(myTank.biuBiuBiu!=null&&myTank.biuBiuBiu.isLive()){
            g.setColor(Color.lightGray);
            g.fillOval(myTank.biuBiuBiu.getX(),myTank.biuBiuBiu.getY(),2,2);//炮弹宽度
        }*/
        //我方发射多发子弹
        for(int i=0;i<myTank.fires.size();i++){
            BiuBiuBiu biuBiuBiu=myTank.fires.get(i);
            if(biuBiuBiu.isLive()){
                g.setColor(Color.lightGray);
                g.fillOval(biuBiuBiu.getX(),biuBiuBiu.getY(),2,2);//炮弹宽度
            }else{
                myTank.fires.remove(biuBiuBiu);
            }
        }
        //画炸弹
        for(int i=0;i<booms.size();i++){
            Boom boom=booms.get(i);
            if(boom.life>8){
                g.drawImage(image1,boom.x,boom.y,60,60,this);
            }else if(boom.life>4){
                g.drawImage(image2,boom.x,boom.y,60,60,this);
            }else{
                g.drawImage(image3,boom.x,boom.y,60,60,this);
            }
            boom.lifeDown();
            if(!boom.live){
                booms.remove(boom);
            }
        }
    }
    //方法 判断是否击中坦克
    public static void hitTank(BiuBiuBiu biuBiuBiu, Tank tank){
        switch (tank.getFangxiang()){
            case 8: case 2:
                if(biuBiuBiu.getX()>tank.getX() && biuBiuBiu.getX()< tank.getX()+40
                        && biuBiuBiu.getY()> tank.getY() && biuBiuBiu.getY()<tank.getY()+60){
                    tank.setLive(false);
                    biuBiuBiu.setLive(false);
                    booms.add(new Boom(tank.getX(),tank.getY()));
                    /*if(tank instanceof DiTank){
                        ShuJu.addShaDiShu();
                    }*/
                    //Boom boom=new Boom(tank.getX(),tank.getY());
                }
                break;
            case 4: case 6:
                if(biuBiuBiu.getX()>tank.getX() && biuBiuBiu.getX()< tank.getX()+60
                        && biuBiuBiu.getY()> tank.getY() && biuBiuBiu.getY()<tank.getY()+40){
                    tank.setLive(false);
                    biuBiuBiu.setLive(false);
                    booms.add(new Boom(tank.getX(),tank.getY()));
                    /*if(tank instanceof DiTank){
                        ShuJu.addShaDiShu();
                    }*/
                }
                break;
        }
    }
    /**
     * 绘制坦克
     * @param x 坦克生成的左上角 x 坐标
     * @param y 左上角 y 坐标
     * @param type 坦克类型  敌我之分
     * @param fangxiang 坦克的方向 上8 左4 右6 下2
     * @param g 画笔
     */
    public static void drawtank(int x, int y, int type, int fangxiang,Graphics g){
        if(type==0){
            //己方
            g.setColor(Color.LIGHT_GRAY);
        }else{
            //其他
            g.setColor(Color.pink);
        }
        switch (fangxiang){
            case 8:
                tankup(x,y,g);
                break;
            case 2:
                tankdown(x,y,g);
                break;
            case 4:
                tankleft(x,y,g);
                break;
            case 6:
                tankright(x,y,g);
                break;
        }
    }
    //坦克姿态 朝向 上下左右
    private static void tankup(int x,int y,Graphics g){
        g.fill3DRect(x,y,10,60,true);
        g.fill3DRect(x+10,y+10,20,40,true);
        g.fill3DRect(x+30,y,10,60,true);
        g.fillOval(x+10,y+20,20,20);
        g.drawLine(x+20,y,x+20,y+30);
        g.setColor(Color.green);
        g.drawRect(x,y,10,60);
        g.drawRect(x+10,y+10,20,40);
        g.drawRect(x+30,y,10,60);
        g.drawOval(x+10,y+20,20,20);
        g.drawLine(x+20,y,x+20,y+30);
    }
    private static void tankdown(int x,int y,Graphics g){
        g.fill3DRect(x,y,10,60,true);
        g.fill3DRect(x+10,y+10,20,40,true);
        g.fill3DRect(x+30,y,10,60,true);
        g.fillOval(x+10,y+20,20,20);
        g.drawLine(x+20,y+60,x+20,y+30);
        g.setColor(Color.green);
        g.drawRect(x,y,10,60);
        g.drawRect(x+10,y+10,20,40);
        g.drawRect(x+30,y,10,60);
        g.drawOval(x+10,y+20,20,20);
        g.drawLine(x+20,y+60,x+20,y+30);
    }
    private static void tankright(int x,int y,Graphics g){
        g.fill3DRect(x,y,60,10,true);
        g.fill3DRect(x+10,y+10,40,20,true);
        g.fill3DRect(x,y+30,60,10,true);
        g.fillOval(x+20,y+10,20,20);
        g.drawLine(x+30,y+20,x+60,y+20);
        g.setColor(Color.green);
        g.drawRect(x,y,60,10);
        g.drawRect(x+10,y+10,40,20);
        g.drawRect(x,y+30,60,10);
        g.drawOval(x+20,y+10,20,20);
        g.drawLine(x+30,y+20,x+60,y+20);
    }
    private static void tankleft(int x,int y,Graphics g){
        g.fill3DRect(x,y,60,10,true);
        g.fill3DRect(x+10,y+10,40,20,true);
        g.fill3DRect(x,y+30,60,10,true);
        g.fillOval(x+20,y+10,20,20);
        g.drawLine(x+30,y+20,x,y+20);
        g.setColor(Color.green);
        g.drawRect(x,y,60,10);
        g.drawRect(x+10,y+10,40,20);
        g.drawRect(x,y+30,60,10);
        g.drawOval(x+20,y+10,20,20);
        g.drawLine(x+30,y+20,x,y+20);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        /*移动测试
        if(e.getKeyCode()==87){
            myTank.setFangxiang(8);
            myTank.setY(myTank.getY()-myTank.getSpeed());
        }else if(e.getKeyCode()==65){
            myTank.setFangxiang(4);
        }else if(e.getKeyCode()==68){
            myTank.setFangxiang(6);
        }else if(e.getKeyCode()==83){
            myTank.setFangxiang(2);
        }
        this.repaint();*/


        //System.out.println(e.getKeyCode());
        //w 87 a 65 d 68 s 83
        if(e.getKeyCode()==87){
            myTank.up();
        }else if(e.getKeyCode()==65){
            myTank.left();
        }else if(e.getKeyCode()==68){
            myTank.right();
        }else if(e.getKeyCode()==83){
            myTank.down();
        }
        if(e.getKeyCode()==74){
            //按下J键可以biubiubiu
            if(myTank.isLive()){
                myTank.openFire();
            }//我方坦克得活着  才能发射子弹
        }
        this.repaint();


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //set get方法
    public int getWx() {
        return wx;
    }
    public void setWx(int wx) {
        this.wx = wx;
    }
    public int getWy() {
        return wy;
    }
    public void setWy(int wy) {
        this.wy = wy;
    }
    public int getWfangxiang() {
        return wfangxiang;
    }
    public void setWfangxiang(int wfangxiang) {
        this.wfangxiang = wfangxiang;
    }
    public int getWspeed() {
        return wspeed;
    }
    public void setWspeed(int wspeed) {
        this.wspeed = wspeed;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
            //判断是否击中敌方坦克
            for(int i=0;i<myTank.fires.size();i++){
                BiuBiuBiu biuBiuBiu=myTank.fires.get(i);
                /*myTank.isLive() && 当我方坦克死亡时 游戏结束  而不会出现我方死亡前打出的子弹
                死后击中敌人最后一辆坦克而赢得游戏的场面发生*/
                if(myTank.isLive() && biuBiuBiu.isLive()){
                    for(int j=0;j<diTanks.size();j++){
                        DiTank diTank=diTanks.get(j);
                        hitTank(biuBiuBiu,diTank);
                    }
                }
            }
            //判断敌方坦克是否击中我方
            for(int i=0;i<diTanks.size();i++){
                DiTank diTank=diTanks.get(i);
                for(int j=0;j<diTank.fires.size();j++){
                    BiuBiuBiu biuBiuBiu=diTank.fires.get(j);
                    if(biuBiuBiu.isLive()){
                        hitTank(biuBiuBiu,myTank);
                    }
                }
            }
        }
    }
    //判断坦克的碰撞体积！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
    //判断时要  ！ifPengZhuang(.....)
    public static boolean ifPengZhuang(Tank tank){
        //没有碰撞则return false   碰撞return true
        //判断敌方坦克的碰撞
        if(tank instanceof DiTank){
            MyTank myTank=tank.getWoTank();
            Vector<DiTank> diTankss=new Vector<>();
            diTankss=tank.getDirenTanks();
            //以上获取 敌我方 坦克对象
            for(int i=0;i<diTankss.size();i++){
                DiTank diTank=diTankss.get(i);
                if(diTank!=tank){
                    //此时获取的坦克对象 为敌方坦克 且不为传入时的敌方坦克
                    //传入的坦克xy
                    int cix=tank.getX();
                    int ciy=tank.getY();
                    int cfx=tank.getFangxiang();
                    //我方的坦克xy
                    int wx=myTank.getX();
                    int wy=myTank.getY();
                    int wfx=myTank.getFangxiang();
                    //敌方的坦克xy
                    int dx=diTank.getX();
                    int dy=diTank.getY();
                    int dfx=diTank.getFangxiang();
                    //判断 此坦克是否与我方坦克发生碰撞
                    //我方坦克横着
                    if(wfx==4||wfx==6){
                        switch (cfx){
                            case 8:
                                if(cix+40<=wx || cix>=wx+60){
                                }else{
                                    if(ciy-tank.getSpeed()<wy+40){
                                        return true;
                                    }
                                }
                                break;
                            case 2:
                                if(cix+40<=wx || cix>=wx+60){
                                }else{
                                    if(ciy+60+tank.getSpeed()>wy){
                                        return true;
                                    }
                                }
                                break;
                            case 4:
                                if(ciy+40<=wy || ciy>=wy+40){
                                }else{
                                    if(cix-tank.getSpeed()<wx+60){
                                        return true;
                                    }
                                }
                                break;
                            case 6:
                                if(ciy+40<=wy || ciy>=wy+40){
                                }else{
                                    if(cix+60+tank.getSpeed()>wx){
                                        return true;
                                    }
                                }
                                break;
                        }
                    }
                    //我方坦克竖着
                    if(wfx==8||wfx==2){
                        switch (cfx){
                            case 8:
                                if(cix+40<=wx || cix>=wx+40){
                                }else{
                                    if(ciy-tank.getSpeed()<wy+60){
                                        return true;
                                    }
                                }
                                break;
                            case 2:
                                if(cix+40<=wx || cix>=wx+40){
                                }else{
                                    if(ciy+60+tank.getSpeed()>wy){
                                        return true;
                                    }
                                }
                                break;
                            case 4:
                                if(ciy+40<=wy || ciy>=wy+60){
                                }else{
                                    if(cix-tank.getSpeed()<wx+40){
                                        return true;
                                    }
                                }
                                break;
                            case 6:
                                if(ciy+40<=wy || ciy>=wy+60){
                                }else{
                                    if(cix+60+tank.getSpeed()>wx){
                                        return true;
                                    }
                                }
                                break;
                        }
                    }
                    //判断 此坦克是否与其他敌方坦克发生碰撞
                    //敌方坦克横着
                    if(dfx==4||dfx==6){
                        switch (cfx){
                            case 8:
                                if(cix+40<=dx || cix>=dx+60){
                                }else{
                                    if(ciy-tank.getSpeed()<dy+40){
                                        return true;
                                    }
                                }
                                break;
                            case 2:
                                if(cix+40<=dx || cix>=dx+60){
                                }else{
                                    if(ciy+60+tank.getSpeed()>dy){
                                        return true;
                                    }
                                }
                                break;
                            case 4:
                                if(ciy+40<=dy || ciy>=dy+40){
                                }else{
                                    if(cix-tank.getSpeed()<dx+60){
                                        return true;
                                    }
                                }
                                break;
                            case 6:
                                if(ciy+40<=dy || ciy>=dy+40){
                                }else{
                                    if(cix+60+tank.getSpeed()>dx){
                                        return true;
                                    }
                                }
                                break;
                        }
                    }
                    //敌方坦克竖着
                    if(dfx==8||dfx==2){
                        switch (cfx){
                            case 8:
                                if(cix+40<=dx || cix>=dx+40){
                                }else{
                                    if(ciy-tank.getSpeed()<dy+60){
                                        return true;
                                    }
                                }
                                break;
                            case 2:
                                if(cix+40<=dx || cix>=dx+40){
                                }else{
                                    if(ciy+60+tank.getSpeed()>dy){
                                        return true;
                                    }
                                }
                                break;
                            case 4:
                                if(ciy+40<=dy || ciy>=dy+60){
                                }else{
                                    if(cix-tank.getSpeed()<dx+40){
                                        return true;
                                    }
                                }
                                break;
                            case 6:
                                if(ciy+40<=dy || ciy>=dy+60){
                                }else{
                                    if(cix+60+tank.getSpeed()>dx){
                                        return true;
                                    }
                                }
                                break;
                        }
                    }
                //全部判定结束
                }
            }
        //------------敌方坦克的碰撞判定结束----------------------------
        }
        //判断我方坦克的碰撞
        if(tank instanceof MyTank){
            Vector<DiTank> diTankss=new Vector<>();
            diTankss=tank.getDirenTanks();
            for (int i = 0; i < diTankss.size(); i++) {
                DiTank diTank=diTankss.get(i);
                //传入的我方坦克xy
                int cix=tank.getX();
                int ciy=tank.getY();
                int cfx=tank.getFangxiang();
                //敌方的坦克xy
                int dx=diTank.getX();
                int dy=diTank.getY();
                int dfx=diTank.getFangxiang();
                //判断 此坦克是否与敌方坦克发生碰撞
                //敌方坦克横着
                if(dfx==4||dfx==6){
                    switch (cfx){
                        case 8:
                            if(cix+40<=dx || cix>=dx+60){
                            }else{
                                if(ciy-tank.getSpeed()<dy+40){
                                    return true;
                                }
                            }
                            break;
                        case 2:
                            if(cix+40<=dx || cix>=dx+60){
                            }else{
                                if(ciy+60+tank.getSpeed()>dy){
                                    return true;
                                }
                            }
                            break;
                        case 4:
                            if(ciy+40<=dy || ciy>=dy+40){
                            }else{
                                if(cix-tank.getSpeed()<dx+60){
                                    return true;
                                }
                            }
                            break;
                        case 6:
                            if(ciy+40<=dy || ciy>=dy+40){
                            }else{
                                if(cix+60+tank.getSpeed()>dx){
                                    return true;
                                }
                            }
                            break;
                    }
                }
                //敌方坦克竖着
                if(dfx==8||dfx==2){
                    switch (cfx){
                        case 8:
                            if(cix+40<=dx || cix>=dx+40){
                            }else{
                                if(ciy-tank.getSpeed()<dy+60){
                                    return true;
                                }
                            }
                            break;
                        case 2:
                            if(cix+40<=dx || cix>=dx+40){
                            }else{
                                if(ciy+60+tank.getSpeed()>dy){
                                    return true;
                                }
                            }
                            break;
                        case 4:
                            if(ciy+40<=dy || ciy>=dy+60){
                            }else{
                                if(cix-tank.getSpeed()<dx+40){
                                    return true;
                                }
                            }
                            break;
                        case 6:
                            if(ciy+40<=dy || ciy>=dy+60){
                            }else{
                                if(cix+60+tank.getSpeed()>dx){
                                    return true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        //-------------------------------------------------
        return false;//无碰撞
    }
    /* 不完整
    public static boolean ifPengZhaung(Tank tank){
        boolean pengzhuang=false;//  true时碰撞    false时不碰  可移动
        //这里把ditanks的集合  mytank 改成static   不行 改了不能序列化了
        if(tank==myTank){
            for(int i=0;i<diTanks.size();i++){
                DiTank diTank=diTanks.get(i);
                int mytankX=myTank.getX();
                int mytankY=myTank.getY();
                int mytankFangxiang=myTank.getFangxiang();
                if(diTank.isLive()){
                    int ditankX=diTank.getX();
                    int ditankY=diTank.getY();
                    int ditankFangxiang=diTank.getFangxiang();
                    //敌方坦克横
                    if(ditankFangxiang==4||ditankFangxiang==6){
                        switch (mytankFangxiang){
                            case 8:
                                if(mytankX+40<=ditankX||mytankX>=ditankX+60){
                                }else{
                                    if(mytankY-myTank.getSpeed()<ditankY+40){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                        return pengzhuang;
                                    }
                                }
                                break;
                            case 2:
                                if(mytankX+40<=ditankX||mytankX>=ditankX+60){
                                }else{
                                    if(mytankY+60+myTank.getSpeed()>ditankY){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                            case 4:
                                if(mytankY+40<=ditankY||mytankY>=ditankY+40){
                                }else{
                                    if(mytankX-myTank.getSpeed()<ditankX+60){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                            case 6:
                                if(mytankY+40<=ditankY||mytankY>=ditankY+40){
                                }else{
                                    if(mytankX+60+myTank.getSpeed()>ditankX){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                        }
                    }else{//敌坦克竖
                        switch (mytankFangxiang){
                            case 8:
                                if(mytankX+40<=ditankX||mytankX>=ditankX+40){
                                }else{
                                    if(mytankY-myTank.getSpeed()<ditankY+60){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                            case 2:
                                if(mytankX+40<=ditankX||mytankX>=ditankX+40){
                                }else{
                                    if(mytankY+60+myTank.getSpeed()>ditankY){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                            case 4:
                                if(mytankY+40<=ditankY||mytankY>=ditankY+60){
                                }else{
                                    if(mytankX-myTank.getSpeed()<ditankX+60){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                            case 6:
                                if(mytankY+40<=ditankY||mytankY>=ditankY+60){
                                }else{
                                    if(mytankX+60+myTank.getSpeed()>ditankX){
                                        //不可移动 碰撞
                                        pengzhuang=true;
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }//以上为我方坦克碰撞敌方坦克
//       else{
//
//        }
        return pengzhuang;
    }*/
    //---------------------------------------------------
}
