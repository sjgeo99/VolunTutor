package com.example.voluntutor;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This class allows for the UI to run in compliance with the individual fragments and the bottom navigation bar
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method allows the GUI to load individual fragments and switch between views
     * @param fragment desired fragment
     * @return boolean
     */
    private boolean loadFragment (Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    //SETTINGS BUTTON
    private Button button;

    /**
     * This method sets the content views, opens the settings button, sets the content views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        button = findViewById(R.id.settingbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

    }

    /**
     * This method opens the settings preference screen
     */
    public void openSettings ()
    {
        Intent intent = new Intent(this, SettingsPreference.class);
        startActivity(intent);
    }


    /**
     * This method allows the bottom navigation bar to be accessed on each screen, and
     * allows for the chosen screens to be displayed
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_hours:
                            selectedFragment = new HoursFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.nav_make_user:
                            selectedFragment = new MakeUserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    /**
     * This creates a new instance of the preference fragment class for the settings preference fragment
     */
    public static class PreferFragment extends PreferenceFragment
    {
        public void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settingspreference);
        }

    }


}
