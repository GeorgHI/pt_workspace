package group08;
import robocode.*;
import robocode.util.Utils;

import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

public class Bot extends TeamRobot
{
	public void run()
	{
		setColors(Color.gray,Color.black,Color.yellow);

		while(true)
		{
			turnGunRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e)
	{
		double bulletPower = 3;
		
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		double robotX = getX() + e.getDistance() * Math.sin(absoluteBearing);
		double robotY = getY() + e.getDistance() * Math.cos(absoluteBearing);
		
		double robotVelocity = e.getVelocity();
		double robotHeading = e.getHeadingRadians();
		
		double predictedX = robotX;
		double predictedY = robotY;
		double deltaTime = 0;
		while(deltaTime * Rules.getBulletSpeed(bulletPower) < getDistance(getX(), getY(), predictedX, predictedY))
		{
			++deltaTime;
			predictedX += Math.sin(robotHeading) * robotVelocity;	
			predictedY += Math.cos(robotHeading) * robotVelocity;
			
			if(	predictedX < 18.0 ||
				predictedY < 18.0 ||
				predictedX > getBattleFieldWidth() - 18.0 ||
				predictedY > getBattleFieldHeight() - 18.0)
			{
				predictedX = Math.min(Math.max(18.0, predictedX), getBattleFieldWidth() - 18.0);	
				predictedY = Math.min(Math.max(18.0, predictedY), getBattleFieldHeight() - 18.0);
				break;
			}
		}
		
		double deltaAngle = Utils.normalAbsoluteAngle(Math.atan2(predictedX - getX(), predictedY - getY()));
		
		double gunAngle = Utils.normalRelativeAngle(deltaAngle - getGunHeadingRadians());
		double radarAngle = Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()) - gunAngle;
		
		turnGunRightRadians(gunAngle);
		turnRadarRightRadians(radarAngle);
		fire(bulletPower);

		scan();
	}
	
	public void onHitByBullet(HitByBulletEvent e)
	{
	}
	
	public void onHitWall(HitWallEvent e)
	{
	}
	
	private double getDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
