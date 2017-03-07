/**
 * @author Zach & Robin A.
 */
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math;
import edu.bismarckstate.shortestpathhelper.dataentry.MenuController;
import edu.bismarckstate.shortestpathhelper.movement.RobotMovement;
import edu.bismarckstate.shortestpathhelper.util.MovementInstruction;
import edu.bismarckstate.shortestpathhelper.util.MovementInstructions;

public class AllPaths {
	
	public static ArrayList<MovementInstruction> parseData (String fileName) {
			ArrayList<MovementInstruction> moveList = new ArrayList<MovementInstruction> ();
			File data = new File(fileName);
			try {
				InputStream is = new FileInputStream(data);
				DataInputStream din = new DataInputStream(is);
				BufferedReader br = new BufferedReader(new InputStreamReader(din));
				String strLineDirection;
				while((strLineDirection = br.readLine()) != null) {
					String strLineDistance = br.readLine();
					if (strLineDistance != null) {
						MovementInstruction mi = new MovementInstruction();
						mi.setDirection(Double.parseDouble(strLineDirection.trim()));
						mi.setDistance(Double.parseDouble(strLineDistance.trim()));
						moveList.add(mi);
					}
					if (!br.ready()) {
						break;
					}							
				}
				din.close();
			} catch(IOException ioe) {
				System.out.println("error:" + ioe.getMessage());
				Button.ENTER.waitForPressAndRelease();
			} catch(Exception ex) {
				System.out.println("error:" + ex.getMessage());
				Button.ENTER.waitForPressAndRelease();
			}
			return moveList;
		}
	
	public static void main (String[] args) {
		final String [] FILE_NAME = {"alist.pnt", "blist.pnt", "clist.pnt", "dlist.pnt", "oddangles.pnt"};
		ArrayList<MovementInstruction> moveList = new ArrayList<MovementInstruction>();
		double [][] vectors = new double [FILE_NAME.length][2];
		double combX= 0;	
		double combY = 0;
		double lastAngle = 0;
		for (int i = 0; i < FILE_NAME.length; i++) {
			moveList = parseData(FILE_NAME[i]);
			for (MovementInstruction mi : moveList)	{
				combX +=  mi.getDistance()* Math.cos((mi.getDirection()*Math.PI)/180);
				combY += mi.getDistance()* Math.sin((mi.getDirection()*Math.PI)/180);
			}
			vectors[i][0] = combX; vectors[i][1] = combY;
			System.out.println("(" + combX + ", " + combY + ")");
			combX = 0; combY = 0;
		}
		
		moveList.clear();
		for (int i = 0; i < vectors.length; i++) {
			double distance, angle;
			if (i == 0) {
				distance = Math.sqrt(Math.pow(vectors[i][0], 2) + Math.pow(vectors[i][1], 2));
				angle = (Math.atan2(vectors[i][1], vectors[i][0]) * 180) / Math.PI;
			} else {
				double x = vectors[i][0] - vectors[i-1][0];
				double y = vectors[i][1] - vectors[i-1][1];
				distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				angle = ((Math.atan2(y, x) * 180) / Math.PI);
			}
			moveList.add(new MovementInstruction(angle - lastAngle, distance));
			lastAngle = angle;
		}
		
		for (MovementInstruction mi: moveList) {
			System.out.println("Distance: " + mi.getDistance() + ", Angle: " + mi.getDirection());
		}

		RobotMovement bot = new RobotMovement();
		if (bot.isDiffPilotOk()) {
			System.out.println("Let's rock");
			Button.ENTER.waitForPressAndRelease();
		} else {
			System.out.println("There was an error");
			Button.ENTER.waitForPressAndRelease();
		}
		
		for (MovementInstruction mi: moveList) {
			bot.move(mi);	
		}
	}
}
