package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> data;
    private int idCount;

    public InMemoryTimeEntryRepository() {

        data = new HashMap<>();
        idCount = 0;
    }

    public TimeEntry find(long timeEntryId){

        return data.get(timeEntryId);
    }

    public void delete(long timeEntryId){

        data.remove(timeEntryId);
    }

    public TimeEntry create(TimeEntry timeEntry){

        if(timeEntry.getId() == 0)
            timeEntry.setTimeEntryId(++idCount);

        data.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry update(long timeEntryId, TimeEntry newTimeEntry){

        boolean exists = data.containsKey(timeEntryId);
        if(!exists)
            return null;

        newTimeEntry.setTimeEntryId(timeEntryId);
        data.put(timeEntryId, newTimeEntry);

        return newTimeEntry;
    }

    public List<TimeEntry> list(){

        return new ArrayList<>(data.values());
    }
}
