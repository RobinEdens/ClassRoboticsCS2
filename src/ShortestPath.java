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
		MenuController menu = new MenuController();
		menu.setInstructions(inst);
		ArrayList<MovementInstruction> moveList = new ArrayList<MovementInstruction> (menu.Start());
		Double [][] vectors = new Double[moveList.size()][2];
		double finalDistance, finalAngle, combY = 0;	
		double combX = 0;
		int i = 0;
		for (MovementInstruction mi : moveList)	{
			vectors[i][0] = mi.getDistance()* Math.cos((mi.getDirection()*Math.PI)/180);
			combX += vectors[i][0];
			vectors[i][1] = mi.getDistance()* Math.sin((mi.getDirection()*Math.PI)/180);
			combY += vectors[i][1];
			i++;
		}
		finalDistance = Math.sqrt(Math.pow(combX, 2) + Math.pow(combY, 2));
		finalAngle = (Math.atan2(combY, combX) * 180) / Math.PI;
		MovementInstruction finalMove = new MovementInstruction(finalAngle, finalDistance);
		Button.ENTER.waitForPressAndRelease();
		RobotMovement bot = new RobotMovement();
		if (bot.isDiffPilotOk()) {
			System.out.println("Let's rock");
			Button.ENTER.waitForPressAndRelease();
		} else {
			System.out.println("There was an error");
			Button.ENTER.waitForPressAndRelease();
		}
		bot.move(finalMove);	
	}
}
