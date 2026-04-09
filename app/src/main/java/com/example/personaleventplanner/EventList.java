package com.example.personaleventplanner;

// These imports are needed for fragment code
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// These imports are for fragments and RecyclerView
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// This fragment shows the saved events
public class EventList extends Fragment {

    // This stores the RecyclerView
    RecyclerView recycler_view;

    // This stores the Room database
    AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // This connects the fragment to the XML layout
        View view = inflater.inflate(R.layout.event_list, container, false);

        // This connects the RecyclerView to the XML id
        recycler_view = view.findViewById(R.id.recycler_view);

        // This sets the layout manager
        recycler_view.setLayoutManager(new LinearLayoutManager(requireContext()));

        // This gets the database instance
        database = AppDatabase.getInstance(requireContext());

        // This gets all saved events from Room
        List<Event> eventList = database.eventDao().getAllEvents();

        // This creates the adapter
        EventAdapter adapter = new EventAdapter(requireContext(), eventList, database);

        // This connects the adapter to the RecyclerView
        recycler_view.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // This refreshes the list whenever the user comes back to this fragment
        if (recycler_view != null) {
            List<Event> eventList = database.eventDao().getAllEvents();
            EventAdapter adapter = new EventAdapter(requireContext(), eventList, database);
            recycler_view.setAdapter(adapter);
        }
    }
}