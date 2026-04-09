package com.example.personaleventplanner;

// These imports are needed for Room
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This creates the event table in Room
@Entity(tableName = "event_table")
public class Event {

    // This is the id for each event
    @PrimaryKey(autoGenerate = true)
    public int id;

    // These are the event details
    public String title;
    public String category;
    public String location;
    public String dateTime;

    // This constructor sets the event values
    public Event(String title, String category, String location, String dateTime) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.dateTime = dateTime;
    }
}