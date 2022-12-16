package model;

import java.time.LocalDateTime;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Task implements Cloneable, Serializable {
	private static final long serialVersionUID = 4669501028180213212L;
	
	private String uid;
	private int level;
	private int[] path;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Task() {
		this("Task");
	}
	
	public Task(Task that) {
		this.uid = UUID.randomUUID().toString();
		this.level = 0;
		this.path = new int[0];
		this.name = that.name;
		this.startDate = LocalDate.from(that.startDate);
		this.endDate = LocalDate.from(that.endDate);
	}
	
	public Task(String name) {
		this(name, LocalDate.now(), LocalDate.now().plusDays(1));
	}
	
	public Task(String name, LocalDate startDate, LocalDate endDate) {
		this.level = 0;
		this.path = new int[0];
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override 
	public boolean equals(Object o) {
		if(!(o instanceof Task))
			return false;
		
		Task that = (Task) o;
		
		if(!this.name.trim().equals(that.name.trim()))
			return false;
		
		if(!this.startDate.isEqual(that.startDate))
			return false;
		
		if(!this.endDate.isEqual(that.endDate))
			return false;
		
//		if(!(this.level == that.level))
//			return false;
		
		return true;
	}
	
	/**
	 * Returns a task with the same name, start date and end date as this one. <br>
	 * Important: the children list is NOT copied. 
	 * 
	 */
	@Override
	public Task clone() {
		return new Task(name, LocalDate.from(startDate), LocalDate.from(endDate));
	}
	

	public boolean isRoot() {
		return level == 0;
	}
	
	/**
	 * Returns the number of children this node has.
	 * @return Equivalent to children.size();
	 */

	
	public int[] getPath() {
		return path;
	}
	
	public boolean pathEquals(int[] that) {
		return Arrays.equals(this.path, that);
	}
	

	
	
//	public GanttTask removeChild(GanttTask task) {
//		return removeChild(children.indexOf(task));
//	}
	
//	public GanttTask removeChild(int[] path) {
//		return removeChild(path, null);
//	}
	
	/**
	 * Removes the child task at the given path starting from this node. 
	 * 
	 * @param path
	 * @return
	 */

	public boolean isDueToday() {
		if(endDate.compareTo(LocalDate.now()) == 0)
			return true;
		return false;
	}
	
	public boolean isOverdue() {
		if(endDate.compareTo(LocalDate.now()) < 0)
			return true;
		return false;
	}
	
	public String getUid() {
		return uid;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
}