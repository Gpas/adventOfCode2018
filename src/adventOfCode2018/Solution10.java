package adventOfCode2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Solution10 {

	public static void main(String[] args) {
		String fileName = "input/input10.txt";
		List<Star> stars = new ArrayList<>();
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    	stream.forEach(s -> {
	    		int x = Integer.parseInt(s.substring(s.indexOf("=<")+2, s.indexOf(", ")).trim());
	    		int y = Integer.parseInt(s.substring(s.indexOf(", ")+2, s.indexOf(">")).trim());
	    		int velocityX = Integer.parseInt(s.substring(s.lastIndexOf("=<")+2, s.lastIndexOf(", ")).trim());
	    		int velocityY = Integer.parseInt(s.substring(s.lastIndexOf(", ")+2, s.lastIndexOf(">")).trim());
	    		stars.add(new Star(x,y,velocityX,velocityY));
	    	});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int height = Integer.MAX_VALUE-1;
		int lastHeight = Integer.MAX_VALUE;
		int counter = 0;
		while(height < lastHeight) {
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;
			lastHeight = height;
			for(Star star : stars) {
				star.move();
				if(star.getY() <= minY) {
					minY = star.getY();
				}
				if(star.getY() >= maxY) {
					maxY = star.getY();
				}
			}
			height = Math.abs(maxY - minY);
			counter++;
		}
		for(Star star : stars) {
			star.moveBackwards();
			star.moveBackwards();
		}
		counter--;
		
		int minX = 0;
		int maxX = 0;
		int minY = 0;
		int maxY = 0;
		for(Star star : stars) {
			star.move();
			if(star.getY() <= minY) {
				minY = star.getY();
			}
			if(star.getY() >= maxY) {
				maxY = star.getY();
			}
			if(star.getX() <= minX) {
				minX = star.getX();
			}
			if(star.getX() >= maxX) {
				maxX = star.getX();
			}
		}
		char[][] image = new char[maxY - minY + 1][maxX - minX + 1];
		
		for(Star star : stars) {
			image[star.getY()][star.getX()] = '#';
		}
		
		for(char[] row : image) {
			for(char c : row) {
				System.out.print(c == '#' ? '#' : ' ');
			}
			System.out.println();
		}
		
		System.out.println("Part 2: "+counter);
	}

	private static class Star {
		int x;
		int y;
		int velocityX;
		int velocityY;
		
		public Star(int x, int y, int velocityX, int velocityY) {
			super();
			this.x = x;
			this.y = y;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
		}
		
		public void move() {
			this.x += velocityX;
			this.y += velocityY;
		}
		
		public void moveBackwards() {
			this.x -= velocityX;
			this.y -= velocityY;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getVelocityX() {
			return velocityX;
		}

		public void setVelocityX(int velocityX) {
			this.velocityX = velocityX;
		}

		public int getVelocityY() {
			return velocityY;
		}

		public void setVelocityY(int velocityY) {
			this.velocityY = velocityY;
		}
		
		
	}
}


