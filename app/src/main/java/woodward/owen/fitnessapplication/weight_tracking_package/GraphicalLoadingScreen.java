
package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;

public class GraphicalLoadingScreen extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_TITLE= "woodard.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    private static final int TIME_OUT = 1000;
    private ExerciseViewModel exerciseViewModel;
    private ArrayList<Exercise> currentExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        exerciseViewModel = new ViewModelProvider(GraphicalLoadingScreen.this).get(ExerciseViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_EXERCISE_TITLE)) {
            exerciseViewModel.setName(intent.getStringExtra(EXTRA_EXERCISE_TITLE));
        }

        Observe();
        ObserveNameChange();

        new Handler().postDelayed(() -> {
            Intent graphical = new Intent(GraphicalLoadingScreen.this, GraphicalActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LIST_OF_EXERCISES, currentExercises);
            graphical.putExtras(bundle);
            startActivity(graphical);
            finish();
        }, TIME_OUT);
    }

    private void Observe() {
        exerciseViewModel.getListOfExercisesGraphical().observe(GraphicalLoadingScreen.this, new Observer<List<Exercise>>() {
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
        exerciseViewModel.getCurrentName().observe(GraphicalLoadingScreen.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(GraphicalLoadingScreen.this, "The Name has been changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
