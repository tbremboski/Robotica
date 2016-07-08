package AStar;

import java.util.ArrayList;

import Test.PotatoMain;

/**
 * @author william
 *
 */
public class PotatoAStar {
	
	public static final int MAPSIZE = 25;
	
	private static final int OBSTACLE = 1;
	private static final int FREE = 0;
	private static final int ROBOT= 2;
	
//	private int[][] map;
	private Point goal;
	private Point initialP;
	
	private ArrayList<Node> openNodes;
//	private ArrayList<Node> closedNodes;

	int[][] map = new int[MAPSIZE][MAPSIZE];

	public PotatoAStar(){
	}
	public void setGoal(Point goal){
		System.out.println(goal.x + " " + goal.y);
		this.goal = goal;
	}

	public int getValue(int x, int y){
		if(x >= 0 ){
			
		}
		return map[x][y];
	}

	public void setValue(int x, int y, int value){
		map[x][y] = value;
		if(value == OBSTACLE)
			System.out.println("obs em " + x + " " + y);
	}

	public ArrayList<Point> calculateDStar(Point initialP) {
		
		openNodes = new ArrayList<>();
//		closedNodes = new ArrayList<>();
		
		this.initialP = new Point(initialP);

		Node n = new Node(initialP, goal, null);

		for (int i = 0; i < MAPSIZE; i++){
			for (int j = 0; j < MAPSIZE; j++){
//				if(map[i][j] == OBSTACLE)
//				System.out.print(map[i][j] + " ");
			}
//			System.out.println();
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		openNodes.add(n);

		while (!openNodes.isEmpty()){

			Node current = openNodes.get(0);
//			System.out.println(current.getPoint().x + " " + current.getPoint().y);
			
//			System.out.println("Found a new node!");
//			
//			System.out.println(current.toString());

//			this.closedNodes.add(current);
//				System.out.println(closedNodes.size());

			openNodes.remove(0);

			if(current.gotThere()){
				
//				System.out.println("ACABOU");
				return current.getPath();
			} else {
				this.openNeighbours(current);
			}
		}
		return null;
	}
	
	/*
	 * Abre os nodos vizinhos no mapa e insere no array de NodosAbertos
	 */
	
	private void openNeighbours(Node n){

		Point position = n.getPoint();

		/*
		 * 		X
		 *  0		0
		 * 		0
		 */
		if((position.x - 1) >= 0){
			Point above = new Point(position.x - 1, position.y);
			
			if(map[position.x - 1][position.y] != OBSTACLE ){
				this.insertInArray(new Node(above,goal, n));
//				System.out.println(" Foi aberto o nodo above" + "(" + above.x + "," + above.y + ")");
			}
		}

		/*
		 * 		0
		 *  0		0
		 * 		X
		 */
		if((position.x + 1) < MAPSIZE){
			Point below = new Point(position.x + 1, position.y);
			
			if(map[position.x + 1][position.y] != OBSTACLE){
				this.insertInArray(new Node(below,goal, n));
//				System.out.println(" Foi aberto o nodo below" + "(" + below.x + "," + below.y + ")");
			}
		}

		/*
		 * 		0
		 *  X		0
		 * 		0
		 */
		if((position.y - 1) >= 0){
			Point left = new Point(position.x, position.y - 1);

			if(map[position.x][position.y - 1] != OBSTACLE){
				this.insertInArray(new Node(left,goal, n));
//				System.out.println(" Foi aberto o nodo left" + "(" + left.x + "," + left.y + ")");
			}
		}

		/*
		 * 		0
		 *  0		X
		 * 		0
		 */
		if((position.y + 1) < MAPSIZE){
			Point right = new Point(position.x, position.y + 1);

			if(map[position.x][position.y + 1] != OBSTACLE){
				this.insertInArray(new Node(right,goal, n));
//				System.out.println(" Foi aberto o nodo right" + "(" + right.x + "," + right.y + ")");
			}
		}
	}

//	private boolean isInClosedList(Point p){
//		for(int i = 0; i < closedNodes.size(); i++){
////			System.out.println(n.getPoint().x + " " + n.getPoint().y);
////			System.out.println(p.x + " " + p.y);
//			if(p.equals(closedNodes.get(i).getPoint())){
////				System.out.println("TRUE");
//				return true;
//			}
//		}
//		return false;
//	}

	private void insertInArray(Node n){
		if(openNodes.size() == 0){
			openNodes.add(n);
		} else {
			for(int i = 0; i < openNodes.size(); i++){
				if (openNodes.get(i).getCost() > n.getCost()){
					openNodes.add(i, n);
					return;
				}
			}
			openNodes.add(n);
		}
	}
}
