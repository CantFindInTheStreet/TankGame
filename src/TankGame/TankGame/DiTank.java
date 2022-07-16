package TankGame.TankGame;

public class DiTank extends Tank implements Runnable {
    public DiTank(int x, int y, int fangxiang, int speed) {
        super(x, y, fangxiang, speed);
    }

    @Override
    public void run() {
        while (true) {
            switch (this.getFangxiang()) {
                case 8:
                    for(int i=0;i<30;i++){
                        this.up();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for(int i=0;i<30;i++){
                        this.down();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 4:
                    for(int i=0;i<30;i++){
                        this.left();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 6:
                    for(int i=0;i<30;i++){
                        this.right();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            switch ((int) (Math.random() * 4)) {
                case 0:
                    this.setFangxiang(8);
                    break;
                case 1:
                    this.setFangxiang(4);
                    break;
                case 2:
                    this.setFangxiang(2);
                    break;
                case 3:
                    this.setFangxiang(6);
                    break;
            }
            if (!this.isLive()) {
                break;
            }

        }

    }
}
