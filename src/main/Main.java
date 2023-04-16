package main;

import javax.swing.*;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Flappy Bird");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();    
		window.setLocationRelativeTo(null);
		window.setIconImage(new ImageIcon("res\\icon\\Flappy_Bird_icon.png").getImage());
		window.setVisible(true);
		
		gamePanel.startGame();
		
		
	}


}
