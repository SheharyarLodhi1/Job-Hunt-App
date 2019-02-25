package com.next.evampintern.projectoneeands;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import  android.support.v4.app.Fragment;

import com.next.evampintern.projectoneeands.Fragments.AndroidJobs;
import com.next.evampintern.projectoneeands.Fragments.JobSfFragment;
import com.next.evampintern.projectoneeands.Fragments.SearchFragment;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new SearchFragment());

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
             /*
            * we simply add the container which we have added earlier a framelayout to show our 3 fragments inside
            * return true means when we selected a fragment will be show
             */
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

             return true;
        }

        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_jobs_sf:
                    selectedFragment = new JobSfFragment();
                    break;
                case R.id.nav_android_jobs:
                    selectedFragment = new AndroidJobs();
                    break;
            }

            return loadFragment(selectedFragment);
    }


}
