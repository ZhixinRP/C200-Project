package org.tensorflow.lite.examples.classification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch(menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_chart:
                    selectedFragment = new ChartFragment();
                    break;
                case R.id.navigation_tracker:
                    selectedFragment = new TrackerFragment();
                    break;
                case R.id.navigation_camera:
                    Intent intent = new Intent(HomeActivity.this, ClassifierActivity.class);
                    startActivity(intent);
            }
            if(selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flHome, selectedFragment).commit();
            }
            return true;
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        Log.d("onResume","onResume is called");
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new HomeFragment()).commit();
    }
}