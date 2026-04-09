package com.example.personaleventplanner;

// This import is needed for context
import android.content.Context;

// These imports are needed for Room
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// This creates the Room database
@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // This links the database to the DAO
    public abstract EventDao eventDao();

    // This stores one database instance
    private static AppDatabase instance;

    // This method creates and returns the database instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "event_database"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}