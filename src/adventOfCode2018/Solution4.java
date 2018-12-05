package adventOfCode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution4 {
	
	public static void main (String[] args) {
		String fileName = "input/input4.txt";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		List<Action> actions = new ArrayList<>();
		Map<Integer, Guard> guards = new HashMap<>();
	    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    	stream.forEach(
	    			input -> {
	    				int guardId = 0;
	    				String state = "";
	    				LocalDateTime dateTime;
	    				if(input.lastIndexOf("Guard #") != -1) {
	    					guardId = Integer.parseInt(input.substring(input.lastIndexOf("#")+1, input.lastIndexOf("begins shift")-1));
	    				}
	    				if(input.lastIndexOf("wakes") != -1) {
	    					state = "wake";
	    				}
	    				if(input.lastIndexOf("asleep") != -1) {
	    					state = "asleep";
	    				}
	    				if(input.lastIndexOf("begins shift") != -1) {
	    					state = "shift";
	    				}
	    				dateTime = LocalDateTime.parse(input.substring(input.lastIndexOf("[")+1, input.lastIndexOf("]")), format);
	    				actions.add(new Action(guardId, dateTime, state));
	    			});
	    	actions.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));
	    	
	    	Guard currentGuard = new Guard();
	    	int fellAsleepAt = 0;
	    	int wokeUpAt = 0;
	    	for(Action action : actions) {
	    		if(action.getState().equals("shift")) {
	    			if(guards.containsKey(action.getGuardId())) {
	    				currentGuard = guards.get(action.getGuardId());
	    			} else {
		    			currentGuard = new Guard(action.getGuardId());
		    			guards.put(currentGuard.getId(), currentGuard);
	    			}
	    		}
	    		if(action.getState().equals("asleep")) {
	    			fellAsleepAt = action.getDateTime().getMinute();
	    		}
	    		if(action.getState().equals("wake")) {
	    			wokeUpAt = action.getDateTime().getMinute();
	    			for(int i = fellAsleepAt; i < wokeUpAt; i++) {
	    				currentGuard.addSleepingMinute(i);
	    			}
	    		}
	    	}
	    	
	    	Guard bestGuardS1 = guards.values().stream().findFirst().get();
	    	Guard bestGuardS2 = guards.values().stream().findFirst().get();
	    	for(Guard guard : guards.values()) {
	    		bestGuardS1 = bestGuardS1.getSumOfSleep() < guard.getSumOfSleep() ? guard : bestGuardS1;
	    		bestGuardS2 = bestGuardS2.getMinuteTimeWithMostSleep() < guard.getMinuteTimeWithMostSleep() ? guard : bestGuardS2;
	    	}
	    	System.out.println("Part 1: "+bestGuardS1.getMinuteWithMostSleep() * bestGuardS1.getId());
	    	System.out.println("Part 2: "+bestGuardS2.getMinuteWithMostSleep() * bestGuardS2.getId());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class Guard {
		int[] minutes = new int[61];
		int id = 0;
		
		public int getMinuteTimeWithMostSleep() {
			return IntStream.of(minutes).max().getAsInt();
		}
		
		public int getMinuteWithMostSleep() {
			int max = Integer.MIN_VALUE;
			int bestMinute = 0;
			for(int i = 0; i < 61; i++) {
				if(max < minutes[i]) {
					max = minutes[i];
					bestMinute = i;
				}
			}
			return bestMinute;
		}
		
		public int getSumOfSleep() {
			return IntStream.of(minutes).sum();
		}
		
		public void addSleepingMinute(int minute) {
			minutes[minute]++;
		}
		
		public int[] getMinutes() {
			return minutes;
		}
		public void setMinutes(int[] minutes) {
			this.minutes = minutes;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public Guard() {
			
		}
		
		public Guard(int id) {
			super();
			this.id = id;
		}
		
		
		
	}
	
	private static class Action {
		int guardId;
		LocalDateTime dateTime;
		String state;
		
		public Action(int guardId, LocalDateTime dateTime, String state) {
			super();
			this.guardId = guardId;
			this.dateTime = dateTime;
			this.state = state;
		}

		public int getGuardId() {
			return guardId;
		}

		public void setGuardId(int guardId) {
			this.guardId = guardId;
		}

		public LocalDateTime getDateTime() {
			return dateTime;
		}

		public void setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
}
