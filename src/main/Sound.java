package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[5];
	
	public Sound() {
		soundURL[0] =  getClass().getResource("/sound/wing.wav");
		soundURL[1] =  getClass().getResource("/sound/point.wav");
		soundURL[2] =  getClass().getResource("/sound/hit.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
			
}
