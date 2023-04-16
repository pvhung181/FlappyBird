package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public BufferedImage up, nor; 
	public String dir; // trạng thái của player
	public int uSpeed = 10, dSpeed = 7;
	public boolean gameOver = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		collis = new Rectangle(0,0,48, 40);
		setDefault();
		getPlayerImage();
	}
	
	public void setDefault() {
		x = 200;
		y = 300;
		dir = "normal";
	}
	
	public void resetPlayer() {
		x = 200;
		y = 300;
		dir = "normal";
		gameOver = false;
		uSpeed = 10;
	}
	
	
	public void getPlayerImage()  {	
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/entity/bird_up.png"));
			nor = ImageIO.read(getClass().getResourceAsStream("/entity/bird_normal.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		if(keyH.space == true) {
			y -= uSpeed; 
			if(gameOver == false) dir = "up";
		}
		else {
			y += dSpeed;
			dir = "normal";
		}
	}
	
	public void draw(Graphics g2) {
		BufferedImage image = null;
		if(dir == "up") image = up;
		else image = nor;
		g2.drawImage(image, x, y, collis.width,collis.height , null);
	}
}
