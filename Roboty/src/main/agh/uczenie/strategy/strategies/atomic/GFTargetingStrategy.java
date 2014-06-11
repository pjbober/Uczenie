package agh.uczenie.strategy.strategies.atomic;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class GFTargetingStrategy extends BaseStrategy {
	private static final double BULLET_POWER = 1.9;

	private static double lateralDirection;
	private static double lastEnemyVelocity;
	Random random = new Random();
//	private static GFTMovement movement;

	public GFTargetingStrategy(AdvancedRobot robot) {
		super(robot);
//		movement = new GFTMovement(robot);
	}

	@Override
	public Color bodyColor() {
		return Color.BLUE;
	}

	@Override
	public Color gunColor() {
		return Color.BLACK;
	}

	@Override
	public Color radarColor() {
		return Color.YELLOW;
	}

	@Override
	public void setup() {
		lateralDirection = 1;
		lastEnemyVelocity = 0;
		robot.setAdjustRadarForGunTurn(true);
		robot.setAdjustGunForRobotTurn(true);
	}

	@Override
	public void loopAction() {
		robot.turnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double enemyAbsoluteBearing = robot.getHeadingRadians() + e.getBearingRadians();
		double enemyDistance = e.getDistance();
		double enemyVelocity = e.getVelocity();
		if (enemyVelocity != 0) {
			lateralDirection = GFTUtils.sign(enemyVelocity * Math.sin(e.getHeadingRadians() - enemyAbsoluteBearing));
		}
		GFTWave wave = new GFTWave(robot);
		wave.gunLocation = new Point2D.Double(robot.getX(), robot.getY());
		GFTWave.targetLocation = GFTUtils.project(wave.gunLocation, enemyAbsoluteBearing, enemyDistance);
		wave.lateralDirection = lateralDirection;
		wave.bulletPower = BULLET_POWER;
		wave.setSegmentations(enemyDistance, enemyVelocity, lastEnemyVelocity);
		lastEnemyVelocity = enemyVelocity;
		wave.bearing = enemyAbsoluteBearing;
		robot.setTurnGunRightRadians(Utils.normalRelativeAngle(enemyAbsoluteBearing - robot.getGunHeadingRadians() + wave.mostVisitedBearingOffset()));
		robot.setFire(wave.bulletPower);
		if (robot.getEnergy() >= BULLET_POWER) {
			robot.addCustomEvent(wave);
		}
//		movement.onScannedRobot(e);
		robot.setTurnRadarRightRadians(Utils.normalRelativeAngle(enemyAbsoluteBearing - robot.getRadarHeadingRadians()) * 2);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		super.onHitByBullet(hitByBulletEvent);
		robot.setAhead((random.nextBoolean() ? 1 : -1) * 40);
	}
}

class GFTWave extends Condition {
	static Point2D targetLocation;

	double bulletPower;
	Point2D gunLocation;
	double bearing;
	double lateralDirection;

	private static final double MAX_DISTANCE = 1000;
	private static final int DISTANCE_INDEXES = 5;
	private static final int VELOCITY_INDEXES = 5;
	private static final int BINS = 25;
	private static final int MIDDLE_BIN = (BINS - 1) / 2;
	private static final double MAX_ESCAPE_ANGLE = 0.7;
	private static final double BIN_WIDTH = MAX_ESCAPE_ANGLE / (double)MIDDLE_BIN;

	private static int[][][][] statBuffers = new int[DISTANCE_INDEXES][VELOCITY_INDEXES][VELOCITY_INDEXES][BINS];

	private int[] buffer;
	private AdvancedRobot robot;
	private double distanceTraveled;

	GFTWave(AdvancedRobot _robot) {
		this.robot = _robot;
	}

	public boolean test() {
		advance();
		if (hasArrived()) {
			buffer[currentBin()]++;
			robot.removeCustomEvent(this);
		}
		return false;
	}

	double mostVisitedBearingOffset() {
		return (lateralDirection * BIN_WIDTH) * (mostVisitedBin() - MIDDLE_BIN);
	}

	void setSegmentations(double distance, double velocity, double lastVelocity) {
		int distanceIndex = (int)(distance / (MAX_DISTANCE / DISTANCE_INDEXES));
		int velocityIndex = (int)Math.abs(velocity / 2);
		int lastVelocityIndex = (int)Math.abs(lastVelocity / 2);
		buffer = statBuffers[distanceIndex][velocityIndex][lastVelocityIndex];
	}

	private void advance() {
		distanceTraveled += GFTUtils.bulletVelocity(bulletPower);
	}

	private boolean hasArrived() {
		return distanceTraveled > gunLocation.distance(targetLocation) - 18;
	}

	private int currentBin() {
		int bin = (int)Math.round(((Utils.normalRelativeAngle(GFTUtils.absoluteBearing(gunLocation, targetLocation) - bearing)) /
				(lateralDirection * BIN_WIDTH)) + MIDDLE_BIN);
		return GFTUtils.minMax(bin, 0, BINS - 1);
	}

	private int mostVisitedBin() {
		int mostVisited = MIDDLE_BIN;
		for (int i = 0; i < BINS; i++) {
			if (buffer[i] > buffer[mostVisited]) {
				mostVisited = i;
			}
		}
		return mostVisited;
	}
}

class GFTUtils {
	static double bulletVelocity(double power) {
		return 20 - 3 * power;
	}

	static Point2D project(Point2D sourceLocation, double angle, double length) {
		return new Point2D.Double(sourceLocation.getX() + Math.sin(angle) * length,
				sourceLocation.getY() + Math.cos(angle) * length);
	}

	static double absoluteBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX() - source.getX(), target.getY() - source.getY());
	}

	static int sign(double v) {
		return v < 0 ? -1 : 1;
	}

	static int minMax(int v, int min, int max) {
		return Math.max(min, Math.min(max, v));
	}
}

//class GFTMovement {
//	private static final double BATTLE_FIELD_WIDTH = 800;
//	private static final double BATTLE_FIELD_HEIGHT = 600;
//	private static final double WALL_MARGIN = 18;
//	private static final double MAX_TRIES = 125;
//	private static final double REVERSE_TUNER = 0.421075;
//	private static final double DEFAULT_EVASION = 1.2;
//	private static final double WALL_BOUNCE_TUNER = 0.699484;
//
//	private AdvancedRobot robot;
//	private Rectangle2D fieldRectangle = new Rectangle2D.Double(WALL_MARGIN, WALL_MARGIN,
//			BATTLE_FIELD_WIDTH - WALL_MARGIN * 2, BATTLE_FIELD_HEIGHT - WALL_MARGIN * 2);
//	private double enemyFirePower = 3;
//	private double direction = 0.4;
//
//	GFTMovement(AdvancedRobot _robot) {
//		this.robot = _robot;
//	}
//
//	public void onScannedRobot(ScannedRobotEvent e) {
//		double enemyAbsoluteBearing = robot.getHeadingRadians() + e.getBearingRadians();
//		double enemyDistance = e.getDistance();
//		Point2D robotLocation = new Point2D.Double(robot.getX(), robot.getY());
//		Point2D enemyLocation = GFTUtils.project(robotLocation, enemyAbsoluteBearing, enemyDistance);
//		Point2D robotDestination;
//		double tries = 0;
//		while (!fieldRectangle.contains(robotDestination = GFTUtils.project(enemyLocation, enemyAbsoluteBearing + Math.PI + direction,
//				enemyDistance * (DEFAULT_EVASION - tries / 100.0))) && tries < MAX_TRIES) {
//			tries++;
//		}
//		if ((Math.random() < (GFTUtils.bulletVelocity(enemyFirePower) / REVERSE_TUNER) / enemyDistance ||
//				tries > (enemyDistance / GFTUtils.bulletVelocity(enemyFirePower) / WALL_BOUNCE_TUNER))) {
//			direction = -direction;
//		}
//		// Jamougha's cool way
//		double angle = GFTUtils.absoluteBearing(robotLocation, robotDestination) - robot.getHeadingRadians();
//		robot.setAhead(Math.cos(angle) * 100);
//		robot.setTurnRightRadians(Math.tan(angle));
//	}
//}