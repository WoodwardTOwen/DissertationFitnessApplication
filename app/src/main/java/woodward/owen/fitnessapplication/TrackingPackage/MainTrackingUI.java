package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import woodward.owen.fitnessapplication.HomePagePackage.HomePage;
import woodward.owen.fitnessapplication.PlateMathCalculatorPackage.PlateMathCalcActivity;
import woodward.owen.fitnessapplication.R;

public class MainTrackingUI extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView dateDisplayTV;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tracking_ui);
        drawer = findViewById(R.id.drawer_layout);

        setToolBar();

        dateDisplayTV = findViewById(R.id.dateDisplayTV);
        String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateDisplayTV.setText("Date - " + dateNow);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.nav_addExercise:
                Intent intentAdd = new Intent(MainTrackingUI.this, AddExercise.class);
                startActivity(intentAdd);
                drawer.closeDrawer(GravityCompat.START);
            case R.id.nav_help:
                Toast.makeText(MainTrackingUI.this, "You interacted with the Help Page", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_graphical:
                Toast.makeText(MainTrackingUI.this, "You have interacted with the graphical Page", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_home:
                Intent intent = new Intent(MainTrackingUI.this, HomePage.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_plateMath:
                Intent intentPlate = new Intent(MainTrackingUI.this, PlateMathCalcActivity.class);
                startActivity(intentPlate);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tracking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch(item.getItemId()) {
            case R.id.calenderMenuItem:
                Toast.makeText(MainTrackingUI.this, "The Calender Item Menu has been chosen", Toast.LENGTH_SHORT).show();
                showDatePickerDialog();
                return true;
            case R.id.deleteExercisesItem:
                Toast.makeText(MainTrackingUI.this, "The Exercises Item Menu has been chosen", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainTrackingUI.this, R.style.DialogTheme,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet (DatePicker view, int year, int month, int day) {

        //Can populate Data from given date in here potentially?
        String currentDate = "Date - " + day + "-" + month + "-" + year;
        dateDisplayTV.setText(currentDate);
    }

    private void setToolBar() {
        //Setting the toolbar in the activity
        Toolbar toolbar = findViewById(R.id.tracking_toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainTrackingUI.this, drawer, toolbar,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);

        //Handles rotations on the UI
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    //When pressing the back button, it wont immediately close the activity, just the drawer
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START); //START because it is on the left hand side of the screen
        }
        else {
            super.onBackPressed(); // Means the drawer was not open on the back press command
        }
    }
}
