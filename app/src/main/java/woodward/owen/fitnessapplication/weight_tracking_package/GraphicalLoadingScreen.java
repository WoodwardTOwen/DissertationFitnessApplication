
package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.GraphicalViewModel;

public class GraphicalLoadingScreen extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_TITLE= "woodward.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    public static final String EXTRA_GRAPHICAL_OPTION= "woodward.owen.fitnessapplication.EXTRA_GRAPHICAL_OPTION";
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    private static final int TIME_OUT = 1000;
    private GraphicalViewModel graphicalViewModel;
    private String graphicalOption = "";
    private ArrayList<Exercise> currentExercises = new ArrayList<>();
    private ArrayList<Exercise> reformedListOfExercises = new ArrayList<>();

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
        }

        titleTextView.setText(String.format("Loading %s Analysis", graphicalOption));
        Observe();
        ObserveNameChange();


        new Handler().postDelayed(() -> {
            Intent graphical = new Intent(GraphicalLoadingScreen.this, GraphicalActivity.class);

            GraphicalAnalysisMethods.convertDates(currentExercises);
            GraphicalAnalysisMethods.sortDatesInOrder(currentExercises);
            FilterData(graphicalOption);

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LIST_OF_EXERCISES, reformedListOfExercises);
            graphical.putExtras(bundle);
            graphical.putExtra(GraphicalActivity.FILTER_OPTION, graphicalOption);
            graphical.putExtra(GraphicalActivity.EXTRA_EXERCISE_NAME, graphicalViewModel.getCurrentName().getValue());
            startActivity(graphical);
            finish();
        }, TIME_OUT);
    }

    private void Observe() {
        graphicalViewModel.getListOfExercisesGraphical().observe(GraphicalLoadingScreen.this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                if(currentExercises.size() != 0){
                    currentExercises.clear();
                }
                currentExercises = (ArrayList<Exercise>) exercises;
            }
        });
    }

    private void ObserveNameChange () {
        graphicalViewModel.getCurrentName().observe(GraphicalLoadingScreen.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });
    }

    private void FilterData (String filterOption) {
        switch (filterOption) {
            case "MaxWeight":
                reformedListOfExercises = (ArrayList<Exercise>) GraphicalAnalysisMethods.sortDataForWeightEntries(currentExercises);
                return;
            case "MaxVolume":
                reformedListOfExercises = (ArrayList<Exercise>) GraphicalAnalysisMethods.sortDataForVolumeDisplay(currentExercises);
        }
    }
}
