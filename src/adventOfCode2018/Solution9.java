package adventOfCode2018;

import java.util.stream.LongStream;

public class Solution9 {

	public static void main(String[] args) {
		int players = 429;
		// Part 1 
		//long lastMarbleValue = 70901;
		// Part 2 
		long lastMarbleValue = 7090100;
		
		long[] playersScore = new long[players];
		
		int currentMarbleValue = 0;
		Marble currentMarble = new Marble(currentMarbleValue++);
		while(currentMarbleValue <= lastMarbleValue) {
			for(int i = 0; i < players; i++) {
				if(currentMarbleValue % 23 == 0) {
					//Special Case
					playersScore[i] += currentMarbleValue;
					for(int j = 0; j < 7; j++) {
						currentMarble = currentMarble.getCounterClockwise();
					}
					playersScore[i] += currentMarble.getValue();
					currentMarble = currentMarble.remove();
				} else {
					Marble oneClockwise = currentMarble.getClockwise();
					Marble twoClockwise = oneClockwise.getClockwise();
					currentMarble = new Marble(currentMarbleValue, twoClockwise, oneClockwise);
				}
				currentMarbleValue++;
				if(currentMarbleValue > lastMarbleValue) {
					break;
				}
			}
		}
		long highScore = LongStream.of(playersScore).max().getAsLong();
		System.out.println("Highscore: " +highScore);
	}
	
	private static class Marble{
		long value = 0;
		Marble clockwise;
		Marble counterClockwise;
		
		public Marble(long value) {
			super();
			this.value = value;
			this.clockwise = this;
			this.counterClockwise = this;
		}
		
		public Marble(long value, Marble clockwise, Marble counterClockwise) {
			super();
			this.value = value;
			this.clockwise = clockwise;
			this.counterClockwise = counterClockwise;
			clockwise.setCounterClockwise(this);
			counterClockwise.setClockwise(this);
		}
		
		public long getValue() {
			return value;
		}
		public Marble getClockwise() {
			return clockwise;
		}
		public void setClockwise(Marble clockwise) {
			this.clockwise = clockwise;
		}
		public Marble getCounterClockwise() {
			return counterClockwise;
		}
		public void setCounterClockwise(Marble counterClockwise) {
			this.counterClockwise = counterClockwise;
		}
		
		public Marble remove() {
			clockwise.setCounterClockwise(counterClockwise);
			counterClockwise.setClockwise(clockwise);
			return clockwise;
		}
		
		
	}

}
