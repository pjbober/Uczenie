package agh.uczenie;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class R2D2Robot extends AdvancedRobot {

    private int step = 90;
    private boolean angleLeft = true;

    static abstract class Action {
        public abstract void exec();
    }

    @Override
    public void run() {
        while (true) {
            turnGunRight((angleLeft ? -1 : 1)*step);
        }
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        ahead(10);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        setTurnGunRight(getHeading() - getGunHeading() + event.getBearing());
        fire(1);
        System.out.println("scanned: bearing "+event.getBearing()+", velocity: "+event.getVelocity());
    }



}
