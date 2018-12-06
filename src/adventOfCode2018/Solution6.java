package adventOfCode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution6 {

	public static void main(String[] args) {
		String fileName = "input/input6.txt";
		List<Area> areas = new ArrayList<>();
		int size = 500;
		int distanceThreshold = 10000;
	    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    	AtomicInteger index = new AtomicInteger();
	    	areas = stream.map(s -> {
	    		int x = Integer.parseInt(s.substring(0, s.lastIndexOf(",")));
	    		int y = Integer.parseInt(s.substring(s.lastIndexOf(",")+2));
	    		
	    		return new Area(index.getAndIncrement(), x,y);
	    	}).collect(Collectors.toList());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    boolean[][] points = new boolean[size][size];
	      
	    for(int x = 0; x < size; x++) {
	    	for(int y = 0; y < size; y++) {
	    		int distanceSum = 0;
	    		int minDistance = Integer.MAX_VALUE;
	    		List<Area> selectedAreas = new ArrayList<>();
	    		for(Area area : areas) {
	    			int distance = Math.abs(area.getX() - x) + Math.abs(area.getY() - y);
	    			distanceSum += distance;
	    			if(distanceSum >= distanceThreshold) {
	    				points[x][y] = true;
	    			}
	    			if(distance < minDistance) {
	    				minDistance = distance;
	    				selectedAreas.clear();
	    				selectedAreas.add(area);
	    			} else if(distance == minDistance) {
	    				selectedAreas.add(area);
	    			}
	    		}
	    		if(selectedAreas.size() == 1) {
			    	selectedAreas.get(0).increaseSize();
			    	if(x == 0 || y == 0 || x == size-1 || y == size-1) {
			    		selectedAreas.get(0).setInfinite(true);
			    	}
	    		}

	    	}
	    }
	    
	    int max = Integer.MIN_VALUE;
	    for(Area area : areas) {
	    	if(!area.isInfinite() && max < area.getSize()) {
	    		max = area.getSize();
	    	}
	    }
	    System.out.println("Part 1: "+max);
	    
	    int area2 = 0;
	    for(int x = 0; x < size; x++) {
	    	for(int y = 0; y < size; y++) {
	    		if(!points[x][y]) {
	    			area2++;
	    		}
	    	}
	    }
	    
	    System.out.println("Part 2: "+area2);
	}
	
	private static class Area {
		int id = 0;
		int x = 0;
		int y = 0;
		int size = 0;
		boolean infinite = false;
		
		public void increaseSize() {
			size++;
		}
		
		public Area(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public int getId() {
			return this.id;
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

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public boolean isInfinite() {
			return infinite;
		}

		public void setInfinite(boolean infinite) {
			this.infinite = infinite;
		}
		
		
	}

}
