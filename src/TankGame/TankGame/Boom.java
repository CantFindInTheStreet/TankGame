package TankGame.TankGame;
//炸弹
public class Boom {
    public int x;
    public int y;
    public int life=12;
    public boolean live=true;
    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        if(life>0){
            life--;
        }else{
            live=false;
        }
    }
}
