package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import woodward.owen.fitnessapplication.R;

public class MainTrackingUI extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateDisplayTV;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tracking_ui);

        getSupportActionBar().hide();

        toolbar = findViewById(R.id.trackingToolbar);
        setSupportActionBar(toolbar);

        dateDisplayTV = findViewById(R.id.dateDisplayTV);
        String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateDisplayTV.setText("Date - " + dateNow);


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
}
