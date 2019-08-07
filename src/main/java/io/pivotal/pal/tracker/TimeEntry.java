package io.pivotal.pal.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TimeEntry {

    public void setTimeEntryId(long timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    @JsonProperty("id")
    private long timeEntryId;

    private long projectId;
    private long userId;

    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("hours")
    private int hours;

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate date, int hours) {
        this.timeEntryId = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        TimeEntry timeEntry = (TimeEntry) obj;
        return timeEntryId==timeEntry.getId()
                && projectId == timeEntry.getProjectId()
                && userId == timeEntry.getUserId()
                && date.isEqual(timeEntry.getDate()) && hours == timeEntry.getHours();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {

        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(){}

    public long getId() {
        return timeEntryId;
    }
}
