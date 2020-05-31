
package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.GraphicalViewModel;

public class GraphicalLoadingScreen extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_TITLE= "woodward.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    public static final String EXTRA_GRAPHICAL_OPTION= "woodward.owen.fitnessapplication.EXTRA_GRAPHICAL_OPTION";
    public static final String EXTRA_EXERCISE_DATE="woodward.owen.fitnessapplication.EXTRA_EXERCISE_DATE";
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    private static final int TIME_OUT = 1000;
    private GraphicalViewModel graphicalViewModel;
    private String graphicalOption = "";
    private ArrayList<Exercise> currentExercises = new ArrayList<>();
    private ArrayList<Exercise> reformedListOfExercises = new ArrayList<>();
    private static GraphicalRunnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        TextView titleTextView = findViewById(R.id.Info_Title_Loading_Screen);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        graphicalViewModel = new ViewModelProvider(GraphicalLoadingScreen.this).get(GraphicalViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_EXERCISE_TITLE)) {
            graphicalViewModel.setName(intent.getStringExtra(EXTRA_EXERCISE_TITLE));
            graphicalOption = intent.getStringExtra(EXTRA_GRAPHICAL_OPTION);
        }else if(intent.hasExtra(EXTRA_EXERCISE_DATE)){
            //Call Converter Method here
            graphicalViewModel.setExerciseDates(WeeklyDates(intent.getStringExtra(EXTRA_EXERCISE_DATE)));
            graphicalOption = intent.getStringExtra(EXTRA_GRAPHICAL_OPTION);

        }

        titleTextView.setText(String.format("Loading %s Analysis", graphicalOption));

        //Observers
        Observe();
        ObserveNameChange();
        ObserveWeeklyList();

        mRunnable = new GraphicalRunnable(this);
        handler.postDelayed(mRunnable, TIME_OUT);
    }

    //Activity LifeCycle Handling
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(mRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //To Stop Memory Leaks from the callbacks
        handler.removeCallbacks(mRunnable);
    }

    //Observers
    private void Observe() {
        graphicalViewModel.getListOfExercisesGraphical().observe(GraphicalLoadingScreen.this, exercises -> {
            if(currentExercises.size() != 0){
                currentExercises.clear();
            }
            currentExercises = (ArrayList<Exercise>) exercises;
        });
    }

    private void ObserveNameChange () {
        graphicalViewModel.getCurrentName().observe(GraphicalLoadingScreen.this, s -> {
        });
    }

    private void ObserveWeeklyList() {
        graphicalViewModel.getListOfExercisesGraphicalWeekly().observe(GraphicalLoadingScreen.this, exercises -> {
            if(currentExercises.size() != 0){
                currentExercises.clear();
            }
            currentExercises = (ArrayList<Exercise>) exercises;
        });
    }

    //Graphical Analysis Set Up For Weekly Volume
    private List<String> WeeklyDates (String date) {
        int weekNumber, year; List<String> dates;

        date = GraphicalAnalysisMethods.convertDate(date);
        weekNumber = GraphicalAnalysisMethods.findCalenderWeek(date);
        year = GraphicalAnalysisMethods.findCalenderYear(date);
        dates = GraphicalAnalysisMethods.findDatesForCalenderWeek(weekNumber, year);
        return dates;
    }

    private void FilterData (String filterOption) {
        switch (filterOption) {
            case "MaxWeight":
                reformedListOfExercises = (ArrayList<Exercise>) GraphicalAnalysisMethods.sortDataForWeightEntries(currentExercises);
                return;
            case "MaxVolume":
                reformedListOfExercises = (ArrayList<Exercise>) GraphicalAnalysisMethods.sortDataForVolumeDisplay(currentExercises);
                return;
            case "WeeklyVolume":
                reformedListOfExercises = (ArrayList<Exercise>) GraphicalAnalysisMethods.sortDataForWeeklyVolume(currentExercises);
        }
    }


    private void completeTask () {
        Intent graphical = new Intent(GraphicalLoadingScreen.this, GraphicalActivity.class);
        GraphicalAnalysisMethods.convertListOfDates(currentExercises);
        GraphicalAnalysisMethods.sortDatesInOrder(currentExercises);
        FilterData(graphicalOption);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_OF_EXERCISES, reformedListOfExercises);
        graphical.putExtras(bundle);
        graphical.putExtra(GraphicalActivity.FILTER_OPTION, graphicalOption);

        if(graphicalOption.equals("WeeklyVolume")){
            graphical.putExtra(GraphicalActivity.EXTRA_FIRST_DATE, Objects.requireNonNull(graphicalViewModel.getExerciseDates().getValue()).get(0));
            graphical.putExtra(GraphicalActivity.EXTRA_LAST_DATE, graphicalViewModel.getExerciseDates().getValue().get(6));
        }
        else {
            graphical.putExtra(GraphicalActivity.EXTRA_EXERCISE_NAME, graphicalViewModel.getCurrentName().getValue());
        }
        startActivity(graphical);
        finish();
    }

    private static class GraphicalHandler extends Handler {}
    private final GraphicalHandler handler = new GraphicalHandler();

    public class GraphicalRunnable implements Runnable {
        //Weak Reference because it can be garbage collected whenever
        private final WeakReference<Activity> mActivity;
        GraphicalRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Activity activity = mActivity.get();
            if (activity != null) {
                completeTask();
            }
        }
    }

}
