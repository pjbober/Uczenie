package agh.uczenie.strategy.strategies.atomic;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.awt.*;

/**
 * Based on DustBunny v. 3.8 by Michael Dorgan
 * Unknown open-source license, probably Public Domain
 * http://robowiki.net/wiki/DustBunny
 */

public class AntiGravityStrategy extends BaseStrategy
{
//	static final double GUN_FACTOR = 30;
//	static final int AIM_START = 10;
//	static final double AIM_FACTOR = 1.008;
//	static final int FIRE_FACTOR = 7;

	static double xForce;
	static double yForce;
//	static double lastDistance;

	public AntiGravityStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void setup()
	{
		// do this to hit stationary targets
		robot.setAdjustGunForRobotTurn(true);

		//This costs 6 bytes or so and 1 bad shot at the beginning of each round.
		// Reset range finder
//		lastDistance = Double.POSITIVE_INFINITY;

		// Do infinite radar.  Saves much space (thanks Dr. Loco!)
		robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

	@Override
	public Color bodyColor() {
		return Color.blue;
	}

	@Override
	public Color gunColor() {
		return Color.black;
	}

	@Override
	public Color radarColor() {
		return Color.blue;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e)
	{
		// Get Abs bearing for aiming routines (and A-Grav)
		// and distance for just about everything else :)
		double	absoluteBearing = e.getBearingRadians() + robot.getHeadingRadians();
		double  distance = e.getDistance();

		// Use a very simple running average system.  /2 is as cheap as I can get this
		xForce = xForce *.9 - Math.sin(absoluteBearing) / distance;
		yForce = yForce *.9 - Math.cos(absoluteBearing) / distance;

		// Get our turn angle - factor in distance from each wall every time so we get
		// pushed towards the center when close to the walls.  This took a long time to come up with.
		robot.setTurnRightRadians(Utils.normalRelativeAngle(
				Math.atan2(xForce + 1 / robot.getX() - 1 / (robot.getBattleFieldWidth() - robot.getX()),
						yForce + 1 / robot.getY() - 1 / (robot.getBattleFieldHeight() - robot.getY()))
						- robot.getHeadingRadians()
		));

		// Move ahead depending on how much turn is needed.
		robot.setAhead(120 - Math.abs(robot.getTurnRemaining()));

		// TODO: fire disabled
//		// If we're at 0 and pointed at a target, fire.
////		if(setFireBullet(Math.min(2.49999,getEnergy() * GUN_FACTOR / distance)) != null)
//		if(robot.setFireBullet(Math.min(2.49999, robot.getEnergy() / FIRE_FACTOR)) != null)
//		{
//			lastDistance = Double.POSITIVE_INFINITY;
//		}
//
//		// Lock onto closest bots
//		if(lastDistance+100 > distance)
//		{
//			lastDistance = distance;
//
//			// and only the closest bot
//			// Radar lock as we approach shooting time
//			// Lowering this value causes us to turn more often do to better data.
//			if(robot.getGunHeat() < 1)
//			{
//				// Let this var be equal the the absolute bearing now...
//				// and set the radar.
//				robot.setTurnRadarLeft(robot.getRadarTurnRemaining());
//				// This would be nice to prevent the occasional long shot.
//				//clearAllEvents();
//			}
//			// Infinity style gun fits!!!!
//			robot.setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteBearing - robot.getGunHeadingRadians() +
////				Math.random() *
//
////				Math.max(1 - distance / (400), 0) *
//					(e.getVelocity() / (AIM_START + Math.pow(AIM_FACTOR, distance))) * Math.sin(e.getHeadingRadians() - absoluteBearing)));
//
//		}
	}
}