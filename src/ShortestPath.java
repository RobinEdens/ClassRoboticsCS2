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
		Double [][] test = new Double[tstList.size()][2];
		int i = 0;
		for (MovementInstruction mi : tstList)
		{
			test[i][0] = mi.getDistance()* Math.cos(mi.getDirection());
			test[i][1] = mi.getDistance()* Math.sin(mi.getDirection());
			System.out.println("(" + test[i][0] + ", " + test[i][1] + ")");
			i++;
		}
		Button.ENTER.waitForPressAndRelease();
	}
}
