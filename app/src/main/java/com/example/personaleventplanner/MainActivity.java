package com.example.personaleventplanner;

// This import is needed for the activity lifecycle
import android.os.Bundle;

// This import is for the bottom navigation
import com.google.android.material.bottomnavigation.BottomNavigationView;

// These imports are for navigation
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

// This is the main activity of the app
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // This runs when the app starts
        super.onCreate(savedInstanceState);

        // This connects this Java file to activity_main.xml
        setContentView(R.layout.activity_main);

        // This gets the bottom navigation from the layout
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);

        // This gets the NavHostFragment from the screen
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        // This checks that the NavHostFragment exists before using it
        if (navHostFragment != null) {

            // This gets the nav controller from the nav host fragment
            NavController navController = navHostFragment.getNavController();

            // This connects the bottom navigation to the nav controller
            NavigationUI.setupWithNavController(bottom_navigation, navController);
        }
    }
}