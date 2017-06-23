package server.element;

import java.io.Serializable;

public class Posizioni implements Serializable{
	
	private double x;
	private double y;
	private String name;
	public Posizioni(double double1, double double2, String string) {
		this.x=double1;
		this.y=double2;
		this.name=string;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
