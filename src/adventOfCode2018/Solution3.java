package adventOfCode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Solution3 {
	
	public static void main (String[] args) {
		String fileName = "input/input3.txt";
		int size = 1200;
	    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    	int[][] grid = new int[size][size];
	    	Map<Integer, Boolean> doesOverlap = new HashMap<>();
	    	stream.forEach(
	    			input -> {
	    				int id = Integer.parseInt(input.substring(input.lastIndexOf("#")+1, input.lastIndexOf("@")-1));
	    				int x = Integer.parseInt(input.substring(input.lastIndexOf("@")+2, input.lastIndexOf(",")));
	    				int y = Integer.parseInt(input.substring(input.lastIndexOf(",")+1, input.lastIndexOf(":")));
	    				int width = Integer.parseInt(input.substring(input.lastIndexOf(":")+2, input.lastIndexOf("x")));
	    				int height = Integer.parseInt(input.substring(input.lastIndexOf("x")+1));
	    				int limitX = x + width;
	    				int limitY = y + height;
	    				doesOverlap.put(id, false);
	    				for(; x < limitX; x++) {
	    					for(int yIter = y; yIter < limitY; yIter++) {
	    						if(grid[x][yIter] != 0) {
	    							doesOverlap.put(grid[x][yIter], true);
	    							doesOverlap.put(id, true);
	    							grid[x][yIter] = 99999;
	    						} else {
		    						grid[x][yIter] = id;
	    						}
	    					}
	    				}
	    			});
	    	int counter = 0;
	    	for(int x = 0; x < size; x++) {
		    	for(int y = 0; y < size; y++) {
		    		if(grid[x][y] == 99999) {
		    			counter++;
		    		}
		    	}
	    	}
	    	System.out.println("Part 1: "+counter);
	    	
	    	for(Entry<Integer, Boolean> entry : doesOverlap.entrySet()) {
	    		if(!entry.getValue()) {
	    			System.out.println("Part 2: "+entry.getKey());
	    		}
	    	}
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
