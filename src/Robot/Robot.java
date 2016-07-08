package Robot;

import java.util.List;

public interface Robot {
	
	//Movement methods
	public void moveForward();
	public void moveRight();
	public void moveLeft();
	
	
	
	//Sensor
	public List<Integer> lookAround();
	public boolean hasObstacleAhead();
	public int searchGoal();
}
