package TankGame.TankGame;


//让敌方坦克可以不断发射子弹
public class DiFangFire implements Runnable{
    public DiTank diTank=null;
    public DiFangFire(DiTank diTank) {
        this.diTank = diTank;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);//5秒打一发
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //-------------------------
            if(diTank.isLive()){
                diTank.openFire();
            }
        }
    }
}
