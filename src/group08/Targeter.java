package group08;

import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.util.Utils;

public class Targeter
{
	private TeamRobot robot;
	
	public Targeter(TeamRobot robot)
	{
		this.robot = robot;
	}
	
	/// Uses linear targeting to predict where the robot will be by the time the bullet
	/// hits them, see http://robowiki.net/wiki/Linear_Targeting
	public void aimGunAt(ScannedRobotEvent event, double bulletPower)
	{
		double absoluteBearing = robot.getHeadingRadians() + event.getBearingRadians();
		double robotX = robot.getX() + event.getDistance() * Math.sin(absoluteBearing);
		double robotY = robot.getY() + event.getDistance() * Math.cos(absoluteBearing);
		
		double robotVelocity = event.getVelocity();
		double robotHeading = event.getHeadingRadians();
		
		double predictedX = robotX;
		double predictedY = robotY;
		double deltaTime = 0;
		while(deltaTime * Rules.getBulletSpeed(bulletPower) < Utility.getDistance(robot.getX(), robot.getY(), predictedX, predictedY))
		{
			++deltaTime;
			predictedX += Math.sin(robotHeading) * robotVelocity;	
			predictedY += Math.cos(robotHeading) * robotVelocity;
			
			if(	predictedX < 18.0 ||
				predictedY < 18.0 ||
				predictedX > robot.getBattleFieldWidth() - 18.0 ||
				predictedY > robot.getBattleFieldHeight() - 18.0)
			{
				predictedX = Math.min(Math.max(18.0, predictedX), robot.getBattleFieldWidth() - 18.0);	
				predictedY = Math.min(Math.max(18.0, predictedY), robot.getBattleFieldHeight() - 18.0);
				break;
			}
		}
		
		double deltaAngle = Utils.normalAbsoluteAngle(Math.atan2(predictedX - robot.getX(), predictedY - robot.getY()));
		
		double gunAngle = Utils.normalRelativeAngle(deltaAngle - robot.getGunHeadingRadians());

		robot.turnGunRightRadians(gunAngle);
	}
	
	/// Directly points the radar towards where the robot is
	public void aimRadarAt(ScannedRobotEvent e)
	{
		double absoluteBearing = robot.getHeadingRadians() + e.getBearingRadians();
		double radarAngle = Utils.normalRelativeAngle(absoluteBearing - robot.getRadarHeadingRadians());
		
		robot.turnRadarRightRadians(radarAngle);
	}
	
	/// Uses linear targeting to fire at the predicted position of an enemy
	public void shootAtPredicted(ScannedRobotEvent e, double bulletPower)
	{
		aimGunAt(e, bulletPower);
		aimRadarAt(e);
		robot.fire(bulletPower);
	}
}
