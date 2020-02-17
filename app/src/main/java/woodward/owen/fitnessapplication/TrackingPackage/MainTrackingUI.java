package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;
import woodward.owen.fitnessapplication.HomePagePackage.HomePage;
import woodward.owen.fitnessapplication.PlateMathCalculatorPackage.PlateMathCalcActivity;
import woodward.owen.fitnessapplication.R;

public class MainTrackingUI extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView dateDisplayTV;
    private DrawerLayout drawer;
    private ExerciseViewModel exerciseViewModel;
    public static final int EDIT_EXERCISE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tracking_ui);

        FloatingActionButton addExerciseButton = findViewById(R.id.button_add_Exercise);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainTrackingUI.this, AddExercise.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //set true if we know the recycler view size will not change

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        exerciseViewModel = new ViewModelProvider(MainTrackingUI.this).get(ExerciseViewModel.class);
        //exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                //Updating Recycler View
                adapter.submitList(exercises);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                exerciseViewModel.Delete(adapter.getExercisePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainTrackingUI.this, "Exercise Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExerciseAdapter.onItemClickListener() { //Implemented Adapter Listener
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(MainTrackingUI.this, EditExercise.class);
                intent.putExtra(EditExercise.EXTRA_ID, exercise.getId());
                intent.putExtra(EditExercise.EXTRA_EXERCISE_NAME, exercise.getExerciseName());
                intent.putExtra(EditExercise.EXTRA_WEIGHT, exercise.getWeight());
                intent.putExtra(EditExercise.EXTRA_REPS, exercise.getReps());
                intent.putExtra(EditExercise.EXTRA_RPE, exercise.getRpe());

                startActivityForResult(intent, EDIT_EXERCISE_REQUEST);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        setToolBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_EXERCISE_REQUEST && resultCode == RESULT_OK){

            int id = data.getIntExtra(EditExercise.EXTRA_ID, -1);
            if(id == -1) {
                Toast.makeText(MainTrackingUI.this, "Exercise Cannot be Updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(EditExercise.EXTRA_EXERCISE_NAME);
            String weight = data.getStringExtra(EditExercise.EXTRA_WEIGHT);
            String reps = data.getStringExtra(EditExercise.EXTRA_REPS);
            String RPE = data.getStringExtra(EditExercise.EXTRA_RPE);

            Exercise exercise = new Exercise(name, Integer.parseInt(reps), Double.parseDouble(weight), Integer.parseInt(RPE));
            exercise.setId(id);
            exerciseViewModel.Update(exercise);

            Toast.makeText(MainTrackingUI.this, "Exercise Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainTrackingUI.this, "Exercise Not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.nav_addExercise:
                Intent intentAdd = new Intent(MainTrackingUI.this, AddExercise.class);
                startActivity(intentAdd);
                drawer.closeDrawer(GravityCompat.START);
                return true;
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
                showDatePickerDialog();
                return true;
            case R.id.deleteExercisesItem:
                exerciseViewModel.DeleteAllExercises();
                Toast.makeText(MainTrackingUI.this, "All Exercises have been deleted from the Date: " + dateDisplayTV.getText().toString(), Toast.LENGTH_SHORT).show();
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
        String currentDate = "Date: " + day + "-" + month + "-" + year;
        dateDisplayTV.setText(currentDate);
    }

    private void setToolBar() {
        //Setting the toolbar in the activity
        Toolbar toolbar = findViewById(R.id.tracking_toolbar);
        setSupportActionBar(toolbar);

        dateDisplayTV = findViewById(R.id.mainUITextViewDate);
        String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateDisplayTV.setText("Date: " + dateNow);

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
