package adventOfCode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Solution7 {
	
	private static Map<String, Integer> idToSecond = new HashMap<>();
	private static final Integer BASETIME = 60;
	

	public static void main(String[] args) {
		Integer i = 1;
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
		    idToSecond.put(Character.toString(alphabet), i);
		    i++;
		}
		String fileName = "input/input7.txt";
		Map<String, Step> steps = new HashMap<>();
	    try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    	stream.forEach(s -> {
	    		String idCondition = s.substring(5, 6);
	    		String idStep = s.substring(s.lastIndexOf("step ")+5, s.lastIndexOf("step ")+6);
	    		Step step = steps.get(idStep);
	    		if(step == null) {
	    			step = new Step(idStep);
	    			steps.put(idStep, step);
	    		}
	    		step.addCondition(idCondition);
	    		Step stepCondition = steps.get(idCondition);
	    		if(stepCondition == null) {
	    			stepCondition = new Step(idCondition);
	    			steps.put(idCondition, stepCondition);
	    		}
	    	});
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //Part 1
	    List<Step> stepsSorted = steps.values().stream().collect(Collectors.toList());
	    stepsSorted.sort((s1, s2) -> {
	    	return s1.getId().compareTo(s2.getId());
	    });
	    
	    List<Step> stepsSorted2 = new ArrayList<>();
	    for(Step step : stepsSorted) {
	    	stepsSorted2.add(step.clone());
	    }

	    StringBuilder order = new StringBuilder();
	    
	    while(!stepsSorted.isEmpty()) {
    		Step toRemove = null;
	    	for(Step step : stepsSorted) {
				if(step.canStart()) {
					toRemove = step;
					break;
    			}
	    	}
	    	stepsSorted.remove(toRemove);
	    	order.append(toRemove.getId());
		    for(Step step : stepsSorted) {
		    	step.removeCondition(toRemove.getId());
		    }
	    }
	    
	    System.out.println("Part 1: "+order);
	    
	    //Part 2
	    Stack<Worker> workers = new Stack<>();
	    List<Worker> workerWorking = new ArrayList<>();
	    for(int wcount = 0; wcount < 5; wcount++) {
	    	workers.push(new Worker(wcount));
	    }
	    
	    int seconds = 0;
	    while(!stepsSorted2.isEmpty()) {
	    	for(int second = 0; second < 10000; second++) {
	    		workerWorking.forEach(worker -> {
	    			worker.subtractTime();
	    		});
	    		workerWorking.removeIf(
	    				w -> w.getWorkTime() == 0 && 
	    				w == workers.push(w) &&
	    				stepIsDone(w.getStepId(), stepsSorted2));
	    		Step toRemove = null;
	    		do {
	    			toRemove = null;
	    			for(Step step : stepsSorted2) {
	    				if(step.canStart() && !workers.empty()) {
	    					toRemove = step;
	    					break;
		    			}
	    			}
	    			if(toRemove != null) {
	    				Worker nextWorker = workers.pop();
	    				nextWorker.setWorkTime(idToSecond.get(toRemove.getId())+BASETIME);
	    				nextWorker.setStepId(toRemove.getId());
	    				workerWorking.add(nextWorker);
	    				stepsSorted2.remove(toRemove);
	    			}
	    		} while(toRemove != null);
	    		if(stepsSorted2.isEmpty() && workerWorking.isEmpty()) {
	    			seconds = second;
	    			break;
	    		}
	    	}
	    }
	    
	    System.out.println("Part 2: "+seconds);
	}
	
	private static boolean stepIsDone(String stepId, List<Step> stepsSorted) {
	    for(Step step : stepsSorted) {
	    	step.removeCondition(stepId);
	    }
	    return true;
	}
	
	private static class Worker {
		int workTime = 0;
		int id = 0;
		String stepId = "";
		public int getWorkTime() {
			return workTime;
		}
		public void setWorkTime(int workTime) {
			this.workTime = workTime;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Worker(int id) {
			super();
			this.id = id;
		}
		
		
		
		public String getStepId() {
			return stepId;
		}
		public void setStepId(String stepId) {
			this.stepId = stepId;
		}
		public void subtractTime() {
			workTime--;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Worker other = (Worker) obj;
			if (id != other.id)
				return false;
			return true;
		}
		
		
		
	}

	private static class Step {
		
		String id;
		Set<String> preconditions = new HashSet<>();
		
		public Step clone() {
			Step newStep = new Step(this.id);
			for(String condition : this.getPreconditions()) {
				newStep.addCondition(condition);
			}
			return newStep;
		}
		
		public Step(String id) {
			super();
			this.id = id;
		}
		
		public void addCondition(String step) {
			preconditions.add(step);
		}
		
		public void removeCondition(String step) {
			preconditions.remove(step);
		}
		
		public boolean canStart() {
			return preconditions.isEmpty();
		}
		
		

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Set<String> getPreconditions() {
			return preconditions;
		}

		public void setPreconditions(Set<String> preconditions) {
			this.preconditions = preconditions;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Step other = (Step) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		
		
		
	}
}


