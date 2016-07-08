package AStar;

import java.util.ArrayList;

public class Node {

	protected Point p;
	protected double h;
	protected double g;
	protected double f;
	
	protected ArrayList<Point> path;
	
	public Node(Point p, Point goal, Node n){
		this.p = new Point(p);
		h = Math.sqrt(Math.pow(goal.x-p.x, 2) + Math.pow(goal.y-p.y, 2));
		
		path = new ArrayList<Point>();
		if(n != null){
			this.addToPath(n.getPath());
		}
		path.add(p);
		g = path.size();
		f = h * g;
	}
	
//	public void addToPath(Point p){
//		path.add(p);
//	}
	
	public void addToPath(ArrayList<Point> ap){
		path.addAll(ap);
	}
		
	public ArrayList<Point> getPath(){
		return path;
	}
	
	public double getCost(){
		return f;
	}
	public boolean gotThere(){
		return f == 0;
	}
	
	public Point getPoint(){
		return p;
	}
	
	@Override
	public String toString(){
		return "(" + p.x + "," + p.y + ") h = " + h + " g = " + g + " f = " + f;
		 
	}
	
}