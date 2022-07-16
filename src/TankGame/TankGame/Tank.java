package TankGame.TankGame;

import java.util.Vector;

public class Tank {
    public Vector<BiuBiuBiu> fires=new Vector<>();//坦克的弹药库
    //BiuBiuBiu biuBiuBiu=null;//设置一个弹药

    //我的坦克
    private MyTank woTank=null;
    //敌方坦克集
    private Vector<DiTank> direnTanks=new Vector<>();
    //可传入
    public void setWoTank(MyTank woTank) { this.woTank = woTank; }
    public void setDirenTanks(Vector<DiTank> direnTanks) { this.direnTanks = direnTanks; }
    public MyTank getWoTank() { return woTank; }
    public Vector<DiTank> getDirenTanks() { return direnTanks; }
    //以上这里为了碰撞做准备


    //获取背景的宽高
    private static int width= BeiJing.getBeiJingwidth();
    private static int height= BeiJing.getBeiJingheight();
    //以下为坦克的 变量
    private int x;
    private int y;
    private int fangxiang;
    private int speed;
    private boolean live=true;
    public boolean isLive() { return live; }
    public void setLive(boolean live) { this.live = live; }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getFangxiang() {
        return fangxiang;
    }
    public void setFangxiang(int fangxiang) {
        this.fangxiang = fangxiang;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    //-----------------------------
    public Tank(int x, int y, int fangxiang, int speed) {
        this.x = x;
        this.y = y;
        this.fangxiang=fangxiang;
        this.speed=speed;
    }
    //意大利炮  开炮
    public void openFire(){
        //限制我方坦克发射子弹的数量  避免无脑连射
        if(this instanceof MyTank){
            if(this.fires.size()==BeiJing.wzidan){
                return;
            }
        }
        BiuBiuBiu biuBiuBiu=null;
        switch (this.getFangxiang()){
            case 8:
                biuBiuBiu=new BiuBiuBiu(this.getX()+20, this.getY(), fangxiang,10);
                break;
            case 2:
                biuBiuBiu=new BiuBiuBiu(this.getX()+20, this.getY()+60, fangxiang,10);
                break;
            case 4:
                biuBiuBiu=new BiuBiuBiu(this.getX(), this.getY()+20, fangxiang,10);
                break;
            case 6:
                biuBiuBiu=new BiuBiuBiu(this.getX()+60, this.getY()+20, fangxiang,10);
                break;
        }
        fires.add(biuBiuBiu);
        for (int i = 0; i < this.fires.size(); i++) {
            Thread thread=new Thread(fires.get(i));
            thread.start();
        }
    }
    //坦克移动
    public void up(){
        this.setFangxiang(8);
        if(this.getY()-this.getSpeed()>=0 && !BeiJing.ifPengZhuang(this)){

            this.setY(this.getY()-this.getSpeed());

            /*if(!BeiJing.ifPengZhuang(this)){
                this.setY(this.getY()-this.getSpeed());
            }*/
        }
    }
    public void down(){
        this.setFangxiang(2);
        if(this.getY()+this.getSpeed()<=height-60 && !BeiJing.ifPengZhuang(this)){

            this.setY(this.getY()+this.getSpeed());

            /*if(!BeiJing.ifPengZhuang(this)){
                this.setY(this.getY()+this.getSpeed());
            }*/
        }
    }
    public void left(){
        this.setFangxiang(4);
        if(this.getX()-this.getSpeed()>=0 && !BeiJing.ifPengZhuang(this)){

            this.setX(this.getX()-this.getSpeed());

            /*if(!BeiJing.ifPengZhuang(this)){
                this.setX(this.getX()-this.getSpeed());
            }*/
        }
    }
    public void right(){
        this.setFangxiang(6);
        if(this.getX()+this.getSpeed()<=width-60 && !BeiJing.ifPengZhuang(this)){

            this.setX(this.getX()+this.getSpeed());

            /*if(!BeiJing.ifPengZhuang(this)){
                this.setX(this.getX()+this.getSpeed());
            }*/
        }
    }

}
