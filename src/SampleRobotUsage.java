import java.util.ArrayList;

import lejos.nxt.Button;
import edu.bismarckstate.shortestpathhelper.dataentry.MenuController;
import edu.bismarckstate.shortestpathhelper.movement.RobotMovement;
import edu.bismarckstate.shortestpathhelper.util.MovementInstruction;
import edu.bismarckstate.shortestpathhelper.util.MovementInstructions;


public class SampleRobotUsage {

	public static void main(String[] args) {
		//declare the movement instructions which will be used in the MenuController to parse the input files
		MovementInstructions inst = new MovementInstructions();
		//create the menu controller that will display and run the menus
		MenuController testMenu = new MenuController();
		//set the instruction to the menu
		testMenu.setInstructions(inst);
		
		//create an arraylist of movement instructions.  TestMenu.Start() runs the menu program and returns a collection of movement instructions
		ArrayList<MovementInstruction> tstList = new ArrayList<MovementInstruction> (testMenu.Start());
		
		//Output all the instructions loaded from the menu
		for (MovementInstruction mi : tstList)
		{
			System.out.println(mi.toString());
		}
		Button.ENTER.waitForPressAndRelease();
		
		//Create a robot movement object which will be used to move the robot with movement instructions
		RobotMovement rob = new RobotMovement();
		//make sure that the diffpilot got created correctly and everything is good for us to continue
		if (rob.isDiffPilotOk()){
			System.out.println("Woohoo it worked");
			Button.ENTER.waitForPressAndRelease();
		}else{
			System.out.println("There was an error");
			Button.ENTER.waitForPressAndRelease();
		}
		
		//make the robot move through all the instructions that is stored in the array list.
		for (MovementInstruction mi: tstList)
		{
			rob.move(mi);			
		}
	}

}
