package TankGame.TankGame;

//子弹
public class BiuBiuBiu implements Runnable{
    //获取背景的宽高
    private static int width= BeiJing.getBeiJingwidth();
    private static int height= BeiJing.getBeiJingheight();
    private int x;//子弹横坐标
    private int y;//子弹纵坐标
    private int fangxiang; //子弹方向
    private int speed;//子弹速度
    private boolean live=true;//子弹是否存活
    public BiuBiuBiu(int x, int y, int fangxiang, int speed) {
        this.x = x;
        this.y = y;
        this.fangxiang = fangxiang;
        this.speed = speed;
    }
    //子弹多线程
    @Override
    public void run() {
        while(true){
            //判断子弹是否出界了 或者子弹已经是不存在的了
            if(this.x>width||this.x<0||this.y>height||this.y<0||!this.isLive()){
                setLive(false);
                break;
            }

            switch (fangxiang){
                case 8 :
                    y-=speed;
                    break;
                case 2 :
                    y+=speed;
                    break;
                case 6 :
                    x+=speed;
                    break;
                case 4 :
                    x-=speed;
                    break;
            }
            //-----------------显示子弹坐标------------------------
            //System.out.println(Thread.currentThread().getName()+"子弹的坐标"+x+" "+y);
            //让子弹线程休眠  不然移动太快
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //getset
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getFangxiang() { return fangxiang; }
    public void setFangxiang(int fangxiang) { this.fangxiang = fangxiang; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public boolean isLive() { return live; }
    public void setLive(boolean live) { this.live = live; }
}
