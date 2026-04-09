package com.example.personaleventplanner;

// These imports are for dialog boxes
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

// These imports are for views
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// These imports are for RecyclerView
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// This adapter shows the saved events
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    // This list stores all events
    List<Event> eventList;

    // This stores the context
    Context context;

    // This stores the database
    AppDatabase database;

    // This constructor receives the list, context, and database
    public EventAdapter(Context context, List<Event> eventList, AppDatabase database) {
        this.context = context;
        this.eventList = eventList;
        this.database = database;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // This loads event_item.xml for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        // This gets the current event
        Event event = eventList.get(position);

        // These lines show the event details
        holder.title_value.setText(event.title);
        holder.category_value.setText("Category: " + event.category);
        holder.location_value.setText("Location: " + event.location);
        holder.date_value.setText("Date: " + event.dateTime);

        // This runs when the edit button is clicked
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(event, holder.getAdapterPosition());
            }
        });

        // This runs when the delete button is clicked
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This deletes the event from Room
                database.eventDao().delete(event);

                // This removes the event from the list
                int currentPosition = holder.getAdapterPosition();

                // This checks that the position is valid before removing
                if (currentPosition != RecyclerView.NO_POSITION) {
                    eventList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                }

                // This shows a success message
                Toast.makeText(context, "Event deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        // This returns the total number of items
        return eventList.size();
    }

    // This method shows the edit dialog
    private void showEditDialog(Event event, int position) {

        // This creates the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // This sets the dialog title
        builder.setTitle("Edit Event");

        // This loads the custom dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_event, null);

        // These lines connect the dialog views
        EditText title_input = dialogView.findViewById(R.id.title_input);
        Spinner dropdown_menu1 = dialogView.findViewById(R.id.dropdown_menu1);
        EditText location_input = dialogView.findViewById(R.id.location_input);
        EditText date_input = dialogView.findViewById(R.id.date_input);

        // This array stores category options
        String[] categories = {"Work", "Social", "Travel"};

        // This creates the spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                categories
        );

        // This sets the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // This connects the adapter to the spinner
        dropdown_menu1.setAdapter(adapter);

        // These lines show the existing event values in the dialog
        title_input.setText(event.title);
        location_input.setText(event.location);
        date_input.setText(event.dateTime);

        // This sets the correct category in the spinner
        if (event.category.equals("Work")) {
            dropdown_menu1.setSelection(0);
        } else if (event.category.equals("Social")) {
            dropdown_menu1.setSelection(1);
        } else {
            dropdown_menu1.setSelection(2);
        }

        // This places the custom layout into the dialog
        builder.setView(dialogView);

        // This adds the Update button
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // These lines get the updated values
                String updatedTitle = title_input.getText().toString().trim();
                String updatedCategory = dropdown_menu1.getSelectedItem().toString();
                String updatedLocation = location_input.getText().toString().trim();
                String updatedDate = date_input.getText().toString().trim();

                // This checks if title is empty
                if (updatedTitle.isEmpty()) {
                    Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This checks if date is empty
                if (updatedDate.isEmpty()) {
                    Toast.makeText(context, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // This checks if date is in the past
                if (isPastDate(updatedDate)) {
                    Toast.makeText(context, "Date cannot be in the past", Toast.LENGTH_SHORT).show();
                    return;
                }

                // These lines update the event object
                event.title = updatedTitle;
                event.category = updatedCategory;
                event.location = updatedLocation;
                event.dateTime = updatedDate;

                // This updates the event in Room
                database.eventDao().update(event);

                // This refreshes the changed item in RecyclerView
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position);
                }

                // This shows a success message
                Toast.makeText(context, "Event updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // This adds a cancel button
        builder.setNegativeButton("Cancel", null);

        // This shows the dialog
        builder.show();
    }

    // This method checks if the date is in the past
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

    // This ViewHolder stores the views for one row
    public static class EventViewHolder extends RecyclerView.ViewHolder {

        // These variables store the row views
        TextView title_value, category_value, location_value, date_value;
        Button edit_button, delete_button;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            // These lines connect the views to XML ids
            title_value = itemView.findViewById(R.id.title_value);
            category_value = itemView.findViewById(R.id.category_value);
            location_value = itemView.findViewById(R.id.location_value);
            date_value = itemView.findViewById(R.id.date_value);
            edit_button = itemView.findViewById(R.id.edit_button);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }
}