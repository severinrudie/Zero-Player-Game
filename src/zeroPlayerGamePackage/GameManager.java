package zeroPlayerGamePackage;

import java.util.HashMap;

public class GameManager {

	public static void main(String[] args) {
		
		// START TEST CODE
//		Regiment tester0 = new Regiment(0);
//		tester0.setPositionX(0);
//		tester0.setPositionY(0);
//		PositionObject position = new PositionObject(0, 0);
//		BoardBuilder.addToOccupiedSquares(position);
//		
//		Regiment tester1 = new Regiment(1);
//		tester1.setPositionX(1);
//		tester1.setPositionY(0);
//		PositionObject position1 = new PositionObject(1, 0);
//		BoardBuilder.addToOccupiedSquares(position1);
//		PositionObject tempPosition = new PositionObject(1, 0);
//		BoardBuilder.team1RegimentLocations.put("hi", tempPosition);
		
//		System.out.println(tester0.checkOpenDirections());
//		tester0.regimentCheckEnemyLocations();
		
		
		UnitLocationList team0 = new UnitLocationList();
		Regiment tester0 = new Regiment(0);
		Regiment tester1 = new Regiment(0);
		
		PositionObject position0 = new PositionObject(0, 0);
		PositionObject position1 = new PositionObject(1, 0);
		
		team0.addUnit(tester0, position0);
		team0.addUnit(tester1, position1);
		
		System.out.println(team0.getRegimentNames());
		System.out.println(team0.getRegimentPositions().get(0));
		
		// END TEST CODE

	}  // end main
		
}  // end GameManager