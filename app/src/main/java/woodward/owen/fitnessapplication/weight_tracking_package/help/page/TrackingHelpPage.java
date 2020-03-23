package woodward.owen.fitnessapplication.weight_tracking_package.help.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import woodward.owen.fitnessapplication.R;

public class TrackingHelpPage extends AppCompatActivity {

    private int pageCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_help_page);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_Help_Page);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(savedInstanceState == null) {
            pageCounter = 0;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_help_page, new HelpHomeFragment()).commit();
        }
        else{
            pageCounter = savedInstanceState.getInt("counter", 0);
            switch(pageCounter){
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_help_page, new HelpHomeFragment()).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_help_page, new HelpPlateMathFragment()).commit();
                    break;
                case 3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_help_page, new HelpGraphicalAnaylsisFragment()).commit();
                    break;
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag = null;
            switch(item.getItemId()) {
                case R.id.nav_main_tracking_UI:
                    pageCounter =1;
                    selectedFrag = new HelpHomeFragment();
                    break;
                case R.id.nav_plate_math_calculator:
                    pageCounter =2;
                    selectedFrag = new HelpPlateMathFragment();
                    break;
                case R.id.nav_help_page_item_3:
                    pageCounter=3;
                    selectedFrag = new HelpGraphicalAnaylsisFragment();
                    break;
            }

            assert selectedFrag != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_help_page, selectedFrag).commit();

            return true;
        }
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", pageCounter);
    }
}
