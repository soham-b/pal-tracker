package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        repository = timeEntryRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TimeEntry timeEntryToCreate) {

        return new ResponseEntity<TimeEntry>(repository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {

        TimeEntry timeEntry = repository.find(timeEntryId);
        if(timeEntry == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(timeEntry);
    }

    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {

        return ResponseEntity.ok(repository.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = repository.update(timeEntryId, expected);
        if(timeEntry == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(timeEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long timeEntryId) {

        repository.delete(timeEntryId);
        return ResponseEntity.noContent().build();
    }
}
