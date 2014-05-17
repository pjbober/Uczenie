package agh.uczenie.strategy.strategies.atomic;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;

/**
 * Based on RamFire robot:
 *
 ********************************************************************************
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************
 */

public class BatteringRamStrategy extends BaseStrategy {
	/// How to turn whole tank
	private boolean clockwise = true;

	public BatteringRamStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public Color bodyColor() {
		return Color.lightGray;
	}

	@Override
	public Color gunColor() {
		return Color.blue;
	}

	@Override
	public Color radarColor() {
		return Color.lightGray;
	}

	@Override
	public void setup() {
		super.setup();

		robot.setAdjustGunForRobotTurn(false);
		robot.setAdjustRadarForGunTurn(false);

		calibrate();
	}

	@Override
	public void loopAction() {
		super.loopAction();
		robot.setTurnRight(360 * (clockwise ? 1 : -1));
	}

	/**
	 * We have a target.  Go get it.
	 */
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		clockwise = (e.getBearing() >= 0);

		robot.setTurnRight(0);
		robot.turnRight(e.getBearing());
		robot.setAhead(0);
		robot.setAhead(e.getDistance() + 5);
		robot.scan(); // Might want to move ahead again!
	}

	/**
	 * Turn to face robot, fire hard, and ram him again!
	 */
	public void onHitRobot(HitRobotEvent e) {
		double bearing = e.getBearing();
		double energy = e.getEnergy();

		clockwise = (bearing >= 0);
		robot.turnRight(bearing);

		// Determine a shot that won't kill the robot...
		// We want to ram him instead for bonus points
		double firePower = 0;
		if (energy > 16) {
			firePower = 3;
		} else if (energy > 10) {
			firePower = 2;
		} else if (energy > 4) {
			firePower = 1;
		} else if (energy > 2) {
			firePower = 0.5;
		} else if (energy > .4) {
			firePower = 0.1;
		}
		robot.fire(firePower);
		robot.ahead(40); // Ram him again!
	}

}
