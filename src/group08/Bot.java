package group08;
import robocode.*;
import robocode.util.Utils;

import java.awt.Color;
import java.util.ArrayList;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

public class Bot extends TeamRobot
{
	private Scanner scanner;
	private Targeter targeter;
	
	private boolean fullRotation = false;
	private double bulletPower = 3;
	
	public void run()
	{
		setColors(Color.gray,Color.black,Color.yellow);

		scanner = new Scanner();
		targeter = new Targeter(this);
		
		turnGunRight(360);
		fullRotation = true;
		
		// TODO: Make an actually good loop
		while(true)
		{
			turnGunRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent event)
	{
		out.println("SCANNED");
		scanner.updateScan(event);
		
		if(fullRotation)
		{
			out.println("FOUND");
			targeter.shootAtPredicted(scanner.findClosestScan(), bulletPower);
		}
	}
	
	public void onHitByBullet(HitByBulletEvent event)
	{
		// TODO: Change dodging algorithm / parameters probably
	}
	
	public void onHitWall(HitWallEvent event)
	{
		// TODO: don't
	}
}
