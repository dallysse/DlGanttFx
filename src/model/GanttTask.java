package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;



public class GanttTask implements Cloneable, Serializable{
	private static final long serialVersionUID = 4669501028180213212L;

	
	private String id;
	private int priority;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double workComplete;
	private String info;
	private boolean isCritical;

	public GanttTask() {}
	
	public GanttTask(GanttTask that) {
		this.id = UUID.randomUUID().toString();
		this.priority = 3;
		this.name = that.name;
		this.startDate = LocalDate.from(that.startDate);
		this.endDate = LocalDate.from(that.endDate);
	}
	
	public GanttTask(String name, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	public GanttTask(String name, LocalDate startDate, LocalDate endDate, int level, boolean isCritical, String info) {
		this.priority = level;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCritical = isCritical;
		this.workComplete = ( startDate == null || endDate == null ) ? 0.0 :
		(LocalDate.now().isBefore(startDate) ? 0.0 :
		(double) Duration.between( startDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() / (double) Duration.between( startDate.atStartOfDay(), endDate.atStartOfDay()).toDays()) ;
		this.info=info;
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
		return priority;
	}

	public void setLevel(int level) {
		this.priority = level;
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