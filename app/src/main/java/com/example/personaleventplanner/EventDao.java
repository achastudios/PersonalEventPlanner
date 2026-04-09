package com.example.personaleventplanner;

// These imports are for Room database actions
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// This DAO handles database operations for events
@Dao
public interface EventDao {

    // This inserts a new event
    @Insert
    void insert(Event event);

    // This updates an existing event
    @Update
    void update(Event event);

    // This deletes an event
    @Delete
    void delete(Event event);

    // This gets all events sorted by date and time
    @Query("SELECT * FROM event_table ORDER BY dateTime ASC")
    List<Event> getAllEvents();
}