package model;

import java.time.LocalDate;

public class GanttRessource {
    private String id;
	private String name;
	private String role;
    private LocalDate startDate;
	private LocalDate endDate;
    
    public GanttRessource(String name, String role, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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
