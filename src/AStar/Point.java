package AStar;

public class Point {

	public int x, y;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean equals(Point p){
		if(this.x == p.x && this.y == p.y)
			return true;
		else
			return false;
	}
}
