package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pipe extends Entity{
	
	public BufferedImage img;
	public boolean isPass = false;
	
	public Pipe(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = 65;
		this.h = 400;
		collis = new Rectangle(4, 13, 55, 370);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
