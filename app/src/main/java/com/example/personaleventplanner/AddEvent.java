package com.example.personaleventplanner;

// These imports are needed for fragment code
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// This fragment is used to add new events
public class AddEvent extends Fragment {

    // These variables connect to the XML views
    EditText title_input, location_input, date_input;
    Spinner dropdown_menu1;
    Button save_button;

    // This stores the database instance
    AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // This connects the Java file to the XML layout
        View view = inflater.inflate(R.layout.add_event, container, false);

        // These lines connect Java variables to XML ids
        title_input = view.findViewById(R.id.title_input);
        dropdown_menu1 = view.findViewById(R.id.dropdown_menu1);
        location_input = view.findViewById(R.id.location_input);
        date_input = view.findViewById(R.id.date_input);
        save_button = view.findViewById(R.id.save_button);

        // This gets the database
        database = AppDatabase.getInstance(requireContext());

        // This array stores the category names
        String[] categories = {"Work", "Social", "Travel"};

        // This creates the adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );

        // This sets how the dropdown looks
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // This connects the adapter to the spinner
        dropdown_menu1.setAdapter(adapter);

        // This runs when the save button is clicked
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // These lines get the user input
                String title = title_input.getText().toString().trim();
                String category = dropdown_menu1.getSelectedItem().toString();
                String location = location_input.getText().toString().trim();
                String dateTime = date_input.getText().toString().trim();

                // This checks if title is empty
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This checks if date is empty
                if (TextUtils.isEmpty(dateTime)) {
                    Toast.makeText(requireContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This checks if the date is in the past
                if (isPastDate(dateTime)) {
                    Toast.makeText(requireContext(), "Date cannot be in the past", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This creates the event object
                Event event = new Event(title, category, location, dateTime);

                // This saves the event into the Room database
                database.eventDao().insert(event);

                // This shows a success message
                Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();

                // These lines clear the inputs
                title_input.setText("");
                location_input.setText("");
                date_input.setText("");
            }
        });

        return view;
    }

    // This method checks if the entered date is in the past
    private boolean isPastDate(String inputDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date enteredDate = format.parse(inputDate);
            Date currentDate = new Date();
            return enteredDate.before(currentDate);
        } catch (Exception e) {
            return true;
        }
    }
}