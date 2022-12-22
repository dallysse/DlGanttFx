package model;

import java.time.LocalDateTime;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class GanttTask implements Cloneable{
	
	private String id;
	private int level;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double workComplete;
	private String info;
	private boolean isCritical;


	
	public GanttTask(GanttTask that) {
		this.id = UUID.randomUUID().toString();
		this.level = 0;
		this.name = that.name;
		this.startDate = LocalDate.from(that.startDate);
		this.endDate = LocalDate.from(that.endDate);
	}
	

	
	public GanttTask(String name, LocalDate startDate, LocalDate endDate, int level, boolean isCritical,  double workComplete, String info) {
		this.level = level;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCritical= isCritical;
		this.workComplete=workComplete;
		this.info=info;
	}

	
	@Override 
	public boolean equals(Object o) {
		if(!(o instanceof GanttTask))
			return false;
		
		GanttTask that = (GanttTask) o;
		
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
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getWorkComplete() {
		return workComplete;
	}

	public void setWorkComplete(Double workComplete) {
		this.workComplete = workComplete;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean getIsCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

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
		return id;
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