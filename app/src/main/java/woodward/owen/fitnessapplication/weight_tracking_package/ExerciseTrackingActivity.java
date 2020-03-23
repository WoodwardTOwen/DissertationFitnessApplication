package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.plate_math_calculator_package.PlateMathCalcActivity;
import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.adapters_package.ExerciseAdapter;
import woodward.owen.fitnessapplication.weight_tracking_package.help.page.TrackingHelpPage;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.TimerViewModel;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;

public class ExerciseTrackingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView dateDisplayTV;
    private TextView emptyView;
    private TextView timerCountDownTextView;
    private DrawerLayout drawer;
    private ExerciseViewModel exerciseViewModel;
    private ExerciseAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private TimerViewModel timerViewModel;
    private CountDownTimer countDownTimer;
    private Button bottomSheetResetButton;
    private Button bottomSheetStartButton;
    private long endTime;
    private List<Exercise> mExercises = new ArrayList<>();
    public static final int EDIT_EXERCISE_REQUEST = 1;
    public static final String EXTRA_DATE_MAIN_UI = "woodward.owen.fitnessapplication.EXTRA_DATE_MAIN_UI";
    private static final String TIMER_PREFS = "timerPrefs";
    private static final String MILLIS_LEFT = "millisLeft";
    private static final String TIMER_RUNNING = "timerRunning";
    private static final String END_TIME = "endTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseViewModel = new ViewModelProvider(ExerciseTrackingActivity.this).get(ExerciseViewModel.class);
        timerViewModel = new ViewModelProvider(ExerciseTrackingActivity.this).get(TimerViewModel.class);
        setContentView(R.layout.activity_main_tracking_ui);
        drawer = findViewById(R.id.drawer_layout);
        timerCountDownTextView = findViewById(R.id.bottomSheetScrollerTextView);
        bottomSheetResetButton = findViewById(R.id.bottomSheetScollerButtonReset);
        bottomSheetStartButton = findViewById(R.id.bottomSheetScollerStartButton);
        emptyView = findViewById(R.id.no_date_available_TextView);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_DATE_MAIN_UI)){
            exerciseViewModel.getCurrentDate().setValue(getIntent().getStringExtra(EXTRA_DATE_MAIN_UI));
        }

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        setToolBar();
        setFloatingButton();

        RecyclerView recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //set true if we know the recycler view size will not change

        adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        Observe();
        ObserveDateChange();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                mExercises = exerciseViewModel.GetAllExercisesByDate().getValue();
                if(dragged.getAdapterPosition() < target.getAdapterPosition()){
                    for(int i = dragged.getAdapterPosition(); i < target.getAdapterPosition(); i++){
                        Collections.swap(mExercises, i, i+1);

                        int order1 = mExercises.get(i).getOrder();
                        int order2 = mExercises.get(i +1).getOrder();
                        mExercises.get(i).setOrder(order2);
                        mExercises.get(i+1).setOrder(order1);

                    }
                }
                else {
                    for(int i = dragged.getAdapterPosition(); i > target.getAdapterPosition(); i--){
                        Collections.swap(mExercises, i, i -1);

                        int order1 = mExercises.get(i).getOrder();
                        int order2 = mExercises.get(i -1).getOrder();
                        mExercises.get(i).setOrder(order2);
                        mExercises.get(i -1).setOrder(order1);

                    }
                }
                adapter.notifyItemMoved(dragged.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                exerciseViewModel.setCachedExercise(adapter.getExercisePosition(viewHolder.getAdapterPosition())); //Cache exercise for undo exercise
                exerciseViewModel.Delete(adapter.getExercisePosition(viewHolder.getAdapterPosition())); //delete selected exercise

                Snackbar.make(viewHolder.itemView, "Deleted Exercise: " + exerciseViewModel.getCurrentCachedExercise().getValue().getExerciseName(), Snackbar.LENGTH_LONG)
                        .setActionTextColor(ContextCompat.getColor(ExerciseTrackingActivity.this, R.color.colorDatePicker))
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exerciseViewModel.Insert(exerciseViewModel.getCurrentCachedExercise().getValue()); //If undo is required, re insert exercise
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);


        //Update Functionality -> sending over the contents to another activity
        adapter.setOnItemClickListener(new ExerciseAdapter.onItemClickListener() { //Implemented Adapter Listener
            @Override
            public void onItemClick(Exercise exercise) {
                callForUpdate(exercise);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_EXERCISE_REQUEST && resultCode == RESULT_OK) {

            assert data != null;
            int id = data.getIntExtra(EditExercise.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(ExerciseTrackingActivity.this, "Exercise Cannot be Updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(EditExercise.EXTRA_EXERCISE_NAME);
            String weight = data.getStringExtra(EditExercise.EXTRA_WEIGHT);
            String reps = data.getStringExtra(EditExercise.EXTRA_REPS);
            String RPE = data.getStringExtra(EditExercise.EXTRA_RPE);
            String date = exerciseViewModel.getCurrentDate().getValue();
            int order = exerciseViewModel.getCurrentOrderPosition().getValue();

            assert reps != null;
            assert weight != null;
            assert RPE != null;
            Exercise exercise = new Exercise(name, Integer.parseInt(reps), Double.parseDouble(weight), Integer.parseInt(RPE), date, order);
            exercise.setId(id);
            exerciseViewModel.Update(exercise);

            Toast.makeText(ExerciseTrackingActivity.this, "Exercise Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ExerciseTrackingActivity.this, "Exercise Not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_addExercise:
                Intent intentAdd = new Intent(ExerciseTrackingActivity.this, CategoryRecyclerView.class);
                intentAdd.putExtra(CategoryRecyclerView.EXTRA_DATE_CATEGORY, exerciseViewModel.getCurrentDate().getValue());
                startActivity(intentAdd);
                closeDrawer();
                return true;
            case R.id.nav_help:
                Intent intentHelpPage = new Intent(ExerciseTrackingActivity.this, TrackingHelpPage.class);
                startActivity(intentHelpPage);
                closeDrawer();
                return true;
            case R.id.nav_graphical:
                Intent intentGraphical = new Intent(ExerciseTrackingActivity.this, GraphicalActivity.class);
                startActivity(intentGraphical);
                closeDrawer();
                return true;
            case R.id.nav_workout_generator:
                Toast.makeText(ExerciseTrackingActivity.this, "Currently Under Construction", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_plateMath:
                Intent intentPlate = new Intent(ExerciseTrackingActivity.this, PlateMathCalcActivity.class);
                startActivity(intentPlate);
                closeDrawer();
                break;
        }
        return true;
    }

    public void closeDrawer () {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tracking_menu, menu);
        return true;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(ExerciseTrackingActivity.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month = month + 1;
        String temp = exerciseViewModel.ConvertDate(day) + "-" + exerciseViewModel.ConvertDate(month) + "-" + exerciseViewModel.ConvertDate(year);
        exerciseViewModel.getCurrentDate().setValue(temp);

    }

    private void setToolBar() {
        //Setting the toolbar in the activity
        Toolbar toolbar = findViewById(R.id.tracking_toolbar);
        setSupportActionBar(toolbar);

        dateDisplayTV = findViewById(R.id.mainUITextViewDate);
        String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if (exerciseViewModel.getCurrentDate().getValue() == null) {
            exerciseViewModel.setDate(dateNow);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ExerciseTrackingActivity.this, drawer, toolbar, R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);

        //Handles rotations on the UI
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    //When pressing the back button, it wont immediately close the activity, just the drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //START because it is on the left hand side of the screen
        } else {
            super.onBackPressed(); // Means the drawer was not open on the back press command
        }
    }

    private void callForUpdate(Exercise exercise) {
        Intent intent = new Intent(ExerciseTrackingActivity.this, EditExercise.class);
        intent.putExtra(EditExercise.EXTRA_ID, exercise.getId());
        intent.putExtra(EditExercise.EXTRA_EXERCISE_NAME, exercise.getExerciseName());
        intent.putExtra(EditExercise.EXTRA_WEIGHT, exercise.getWeight());
        intent.putExtra(EditExercise.EXTRA_REPS, exercise.getReps());
        intent.putExtra(EditExercise.EXTRA_RPE, exercise.getRpe());
        exerciseViewModel.setPosition(exercise.getOrder());

        startActivityForResult(intent, EDIT_EXERCISE_REQUEST);
    }

    private void Observe() {
        exerciseViewModel.GetAllExercisesByDate().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.submitList(exercises);
                if (exercises.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void ObserveDateChange() {
        exerciseViewModel.getCurrentDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dateDisplayTV.setText(s);
            }
        });
    }

    private void setFloatingButton() {
        FloatingActionButton addExerciseButton = findViewById(R.id.button_add_Exercise);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivity = new Intent(ExerciseTrackingActivity.this, CategoryRecyclerView.class);
                intentActivity.putExtra(CategoryRecyclerView.EXTRA_DATE_CATEGORY, exerciseViewModel.getCurrentDate().getValue());
                startActivity(intentActivity);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", (String) dateDisplayTV.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        dateDisplayTV.setText(saveInstanceState.getString("date"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calenderMenuItem:
                showDatePickerDialog();
                return true;
            case R.id.timerMenuItem:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                ExpandBottomSheet();
                timerListenersForBottomSheet();
                return true;
            case R.id.deleteExercisesItem:
                if(adapter.getItemCount() != 0){
                    exerciseViewModel.DeleteAllExercises(exerciseViewModel.getCurrentDate().getValue());
                    Toast.makeText(ExerciseTrackingActivity.this, "All Exercises have been deleted from the Date: " + dateDisplayTV.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(ExerciseTrackingActivity.this, "No Exercises to delete for " + dateDisplayTV.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Timer Section
    private void UpdateCountDownText() {
        //Updates the text at run time to which shows the timer ticking down
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", timerViewModel.calculateMinutesRemaining(), timerViewModel.calculateSecondsRemaining());
        timerCountDownTextView.setText(timeLeft);
    }

    private void pauseTimer() {
        bottomSheetStartButton.setText(timerViewModel.getStartButtonName());
        bottomSheetResetButton.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        timerViewModel.setIsTimerRunning(false);
        UpdateTimer();
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timerViewModel.getTimeRemaining();

        countDownTimer = new CountDownTimer(timerViewModel.getTimeRemaining(), 1000) { //How fast the countdown goes down, currently 1 second
            @Override
            public void onTick(long millisUntilFinished) {
                timerViewModel.setTimeRemaining(millisUntilFinished); //Number of milliseconds until the countdown is finished
                UpdateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerViewModel.setIsTimerRunning(true);
        UpdateCountDownText();
        bottomSheetResetButton.setVisibility(View.INVISIBLE);
        bottomSheetStartButton.setText(timerViewModel.getPauseButtonName());
    }

    private void UpdateTimer() {

    }

    private void RunPauseTimer() {
        if (timerViewModel.getIsTimerRunning()) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void resetTimer() {
        timerViewModel.setTimeRemaining(timerViewModel.getStartTimeInMillis());
        UpdateCountDownText();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Checks Shared Preferences to see if a timer is currently running and therefore loads in the timer if there is
        SharedPreferences prefs = getSharedPreferences(TIMER_PREFS, MODE_PRIVATE);
        timerViewModel.setTimeRemaining(prefs.getLong(MILLIS_LEFT, timerViewModel.getStartTimeInMillis()));
        timerViewModel.setIsTimerRunning(prefs.getBoolean(TIMER_RUNNING, false));
        UpdateCountDownText();

        if (timerViewModel.getIsTimerRunning()) {
            endTime = prefs.getLong(END_TIME, 0);
            timerViewModel.setTimeRemaining(endTime - System.currentTimeMillis());

            if (timerViewModel.getTimeRemaining() < 0) {
                timerViewModel.setTimeRemaining(0);
                timerViewModel.setIsTimerRunning(false);
                UpdateCountDownText();
            } else {
                startTimer();
            }
        }

    }

    @Override
    protected void onStop() {
        //Lifecycle handling onStop cycle -> saves data in conjunction with timer
        super.onStop();
        SharedPreferences prefs = getSharedPreferences(TIMER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(MILLIS_LEFT, timerViewModel.getTimeRemaining());
        editor.putBoolean(TIMER_RUNNING, timerViewModel.getIsTimerRunning());
        editor.putLong(END_TIME, endTime);

        editor.apply();
    }

    private void ExpandBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void timerListenersForBottomSheet() {
        bottomSheetStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunPauseTimer();
            }
        });

        bottomSheetResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }
}
