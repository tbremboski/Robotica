package Robot;

import java.util.List;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

import java.util.ArrayList;

public class NxtMovement implements Robot {
	
	private UltrasonicSensor sensor;
	private float sizeRobot = 30f;
	
	
	// Inicializa o sensor ultrasonico
	public NxtMovement(){
		 this.sensor = new UltrasonicSensor(SensorPort.S4);
		
	}
	
	// Movimenta-se exatamente o tamanho do robo a frente
	@Override
	public void moveForward(){
		this.resetMotors();
//		Thread.sleep(1000);
		//Motor.B.rotateTo(720,true);
		//Motor.C.rotateTo(720,true);

		DifferentialPilot a = new DifferentialPilot(2.9f,13.5f,Motor.B,Motor.C);
		a.setTravelSpeed(20);
		a.travel(this.sizeRobot);
		//System.out.println(a.getMovementIncrement());
		//while(Motor.C.isMoving() && Motor.B.isMoving());
		this.resetMotors();
//		Thread.sleep(1000);
	}

	// Gira sobre proprio eixo para a direita
	@Override
	public void moveRight() {
		this.resetMotors();
		DifferentialPilot a = new DifferentialPilot(2.9f,13.5f,Motor.B,Motor.C);
//		System.out.println("Direita");
		
		//23 ideal
		a.setRotateSpeed(50);
		a.rotate(90);
//		Thread.sleep(1000);
		
		
		/*
		//a.rotate(90f);
		//Motor.B.rotateTo(-440,true);
		
		//Motor.C.rotateTo(440,true);
		//while(Motor.C.isMoving() && Motor.B.isMoving());
		this.resetMotors();
		Thread.sleep(1000);
		*/
		this.resetMotors();
	}

	
	// Gira sobre proprio eixo para a esquerda
	@Override
	public void moveLeft() {
		this.resetMotors();
//		System.out.println("Esquerda");

		//Motor.B.setSpeed(600);
		//Motor.C.setSpeed(600);
		/*Thread.sleep(1000);
		Motor.B.rotateTo(440,true);
		
		Motor.C.rotateTo(-440,true);
		while(Motor.C.isMoving() && Motor.B.isMoving());*/
		DifferentialPilot a = new DifferentialPilot(2.9f,13.5f,Motor.B,Motor.C);
		
		//23 ideal
		a.setRotateSpeed(50);
		a.rotate(-90);
//		Thread.sleep(1000);
		
		this.resetMotors();
//		Thread.sleep(1000);
		
	}

	// Somente reseta os buffers dos rotores
	private void resetMotors(){
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		Motor.C.resetTachoCount();
		
	}
	
	//Observa a direita, retorna um valor inteiro da distancia entre o robo e o obstaculo encontrado
	private int lookRight() {
		int distance = 0;
		
		Motor.A.rotate(-90);
		Delay.msDelay(500);
		distance = this.sensor.getDistance();
		
		Delay.msDelay(500);
		Motor.A.rotate(90);
		this.resetMotors();
		
		if(distance==255)return -1;
		double retr = (double) distance/((double)this.sizeRobot);
		return (int) (Math.ceil(retr));
	}

	//Observa a esquerda, retorna um valor inteiro da distancia entre o robo e o obstaculo encontrado
	private int lookLeft() {
		int distance = 0;
		
		Motor.A.rotate(90);
		Delay.msDelay(500);
		distance = this.sensor.getDistance();
		
		Delay.msDelay(500);
		Motor.A.rotate(-90);
		this.resetMotors();
		
		if(distance == 255)return -1;
		double retr = (double) distance/((double)this.sizeRobot);
		return (int) (Math.ceil(retr));
		
	}

	//Observa a frente, retorna um valor inteiro da distancia entre o robo e o obstaculo encontrado
	private int lookFoward() {
		int distance = 0;
		distance = this.sensor.getDistance();
		
		Delay.msDelay(500);
		
		if(distance==255)return -1;
		
		double retr = (double) distance/((double)this.sizeRobot);
		return (int) (Math.ceil(retr));
		
	}
	
	// Procura o objetivo em frente.
	public int searchGoal(){
		return this.lookFoward();
	}

	// Responsavel por reconhecer o ambiente a volta
	// Retorna uma Lista com 3 valores na ordem
	// 0 = Frente
	// 1 = Esquerda
	// 2 = Direita
	// O valor retornado consistem um inteiro com a distancia do obstaculo em relacao ao robo
	public List<Integer> lookAround(){
		List<Integer> look = new ArrayList<Integer>();
		look.add(0, this.lookRight());
		look.add(0, this.lookLeft());
		look.add(0, this.lookFoward());
		
		this.resetMotors();
		
		return look;
	}
	
	//Retorna um valor Booleano caso tenha um obstaculo logo a frente
	public boolean hasObstacleAhead(){
		int distance = this.lookFoward();
		return ((distance <= 1) &&(distance !=-1)) ?  true : false;

	}
//	
//	//NAO ESQUECER DE RETIRAR
//	public static void main(String args[]) throws InterruptedException{
//		NxtMovement a = new NxtMovement();
//		
//
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		Thread.sleep(1000);
//		System.out.println(a.hasObstacleAhead());
//		
//		/*System.out.println(a.lookAround());
//		a.moveRight();
//		System.out.println(a.lookAround());
//		a.moveRight();
//		System.out.println(a.lookAround());
//		a.moveFoward();
//		System.out.println(a.lookAround());
//		a.moveFoward();
//		System.out.println(a.lookAround());
//		a.moveLeft();
//		System.out.println(a.lookAround());
//		a.moveLeft();
//		System.out.println(a.lookAround());
//		a.moveFoward();
//		System.out.println(a.lookAround());
//		*/
//	}
}
