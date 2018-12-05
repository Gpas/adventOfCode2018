package adventOfCode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class Solution5 {
	
	public static void main (String[] args) {
		  String fileName = "input/input5.txt";
		    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
		    	String input = stream.findFirst().get();
		    	Stream<String> stringStream = input.codePoints()
		    			  .mapToObj(c -> String.valueOf((char) c));
		    	BinaryOperator<String> function = ((c1, c2)
		    			-> {
		    				String lastChar = c1;
		    				if(c1.length() > 1) {
		    					lastChar = c1.substring(c1.length()-1);
		    				}

							if(lastChar.equalsIgnoreCase(c2) && getsDestroyed(lastChar, c2)) {
			    				if(c1.length() > 1) {
			    					c1 = c1.substring(0, c1.length()-1);
			    					return c1;
			    				}
			    				return "";
		    				} else {
		    					return c1 + c2;
		    				}
		    			});
		    	String output = stringStream.reduce(function).get();
		    	System.out.println("Part 1: "+output.length());
		    	
		    	int bestLength = Integer.MAX_VALUE;
		    	for(char alphabet = 'A'; alphabet <= 'Z';alphabet++) {
		    		char currentChar = alphabet;
			    	stringStream = input.codePoints()
			    			  .mapToObj(c -> String.valueOf((char) c));
		    	    int currentLength = stringStream.filter(s -> !s.toUpperCase().equals(String.valueOf(currentChar))).reduce(function).get().length();
		    	    bestLength = currentLength < bestLength ? currentLength : bestLength;
		    	}  

		    	System.out.println("Part 2: "+bestLength);
		    	
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static boolean getsDestroyed(String c1, String c2) {
		return (Character.isLowerCase(c1.toCharArray()[0]) && Character.isUpperCase(c2.toCharArray()[0]))
		|| (Character.isLowerCase(c2.toCharArray()[0]) && Character.isUpperCase(c1.toCharArray()[0]));
	}
}


