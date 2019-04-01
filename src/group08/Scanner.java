package group08;
import java.util.ArrayList;
import robocode.ScannedRobotEvent;

public class Scanner
{
	private ArrayList<ScannedRobotEvent> scans;
	
	public Scanner()
	{
		scans = new ArrayList<ScannedRobotEvent>();
	}
	
	/// Updates or adds the scanned robot to memory
	public void updateScan(ScannedRobotEvent event)
	{
		boolean isLocated = false;
		for(int i = 0; i < scans.size(); ++i)
		{
			if(scans.get(i).getName() == event.getName())
			{
				scans.set(i, event);
				isLocated = true;
			}
		}

		if(!isLocated)
			scans.add(event);
	}
	
	/// Returns the closest robot scan event, or null if none exist
	public ScannedRobotEvent findClosestScan()
	{
		ScannedRobotEvent closestScan = null;
		for(ScannedRobotEvent scan : scans)
			if(closestScan == null || scan.getDistance() < closestScan.getDistance())
				closestScan = scan;
		
		return closestScan;
	}
	
	/// TODO: Turn radar towards all enemies to confirm they're still there
	public ScannedRobotEvent cycleScans()
	{
		throw new Error("Not implemented.");
		
		/*	ScannedRobotEvent closestScan = scanner.findClosestScan();
			if(closestScan != null)
				targeter.shootAtPredicted(closestScan, bulletPower);
			
			ArrayList<ScannedRobotEvent> previousScans = scanner.getScans();
			scanner.setScans(new ArrayList<ScannedRobotEvent>());
			
			for(ScannedRobotEvent scan : previousScans)
				targeter.aimRadarAt(scan);
		*/
	}
	
	
	
	public ArrayList<ScannedRobotEvent> getScans()
	{
		return scans;
	}
	
	public void setScans(ArrayList<ScannedRobotEvent> scans)
	{
		this.scans = scans;
	}
}
