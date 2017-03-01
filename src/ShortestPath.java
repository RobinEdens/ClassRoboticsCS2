/**
 * @author Zach & Robin A.
 */
import java.util.ArrayList;
import java.lang.Math;
import lejos.nxt.Button;
import edu.bismarckstate.shortestpathhelper.dataentry.MenuController;
import edu.bismarckstate.shortestpathhelper.movement.RobotMovement;
import edu.bismarckstate.shortestpathhelper.util.MovementInstruction;
import edu.bismarckstate.shortestpathhelper.util.MovementInstructions;


public class ShortestPath {

	public static void main (String[] args) {
		MovementInstructions inst = new MovementInstructions();
		MenuController testMenu = new MenuController();
		testMenu.setInstructions(inst);
		ArrayList<MovementInstruction> tstList = new ArrayList<MovementInstruction> (testMenu.Start());
		Double [][] vectors = new Double[tstList.size()][2];
		int finalVector, finalAngle, combX, combY, i = 0;	
		for (MovementInstruction mi : tstList)
		{
			vectors[i][0] = mi.getDistance()* Math.cos((mi.getDirection()*Math.PI)/180);
			combX += vectors[i][0];
			vectors[i][1] = mi.getDistance()* Math.sin((mi.getDirection()*Math.PI)/180);
			combY += vectors[i][1];
			System.out.println("(" + vectors[i][0] + ", " + vectors[i][1] + ")");
			i++;
		}
		finalVector = Math.hypot(combX, combY);
		finalAngle = Math.atan(combY/combX);
		System.out.println("\nOptimized Instructions: Angle " + finalAngle + ", Distance " + finalVector);
		Button.ENTER.waitForPressAndRelease();
	}
}
