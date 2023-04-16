package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Pipe;
import entity.Player;
import renderer.Renderer;

public class GamePanel extends JPanel implements Runnable{

		private static final long serialVersionUID = 1L;
	
		// SCREEN SETTINGS
		public final int SCREEN_WIDTH = 864; // pixels
		public final int SCREEN_HEIGHT = 672;	// pixels
		public int 	SCREEN = 1; // 1 màn hình lúc mới bắt đầu game
								// 2 lúc chơi game
								// 3 lúc kết thúc
		
		//FPS
		int FPS = 60;
		
		//Renderer
		Renderer renderer = new Renderer(this);
		
		
		// Key Handler
		KeyHandler key = new KeyHandler();
		
		//Player
		Player player = new Player(this, key);
		
		//SCORE
		public static int score = 0;
		JLabel sc = new JLabel("Score : " + score);
		JButton rs = new JButton();
		
		//GAME OVER
		public boolean isOver = false;
		
		// COLLISION
		CollisionChecker checker = new CollisionChecker();
		
		// SOUND
		Sound sound = new Sound();
		boolean isTypeSpace = true;
		boolean isDead = false;
		
		//Thread
		Thread gameThread;
		
		
		
		public GamePanel() {
			this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
			this.setDoubleBuffered(true);
			this.addKeyListener(key);
			this.setFocusable(true);
			this.setLayout(null);
			
			
			this.add(rs);
			rs.setText("RESTART");
			rs.setSize(200,50);
			rs.setLocation(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 + 50);
			rs.setVisible(false);
			rs.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource() == rs) {
						reset();
						rs.setVisible(false);	
						requestFocus();
						sc.setText("Score : " + score);
					}
				}
			});
			this.add(sc);
			sc.setSize(200,50);
			sc.setFont(new Font("Serif", Font.PLAIN, 18));
			sc.setLocation(SCREEN_WIDTH / 2 - 50, 0);
			sc.setVisible(true);
		}
	
		
		public void startGame() {
			gameThread = new Thread(this);
			gameThread.start();
		}
		

		@Override
		// GAME LOOP
		public void run() {
			double drawInterval = 1e9 / FPS;
			double nextDraw = System.nanoTime() + drawInterval;
			
			while(gameThread != null) {
				update();
				isOver();
				repaint();
				updateScore();
				try {
					double remanning = nextDraw - System.nanoTime();
					remanning /= 1e6;
					if(remanning < 0) remanning = 0;
					Thread.sleep((long) remanning);
					nextDraw += drawInterval;     
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(key.space == true && SCREEN == 1) SCREEN = 2;

				if(isOver == true) {
					SCREEN = 3;
					rs.setVisible(true);
				}
					
				updateSound();
				
			}
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			renderer.draw(g2);
			player.draw(g2);
			if(SCREEN == 3) drawGameOver(g2);
		}
		
		public void update() {
			if(SCREEN == 2) {
				player.update();
				renderer.update();
			}
			
			if(SCREEN == 3 && player.y < SCREEN_HEIGHT - renderer.heightGround - player.collis.height) {
				player.uSpeed = -7;
				player.gameOver = true;
				player.update();
			}
		}
		
		public void drawGameOver(Graphics2D g2) {
			BufferedImage img = null;
			try   {img = ImageIO.read(getClass().getResourceAsStream("/background/gameover.png"));} 
			catch (IOException e) { e.printStackTrace();}
			
			g2.drawImage(img,(int)SCREEN_WIDTH / 2 - 200, (int)SCREEN_HEIGHT / 2 - 180, 400, 200, null);
		}
		
		public void isOver() {
			isOver = checker.detect(player, renderer.low[renderer.curr]);
			int positionOfPlayer = player.y + player.collis.height; 
			if(positionOfPlayer >= SCREEN_HEIGHT - renderer.heightGround) isOver = true ;
		}
		
		public void reset() {
			player.resetPlayer();
			renderer.reset();
			
			isOver = false;
			isDead = false;
			
			score = 0;
			SCREEN = 1;
		}
		
		public void updateScore() {
			Pipe currentPipe = renderer.low[renderer.curr];
			if(currentPipe.x + currentPipe.collis.x <= 248  && currentPipe.isPass == false) {
				playSound(1);
				score++;
				currentPipe.isPass = true;
			}
			sc.setText("Score : " + score);
		}

		public void updateSound() {
			if(isDead == false && SCREEN == 3) {
				playSound(2);
				isDead = true;
			}
			
			
			if(key.space == true && SCREEN == 2 && isTypeSpace == true) {
				playSound(0);
				isTypeSpace = false;
			}
			if(key.space == false && SCREEN == 2 && isTypeSpace == false) {
				isTypeSpace = true;
			}
		}
		
		public void playSound(int i) {
			sound.setFile(i);
			sound.play();
		}
		
		
}
