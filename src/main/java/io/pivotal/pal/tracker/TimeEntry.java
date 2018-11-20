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
    private LocalDate parse;
    @JsonProperty("hours")
    private int i;

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getParse() {
        return parse;
    }

    public int getI() {
        return i;
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate parse, int i) {
        this.timeEntryId = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.parse = parse;
        this.i = i;
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
                && parse.isEqual(timeEntry.getParse()) && i == timeEntry.getI();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public TimeEntry(long projectId, long userId, LocalDate parse, int i) {

        this.projectId = projectId;
        this.userId = userId;
        this.parse = parse;
        this.i = i;
    }

    public TimeEntry(){}

    public long getId() {
        return timeEntryId;
    }
}
