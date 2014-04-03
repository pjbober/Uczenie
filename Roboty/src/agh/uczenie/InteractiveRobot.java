package agh.uczenie;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import robocode.AdvancedRobot;

public class InteractiveRobot extends AdvancedRobot {
	private double moveDirection = 0;
	private double turnDirection = 0;
	private int firing = 0;
	private int aimX, aimY;
	
	@Override
	public void run() {
		while(true){
			setAhead(10 * moveDirection);
			setTurnRight(45 * turnDirection);
			setTurnGunLeftRadians(normalRelativeAngle(getGunHeadingRadians()));

			double angle = normalAbsoluteAngle(Math.atan2(aimX - getX(), aimY - getY()));
			setTurnGunRightRadians(normalRelativeAngle(angle - getGunHeadingRadians()));
			
			if(firing > 0){
				fire(1.0 * firing);
			}
			
			
			execute();
		}
	}
	
	@Override
	public void onKeyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			moveDirection = 1;
			break;
		case KeyEvent.VK_DOWN:
			moveDirection = -1;
			break;
		case KeyEvent.VK_RIGHT:
			turnDirection = 1;
			break;
		case KeyEvent.VK_LEFT:
			turnDirection = -1;
			break;
		case KeyEvent.VK_SPACE:
			firing = 1;
			break;
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			moveDirection = 0;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			turnDirection = 0;
			break;
		case KeyEvent.VK_SPACE:
			firing = 0;
			break;
		}
	}
	
	public void onMouseMoved(MouseEvent e) {
		// Set the aim coordinate = the mouse pointer coordinate
		aimX = e.getX();
		aimY = e.getY();
	}
}
