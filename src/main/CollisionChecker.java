package main;

import entity.Entity;

public class CollisionChecker {
	
	public boolean detect(Entity b, Entity p) {
		int birdLeftX = b.x;
		int birdRightX = birdLeftX + b.collis.width;
		int birdTopY = b.y;
		int birdBottomY = birdTopY + b.collis.height;
		
		int pipeLeftX = p.x + p.collis.x;
		int pipeRightX = pipeLeftX + p.collis.width;
		int pipeTopY = p.y + p.collis.y;
		
		if(birdRightX >= pipeLeftX && birdLeftX < pipeRightX) {
			if(birdBottomY >= pipeTopY || birdTopY <= pipeTopY - 180 - 2 * p.collis.y) return true; 
		}
		return false;
	}
}
