package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry find(long timeEntryId);
    void delete(long timeEntryId);
    TimeEntry create(TimeEntry timeEntry);
    TimeEntry update(long timeEntryId, TimeEntry newTimeEntry);
    List<TimeEntry> list();

}
