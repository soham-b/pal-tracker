package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Repository
public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate template;
    private static final String TABLE_NAME="time_entries";
    private static final String LIST_QUERY ="SELECT * FROM "+TABLE_NAME;
    private static final String CREATE_QUERY="INSERT INTO " + TABLE_NAME + " (project_id, user_id, date, hours) VALUES(?, ?, ?, ?)";
    private static final String FIND_QUERY= "SELECT * FROM "+TABLE_NAME+ " WHERE id= ?";
    private static final String UPDATE_QUERY = "UPDATE "+TABLE_NAME+ " SET project_id=?, user_id=?, date=?, hours=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

    public JdbcTimeEntryRepository(DataSource dataSource) {

        template = new JdbcTemplate(dataSource);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        List<TimeEntry> found = template.query(FIND_QUERY, new TimeEntryMapper(),timeEntryId);
        if(found.isEmpty())
            return null;
        return found.get(0);
    }

    @Override
    public void delete(long timeEntryId) {

        template.update(con -> {
            PreparedStatement ps = con
                    .prepareStatement(DELETE_QUERY);
            ps.setLong(1, timeEntryId);
            return ps;
        });
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con
                    .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setDate(3, Date.valueOf(timeEntry.getDate()));
            ps.setLong(4, timeEntry.getHours());
            return ps;
        }, keyHolder);

        return find(keyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry newTimeEntry) {

        template.update(con -> {
            PreparedStatement ps = con
                    .prepareStatement(UPDATE_QUERY);
            ps.setLong(1, newTimeEntry.getProjectId());
            ps.setLong(2, newTimeEntry.getUserId());
            ps.setDate(3, Date.valueOf(newTimeEntry.getDate()));
            ps.setLong(4, newTimeEntry.getHours());
            ps.setLong(5, timeEntryId);
            return ps;
        });

        return find(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return template.query(LIST_QUERY, new TimeEntryMapper());
    }

    private class TimeEntryMapper implements RowMapper<TimeEntry>{

        @Override
        public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
//            if(rs == null || rs.wasNull())
//                return null;

            TimeEntry timeEntry = new TimeEntry(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getDate(4).toLocalDate(), rs.getInt(5));
            return timeEntry;
        }
    }
}
