package renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Pipe;
import main.GamePanel;

public class Renderer {
	
	GamePanel gp;
	public BufferedImage p1, p2;
	public Pipe[] upp, low;
	public int speed = 3;
	
	public BufferedImage background;
	public BufferedImage [] ground;
	
	public int distance = 300; // Khoảng cách giữa 2 cột

	static int currentGround = 1; // dùng để hiển thị ảnh ground
	public int heightGround;
	
	public int begin = 0, end = 1;
	public int curr = 0 ; 
	
	public final int gap = 180; // Khoảng cách giữa cột trên và dưới 
	
	public Renderer(GamePanel gp) {
		this.gp = gp;
		ground = new BufferedImage[13];
		getBackgroundImage();
		getPipe();
		heightGround = ground[1].getHeight();
	}
	
	public void getBackgroundImage() {
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/background/background.png"));
			
			ground[1] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_1.png"));
			ground[2] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_2.png"));
			ground[3] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_3.png"));
			ground[4] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_4.png"));
			ground[5] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_5.png"));
			ground[6] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_6.png"));
			ground[7] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_7.png"));
			ground[8] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_8.png"));
			ground[9] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_9.png"));
			ground[10] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_10.png"));
			ground[11] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_11.png"));
			ground[12] = ImageIO.read(getClass().getResourceAsStream("/background/Ground_12.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int random() {
		return (int) ((Math.random() * (-50 +300) - 300)); 
	}	
	
	public void getPipe() {
		try {
			p1 = ImageIO.read(getClass().getResourceAsStream("/entity/pipe_1_rs.png"));
			p2 = ImageIO.read(getClass().getResourceAsStream("/entity/pipe_2_rs.png"));
			
			upp = new Pipe[1005];
			low = new Pipe[1005];
			for(int i = 0; i < 1005; i++) {
				int randomHeight = random();
				
				upp[i] = new Pipe(gp.SCREEN_WIDTH, randomHeight);
				low[i] = new Pipe(gp.SCREEN_WIDTH, upp[i].y + upp[i].h + gap);
				upp[i].img = p1;
				low[i].img = p2;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
	}
}
	
	public void reset() {
		curr = 0; begin = 0; end = 1;
		for(int i = 0; i < 1005; i++) {
			Pipe upper = upp[i], lower = low[i];
			int randomHeight = random();
			
			upper.setXY(gp.SCREEN_WIDTH, randomHeight);
			lower.setXY(gp.SCREEN_WIDTH, upper.y + upper.h + gap);
			lower.isPass = false;
		}
	}

	public void update() {
		if(gp.SCREEN_WIDTH - upp[end - 1].x >= distance) end++;
		
		if(upp[begin].x < -100) begin++; 
		for(int j = begin;j < end; j++) {
			upp[j].x -= speed;
			low[j].x -= speed;
		}
		
		Pipe upper = upp[curr];
		if(upper.x + upper.collis.x + upper.collis.width <= 200) curr++;
		
		currentGround++; 
		if(currentGround == 13) currentGround = 1;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null); 
		
		for(int i = begin;i < end; i++) {
			Pipe upper = upp[i], lower = low[i];
			g2.drawImage(upper.img, upper.x, upper.y, upper.w, upper.h, null );
			g2.drawImage(lower.img, lower.x,lower.y, upper.w, lower.h, null ); 
		}
	
		g2.drawImage(ground[currentGround], 0, gp.SCREEN_HEIGHT - heightGround, gp.SCREEN_WIDTH,heightGround, null);
		
	}
}
