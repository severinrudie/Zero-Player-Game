package zeroPlayerGamePackage;

import java.util.ArrayList;
import java.util.HashMap;

import zeroPlayerGamePackage.ReturnObjects.PositionObject;
import zeroPlayerGamePackage.ReturnObjects.PositionValueAndType;

public class PredictionHolder {
	
	public PredictionHolder(Regiment regiment) {
		super();
		this.regiment = regiment;
		regiment.assignPredictionHolder(this);
		
//		directionArrayHolder.add(N);
//		directionArrayHolder.add(NE);
//		directionArrayHolder.add(E);
//		directionArrayHolder.add(SE);
//		directionArrayHolder.add(S);
//		directionArrayHolder.add(SW);
//		directionArrayHolder.add(W);
//		directionArrayHolder.add(NW);
	}  // end constructor
	

	private Regiment regiment;
	
	public ArrayList<ArrayList<PositionValueAndType>> surroundingPositionPerimeters = 
			new ArrayList<ArrayList<PositionValueAndType>>();

//	private ArrayList<PositionValueAndType> N = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> NE = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> E = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> SE = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> S = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> SW = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> W = new ArrayList<PositionValueAndType>();
//	private ArrayList<PositionValueAndType> NW = new ArrayList<PositionValueAndType>();
	
	public ArrayList<PositionValueAndType> surroundingPositions = new ArrayList<PositionValueAndType>();
	
	
	// This populates each position next to the Regiment, and each next to that.
	// Used for weighing possible moves
	public void populateSurroundings() {
		
    	ArrayList<PositionObject> positions = surroundingsToPositions(regiment.getPositionObject());
		
    	// check what, if anything, is occupying those surrounding positions
		ArrayList<String> types = new ArrayList<String>();
		
		for (int i = 0; i < positions.size(); i++) {

			if (regiment.getTeam() == 0 
					&& UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 1)
					|| regiment.getTeam() == 1 
					&& UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 0)) {
				
				types.add("enemy");
				
			} else if (regiment.getTeam() == 0 
					&& UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 0)
					|| regiment.getTeam() == 1 
					&& UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 1)) {
				
				types.add("ally");
				
			} else if (positions.get(i).getPositionX() < 0
					|| positions.get(i).getPositionX() >= BoardBuilder.BOARD_WIDTH
					|| positions.get(i).getPositionY() < 0
					|| positions.get(i).getPositionY() >= BoardBuilder.BOARD_HEIGHT) {
				
				types.add("blocked");
				
			} else if (!(UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 1))
					&& !(UnitLocationList.isPositionOccupiedByTeam(positions.get(i), 0))) {
				
				types.add("empty");
				
			}  // end if statement
			
			
		}  // end for loop
		
	    // pop each PositionObject and Type into a pvat, then add it to the ArrayList		
		for (int i = 0; i < positions.size(); i++) {
			
			PositionValueAndType tempPvat = new PositionValueAndType(positions.get(i), 0, types.get(i));
			
			surroundingPositions.add(tempPvat);
			
		}  // end for loop
		
	}  // end populateSurroundingPositions
	
	public ArrayList<PositionObject> surroundingsToPositions(PositionObject thisPosition) {
	
		// convert neighboring directions to PositionObjects; check that they're on the board
		ArrayList<String> eightDirections = BoardBuilder.eightDirections();
		ArrayList<PositionObject> positions = new ArrayList<PositionObject>();
		
		HasGridPosition positionFactory = new HasGridPosition();
		positionFactory.setPositionWithPositionObject(thisPosition);
		
		for (int i = 0; i < eightDirections.size(); i++) {
			
			PositionObject place = positionFactory.directionToPositionObject(eightDirections.get(i));
			
			
//			if (place.getPositionX() >= 0 
//					&& place.getPositionX() <= (BoardBuilder.BOARD_WIDTH -1) 
//					&& place.getPositionY() >= 0 
//					&& place.getPositionY() <= (BoardBuilder.BOARD_HEIGHT -1)) {
				positions.add(place);
			
//			}  // end if statement
			
		}  // end for loop
		
		return positions;
		
	}  // surroundingsToPositions
	
	// populates surroundingPerimeters and tests for type. Returns a PositionObject, 0, 
	// Type pvat
	public void populateSurroundingPerimeters() {
		
		for (int i = 0; i < surroundingPositions.size(); i++) {
			
			PositionValueAndType tempPvat = surroundingPositions.get(i);
			PositionObject testPosition = tempPvat.getPosition();
			
			ArrayList<PositionObject> testSurroundings = surroundingsToPositions(testPosition);
			ArrayList<PositionValueAndType> testedPerimeters = new ArrayList<PositionValueAndType>();
			
			for (int j = 0; j < testSurroundings.size(); j++) {
				
				if (regiment.getTeam() == 0 
						&& UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 1)
						|| regiment.getTeam() == 1 
						&& UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 0)) {
					
					PositionValueAndType pvat = new PositionValueAndType(testSurroundings.get(j), 
							0, "enemy");
					testedPerimeters.add(pvat);
					
				} else if (regiment.getTeam() == 0 
						&& UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 0)
						|| regiment.getTeam() == 1 
						&& UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 1)) {
					
					PositionValueAndType pvat = new PositionValueAndType(testSurroundings.get(j), 
							0, "ally");
					testedPerimeters.add(pvat);
					
				} else if (testSurroundings.get(j).getPositionX() < 0
						|| testSurroundings.get(j).getPositionX() >= BoardBuilder.BOARD_WIDTH
						|| testSurroundings.get(j).getPositionY() < 0
						|| testSurroundings.get(j).getPositionY() >= BoardBuilder.BOARD_HEIGHT) {
					
					PositionValueAndType pvat = new PositionValueAndType(testSurroundings.get(j), 
							0, "blocked");
					testedPerimeters.add(pvat);
					
				} else if (!(UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 1))
						&& !(UnitLocationList.isPositionOccupiedByTeam(testSurroundings.get(j), 0))) {
					
					PositionValueAndType pvat = new PositionValueAndType(testSurroundings.get(j), 
							0, "empty");
					testedPerimeters.add(pvat);
					
				}  // end if statement
				
			}  // end for loop (testSurroundings)
			
			surroundingPositionPerimeters.add(testedPerimeters);
			
		}  // end for loop (surroundingPositions)
		
	}  // end populateSurroundingPerimeters
	
	// sets up / calls: areFlanksOpen, 
	public void loopOverSurroundingPerimeters(UnitLocationList myTeam, UnitLocationList enemyTeam) {
		
		HashMap<Integer, PositionObject> allyTeamHashMap;
		HashMap<Integer, PositionObject> enemyTeamHashMap;
		
		if (regiment.getTeam() == 0) {
			allyTeamHashMap = UnitLocationList.team0RegimentLocations;
			enemyTeamHashMap = UnitLocationList.team1RegimentLocations;
		} else if (regiment.getTeam() == 1) {
			allyTeamHashMap = UnitLocationList.team1RegimentLocations;
			enemyTeamHashMap = UnitLocationList.team0RegimentLocations;
		} else {
			// two below HashMaps created only so that the variables are instantiated.  One of the
			// above conditions should pass, or something is seriously wrong
			allyTeamHashMap = new HashMap<Integer, PositionObject>();
			enemyTeamHashMap = new HashMap<Integer, PositionObject>();
			System.out.println("Error: In PredictionHolder.loopOverSurroundingPerimeters(),"
					+ " regiment.getTeam() returned a team other than 0 or 1");
			new Exception().printStackTrace();
		}  // end if statement
		
		for (int i = 0; i < surroundingPositionPerimeters.size(); i++) {
			
			int alliesNearby = 0;
			int healthyAlliesNearby = 0;
			int healthyEnemiesNearby = 0;
			
			for (PositionValueAndType pvat: surroundingPositionPerimeters.get(i)) {
				
				Regiment regiment;
				
				if (pvat.getType() == "ally") {
					
					alliesNearby += 1;
					
					regiment = myTeam.getRegimentByPosition(pvat.getPosition());
					// if unit is not critical or low health or morale
					if (!(regiment.getFuzzyHealth().equals("critical")
							|| regiment.getFuzzyHealth().equals("low")
							|| regiment.getFuzzyMorale().equals("critical")
							|| regiment.getFuzzyMorale().equals("low"))) {
						
					healthyAlliesNearby += 1;
					
					}  // end if ally is healthy
					
					if (pvat.getType() == "enemy") {
						
						regiment = enemyTeam.getRegimentByPosition(pvat.getPosition());
						// if unit is not critical or low health or morale
						if (!(regiment.getFuzzyHealth().equals("critical")
								|| regiment.getFuzzyHealth().equals("low")
								|| regiment.getFuzzyMorale().equals("critical")
								|| regiment.getFuzzyMorale().equals("low"))) {
						
							healthyEnemiesNearby += 1;
							
						}  // end if enemy is healthy

					}  // end if regiment is enemy
					
				}  // end if type == ally
				
			}  // end for pvat in i
			
			double openFlanksScore = areFlanksOpen(alliesNearby);
			
			openFlanksScore *= this.regiment.getDefensiveModifier();
			surroundingPositions.get(i).addValue(openFlanksScore);
			
			double healthyAlliesNearbyScore = areHealthyAlliesNearby(healthyAlliesNearby);
			
			healthyAlliesNearbyScore *= this.regiment.getDefensiveModifier();
			surroundingPositions.get(i).addValue(healthyAlliesNearbyScore);
			
			double healthyEnemiesNearbyScore = areHealthyEnemiesNearby(healthyEnemiesNearby);
			
			healthyEnemiesNearbyScore *= this.regiment.getDefensiveModifier();
			surroundingPositions.get(i).addValue(healthyEnemiesNearbyScore);
			
		}  // end for i in surroundingPerimeters
	
	}  // end loopOverSurroundingPerimeters
	
	public double areFlanksOpen(int alliesNearby) {
		if (alliesNearby < 2) {
			return -4;
		} else {
			return 0;
		}  // end if statement
	}  // end areFlanksOpen
	
	public double areHealthyAlliesNearby(int healthyAlliesNearby) {
		if (healthyAlliesNearby < 2) {
			return -4;
		} else if (healthyAlliesNearby < 4) {
			return 0;
		} else if (healthyAlliesNearby < 6) {
			return 3;
		} else {
			return 5;
		}  // end if statement
	}  // end areHealthyAlliesNearby
	
	public double areHealthyEnemiesNearby(int healthyEnemiesNearby) {
		if (healthyEnemiesNearby < 1) {
			return 1;
		} else if (healthyEnemiesNearby < 2) {
			return -.5;
		} else if (healthyEnemiesNearby < 3) {
			return -1;
		} else if (healthyEnemiesNearby < 6) {
			return -4;
		} else {
			return -7;
		}
	}  // end areHealthyEnemiesNearby
	
}  // end PredictionHolder
