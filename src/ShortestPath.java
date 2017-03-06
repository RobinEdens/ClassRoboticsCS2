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
		double finalDistance, finalAngle, combY = 0;	
		double combX = 0;
		for (MovementInstruction mi : moveList)	{
			combX +=  mi.getDistance()* Math.cos((mi.getDirection()*Math.PI)/180);
			combY += mi.getDistance()* Math.sin((mi.getDirection()*Math.PI)/180);
		}
		finalDistance = Math.sqrt(Math.pow(combX, 2) + Math.pow(combY, 2));
		finalAngle = (Math.atan2(combY, combX) * 180) / Math.PI;
		MovementInstruction finalMove = new MovementInstruction(finalAngle, finalDistance);
		System.out.println("Distance: " + finalDistance + ", Angle: " + finalAngle);
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
