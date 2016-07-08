package Test;
import java.util.ArrayList;

import java.util.List;

import AStar.PotatoAStar;
import AStar.Point;

import Robot.NxtMovement;;


public class PotatoMain {
	
	static final int SOUTH = 0;
	static final int NORTH = 1;
	static final int EAST = 2;
	static final int WEST = 3;
	
	static final int OBSTACLE = 1;
	static final int ROBOT = 2;
	static final int GOAL = 3;
	static final int FREE = 0;
	static PotatoAStar potatoAlgorithm;
	
//	public static int[][] map = new int[MAPSIZE][MAPSIZE];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// PotatoAStar dStar = new PotatoAStar(null, null, null);

		potatoAlgorithm = new PotatoAStar();
		NxtMovement littlePotato = new NxtMovement();
		ArrayList<Point> path = new ArrayList<>();

		for (int i = 0; i < PotatoAStar.MAPSIZE; i++){
			for (int j = 0; j < PotatoAStar.MAPSIZE; j++){
				potatoAlgorithm.setValue(i, j, FREE);
			}
		}

		int robotOrientation = NORTH;
		Point robotPosition = new Point((int)Math.floor(PotatoAStar.MAPSIZE/2.0), (int)Math.floor(PotatoAStar.MAPSIZE/2.0));
		Point goalPosition = searchGoal(littlePotato, robotPosition);
		potatoAlgorithm.setValue(goalPosition.x, goalPosition.y, GOAL);
		System.out.println("Goal: " + goalPosition.x + " " + goalPosition.y);
		potatoAlgorithm.setGoal(goalPosition);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(robotPosition.x + " " + robotPosition.y);
		System.out.println("Insira os obstaculos");
		// espera colocar os obstaculos
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lookForObstacles(littlePotato, robotPosition, robotOrientation);
		
		path = potatoAlgorithm.calculateDStar(robotPosition);
		
		// path.size()-1 para nao entrar dentro do quadrado do objetivo
		// senao ele vai empurrar o objetivo haha
		for(int i = 1; i < path.size() - 1; i++){
			
			Point p = path.get(i);
			System.out.println("--------");
			System.out.println("Estou em " + robotPosition.x + " " + robotPosition.y);
			System.out.println("Indo para " + p.x + " " + p.y);
			System.out.println("Obj em " + goalPosition.x + " " + goalPosition.y);
			

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			robotOrientation = lookToPoint(p, robotPosition, littlePotato, robotOrientation);

			if(littlePotato.hasObstacleAhead()){
				
//				System.out.println("New obstacle");
				if (potatoAlgorithm.getValue(p.x, p.y) != GOAL)
					potatoAlgorithm.setValue(p.x, p.y, OBSTACLE);
				
			}
//			if(i + 1 < path.size()) {
//				if(potatoAlgorithm.getValue(path.get(i+1).x, path.get(i+1).y) != OBSTACLE) {
				if(potatoAlgorithm.getValue(p.x, p.y) != OBSTACLE) {
					littlePotato.moveForward();
					
					robotPosition = new Point(p);
					lookForObstacles(littlePotato, robotPosition, robotOrientation);
				} else {
					System.out.println("Recalculando");
					path = potatoAlgorithm.calculateDStar(robotPosition);
					i = 0;
				}
//			}
		}

		robotOrientation = lookToPoint(path.get(path.size()-1), robotPosition, littlePotato, robotOrientation);

	}
	
	private static Point searchGoal(NxtMovement lp, Point rp){
		
		Point goal;
		
		int distance = lp.searchGoal();
		
		goal = new Point(rp.x - distance, rp.y);		
		
		return goal;
		
	}
	
	private static int lookToPoint(Point p, Point robotPosition, NxtMovement lp, int orientation){
//		System.out.println(robotPosition.x + " " + robotPosition.y + " Robo");
//		System.out.println(p.x + " " + p.y + " destino");
		int goTo;
		if (p.x == robotPosition.x){
			if(p.y < robotPosition.y){
				goTo = WEST;
			} else {
				goTo = EAST;
			}
		} else {
			if(p.x < robotPosition.x){
				goTo = NORTH;
			} else {
				goTo = SOUTH;
			}
		}
//		System.out.println(orientation);
//		System.out.println(goTo);
		switch(orientation){
			case NORTH:
				switch(goTo){
					case NORTH:
						return NORTH;
//						break;
					case SOUTH:
						lp.moveLeft();
						lp.moveLeft();
						return SOUTH;
//						break;
					case EAST:
						lp.moveRight();
						return EAST;
//						break;
					case WEST:
						lp.moveLeft();
						return WEST;
//						break;
					default:
						// showError
						break;
				}
				break;
			case SOUTH:
				switch(goTo){
					case NORTH:
						lp.moveLeft();
						lp.moveLeft();
						return NORTH;
//						break;
					case SOUTH:
						return SOUTH;
//						break;
					case EAST:
						lp.moveLeft();
						return EAST;
//						break;
					case WEST:
						lp.moveRight();
						return WEST;
//						break;
					default:
						// showError
						break;
				}
				break;
			case EAST:
				switch(goTo){
					case NORTH:
						lp.moveLeft();
						return NORTH;
//						break;
					case SOUTH:
						lp.moveRight();
						return SOUTH;
//						break;
					case EAST:
						return EAST;
//						break;
					case WEST:
						lp.moveLeft();
						lp.moveLeft();
						return WEST;
//						break;
					default:
						// showError
						break;
				}
				break;
			case WEST:
				switch(goTo){
					case NORTH:
						lp.moveRight();
						return NORTH;
//						break;
					case SOUTH:
						 lp.moveLeft();
						return SOUTH;
//						break;
					case EAST:
						lp.moveLeft();
						lp.moveLeft();
						return EAST;
//						break;
					case WEST:
						return WEST;
//						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		return -1;
	}
	
	
	private static void lookForObstacles(NxtMovement lp, Point rPos, int orientation){
		List<Integer> values;
		
		System.out.println("Entrei no look");
		
		values = lp.lookAround();
		
		System.out.println(values.get(0) + " " + values.get(1) + " " + values.get(2));
		
		switch(orientation){
			
			case NORTH:
				// To the front
				if(values.get(0) != -1 && rPos.x - values.get(0) >= 0)
					if (potatoAlgorithm.getValue(rPos.x - values.get(0), rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x - values.get(0), rPos.y, OBSTACLE);
					}
				// To the left
				if(values.get(1) != -1 && rPos.y - values.get(1) >= 0)
					if (potatoAlgorithm.getValue(rPos.x, rPos.y - values.get(1)) != GOAL){
						potatoAlgorithm.setValue(rPos.x,rPos.y - values.get(1),OBSTACLE);
					}
				// To the right
				if(values.get(2) != -1 && rPos.y + values.get(2) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x, rPos.y + values.get(2)) != GOAL){
						potatoAlgorithm.setValue(rPos.x,rPos.y + values.get(2),OBSTACLE);
					}
				break;
			case SOUTH:
				// To the front
				if(values.get(0) != -1 && rPos.x + values.get(0) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x + values.get(0), rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x + values.get(0), rPos.y, OBSTACLE);
					}
				// To the left
				if(values.get(1) != -1 && rPos.y + values.get(1) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x, rPos.y + values.get(1)) != GOAL){
						potatoAlgorithm.setValue(rPos.x, rPos.y + values.get(1), OBSTACLE);
					}
				// To the right
				if(values.get(2) != -1 && rPos.y - values.get(2) >= 0)
					if (potatoAlgorithm.getValue(rPos.x, rPos.y - values.get(2)) != GOAL){
						potatoAlgorithm.setValue(rPos.x, rPos.y - values.get(2),OBSTACLE);
					}
				
				break;
			case EAST:
				// To the front
				if(values.get(0) != -1 && rPos.y + values.get(0) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x,rPos.y + values.get(0)) != GOAL){
						potatoAlgorithm.setValue(rPos.x, rPos.y + values.get(0), OBSTACLE);
					}
				// To the left
				if(values.get(1) != -1 && rPos.x - values.get(1) >= 0)
					if (potatoAlgorithm.getValue(rPos.x  - values.get(1),rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x  - values.get(1), rPos.y, OBSTACLE);
					}
				// To the right
				if(values.get(2) != -1 && rPos.x + values.get(2) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x + values.get(2),rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x + values.get(2), rPos.y, OBSTACLE);
					}
				break;
			case WEST:
				// To the front
				if(values.get(0) != -1 && rPos.y - values.get(0) >= 0)
					if (potatoAlgorithm.getValue(rPos.x,rPos.y - values.get(0)) != GOAL){
						potatoAlgorithm.setValue(rPos.x, rPos.y - values.get(0), OBSTACLE);
					}
				// To the left
				if(values.get(1) != -1 && rPos.x + values.get(1) < PotatoAStar.MAPSIZE)
					if (potatoAlgorithm.getValue(rPos.x + values.get(1), rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x + values.get(1),rPos.y, OBSTACLE);
					}
				// To the right
				if(values.get(2) != -1 && rPos.x - values.get(2) >= 0)
					if (potatoAlgorithm.getValue(rPos.x - values.get(2), rPos.y) != GOAL){
						potatoAlgorithm.setValue(rPos.x - values.get(2), rPos.y, OBSTACLE);
					}
				break;
			default:
				System.out.println("Default");
				break;
		}		
	}
	
}
