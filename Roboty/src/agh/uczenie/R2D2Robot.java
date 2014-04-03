package agh.uczenie;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class R2D2Robot extends AdvancedRobot {

    private int step = 90;
    private boolean angleLeft = true;



    @Override
    public void run() {
        while (true) {
            turnGunRight((angleLeft ? -1 : 1)*step);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        angleLeft = !angleLeft;
        step = Math.max(5, step/2);
        fire(1);
        System.out.println("scanned");
    }
}
