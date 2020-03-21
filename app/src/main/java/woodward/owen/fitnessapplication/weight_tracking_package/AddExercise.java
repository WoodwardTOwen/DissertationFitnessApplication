package woodward.owen.fitnessapplication.weight_tracking_package;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.AddExerciseViewModel;

public class AddExercise extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DATE = "woodward.owen.fitnessapplication.EXTRA_DATE";
    private EditText weightInput;
    private EditText repInput;
    private EditText rpeInput;
    private TextView exerciseTextView;
    private TextView categoryTextView;
    private String dateForExercise;
    private AddExerciseViewModel addExerciseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_layout_file);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DATE)) {
            dateForExercise = intent.getStringExtra(EXTRA_DATE);
        }

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.close_white);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        addExerciseViewModel = new ViewModelProvider(AddExercise.this).get(AddExerciseViewModel.class);

        listen();

        Intent i = getIntent();
        exerciseTextView.setText(i.getStringExtra("Exercise"));
        categoryTextView.setText(i.getStringExtra("Category"));

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().setTitle("Gainz Tracker - Add Exercise");  // In landscape
        }

    }

    private void saveExercise() {
        String exerciseName = exerciseTextView.getText().toString();
        String exerciseWeight = weightInput.getText().toString();
        String exerciseReps = repInput.getText().toString();
        String exerciseRPE = rpeInput.getText().toString();

        boolean verify = AddEditMethods.isVerified(exerciseWeight, exerciseReps, exerciseRPE);
        if (verify) {
            addExerciseViewModel.setDate(dateForExercise);
            Exercise exercise = new Exercise(exerciseName, Integer.parseInt(exerciseReps), Double.parseDouble(exerciseWeight), Integer.parseInt(exerciseRPE), dateForExercise);
            addExerciseViewModel.Insert(exercise);
            addExerciseViewModel.cleanSharedPreferences();
            repInput.getText().clear();
            weightInput.getText().clear();
            rpeInput.getText().clear();
            Toast.makeText(AddExercise.this, "Exercise Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddExercise.this, ExerciseTrackingActivity.class);
            intent.putExtra(ExerciseTrackingActivity.EXTRA_DATE_MAIN_UI, addExerciseViewModel.getCurrentDate().getValue());
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(AddExercise.this, "Please Ensure All Fields Have an Inputted Value", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveExercise) {
            saveExercise();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listen() {
        Button buttonIncrementWeight;
        Button buttonDecrementWeight;
        Button buttonIncrementRep;
        Button buttonDecrementRep;
        Button buttonDecrementRPE;
        Button buttonIncrementRPE;

        exerciseTextView = findViewById(R.id.exerciseSpinnerTitle);
        categoryTextView = findViewById(R.id.catSpinnerTitle);
        buttonIncrementWeight = findViewById(R.id.incrementWeightBnt);
        buttonDecrementWeight = findViewById(R.id.decrementWeightBnt);
        buttonIncrementRep = findViewById(R.id.incrementRepBnt);
        buttonDecrementRep = findViewById(R.id.decrementRepBnt);
        buttonIncrementRPE = findViewById(R.id.incrementRPEBnt);
        buttonDecrementRPE = findViewById(R.id.decrementRPEbnt);
        weightInput = findViewById(R.id.editWeightInputET);
        repInput = findViewById(R.id.editRepInputET);
        rpeInput = findViewById(R.id.editRPEInput);
        rpeInput.setFilters(new InputFilter[]{new FilterInputs("0", "10")});

        buttonIncrementWeight.setOnClickListener(AddExercise.this);
        buttonDecrementWeight.setOnClickListener(AddExercise.this);
        buttonIncrementRep.setOnClickListener(AddExercise.this);
        buttonDecrementRep.setOnClickListener(AddExercise.this);
        buttonIncrementRPE.setOnClickListener(AddExercise.this);
        buttonDecrementRPE.setOnClickListener(AddExercise.this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        String tempInput = weightInput.getText().toString().trim();
        String inputRep = repInput.getText().toString().trim();
        String inputRPE = rpeInput.getText().toString().trim();
        switch (v.getId()) {
            case R.id.incrementWeightBnt:
                weightInput.setText(Double.toString(AddEditMethods.incrementWeight(tempInput)));
                break;
            case R.id.decrementWeightBnt:
                weightInput.setText(Double.toString(AddEditMethods.decrementWeight(tempInput)));
                break;
            case R.id.incrementRepBnt:
                repInput.setText(Integer.toString(AddEditMethods.incrementRepsRPE(inputRep)));
                break;
            case R.id.decrementRepBnt:
                repInput.setText(Integer.toString(AddEditMethods.decrementRepsRPE(inputRep)));
                break;
            case R.id.incrementRPEBnt:
                if (rpeInput.getText().toString().equals("10")) {
                    break;
                }
                rpeInput.setText(Integer.toString(AddEditMethods.incrementRepsRPE(inputRPE)));
                break;
            case R.id.decrementRPEbnt:
                rpeInput.setText(Integer.toString(AddEditMethods.decrementRepsRPE(inputRPE)));
                break;
        }
    }

    //If the lifecycle turns to a paused state, it temporarily holds onto the variable states
    @Override
    protected void onPause() {
        super.onPause();
        addExerciseViewModel.saveSharedPrefData(weightInput.getText().toString(), repInput.getText().toString(), rpeInput.getText().toString());
        Log.i("onPause", "The app has saved the preference");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
//Gathers stored variables in the onResume state from shared preferences
    @Override
    protected void onResume() {
        super.onResume();
        weightInput.setText(addExerciseViewModel.loadWeightSharedPreference());
        repInput.setText(addExerciseViewModel.loadRepsSharedPreference());
        rpeInput.setText(addExerciseViewModel.loadRPESharedPreference());
        Log.i("onResume", "The app has loaded the data");
    }
}
