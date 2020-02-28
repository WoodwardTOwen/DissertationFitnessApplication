package woodward.owen.fitnessapplication.weight_tracking_package;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.CategoryType;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.exercise_package.IO;
import woodward.owen.fitnessapplication.R;

public class AddExercise extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DATE = "woodward.owen.fitnessapplication.EXTRA_DATE";
    private static final Map<CategoryType, List<String>> PossibleNames = new Hashtable<>();
    private static final Map<CategoryType, Category> Categories = new Hashtable<>();
    private Spinner catSpinner;
    private Spinner exerciseSpinner;
    private ArrayList<String> exerciseList;
    private EditText weightInput;
    private EditText repInput;
    private EditText rpeInput;
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
        assignIOValues(getApplication());
        listen();
        checkSelectedItem();

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().setTitle("Fitness Application - Add Exercise");  // In landscape
        }

    }

    private void saveExercise() {
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        String exerciseWeight = weightInput.getText().toString();
        String exerciseReps = repInput.getText().toString();
        String exerciseRPE = rpeInput.getText().toString();

        boolean verify = AddEditMethods.isVerified(exerciseWeight, exerciseReps, exerciseRPE);
        if (verify) {
            Exercise exercise = new Exercise(exerciseName, Integer.parseInt(exerciseReps), Double.parseDouble(exerciseWeight), Integer.parseInt(exerciseRPE), dateForExercise);
            addExerciseViewModel.Insert(exercise);
            addExerciseViewModel.cleanSharedPreferences();
            repInput.getText().clear();
            weightInput.getText().clear();
            rpeInput.getText().clear();
            Toast.makeText(AddExercise.this, "Exercise Saved", Toast.LENGTH_SHORT).show();
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

    private void assignIOValues(Application context) {
        IO.setInstance(context);
        Map<CategoryType, String[]> pair = new Hashtable<>(IO.ReadData());
        for (Map.Entry<CategoryType, String[]> entry : pair.entrySet()) {
            PossibleNames.put(entry.getKey(), new ArrayList<>(Arrays.asList(entry.getValue())));
        }

        for (CategoryType type : CategoryType.values()) {

            switch (type) {
                case CARDIO:
                    Categories.put(type, new Category(type, false));
                    break;
                default:
                    Categories.put(type, new Category(type, true));
                    break;
            }
        }

    }

    private Spinner setAdapterCatAdapter() {

        List<CategoryType> catList = new ArrayList<>(PossibleNames.keySet());

        ArrayAdapter<CategoryType> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        return catSpinner;
    }

    private void checkSelectedItem() {
        catSpinner = setAdapterCatAdapter();

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Map.Entry<CategoryType, List<String>> entry : PossibleNames.entrySet()) {
                    if (entry.getKey().equals(catSpinner.getSelectedItem())) {
                        exerciseList = new ArrayList(entry.getValue());

                        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(AddExercise.this,
                                android.R.layout.simple_spinner_item, exerciseList);
                        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        exerciseSpinner.setAdapter(exerciseAdapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listen() {
        Button buttonIncrementWeight;
        Button buttonDecrementWeight;
        Button buttonIncrementRep;
        Button buttonDecrementRep;
        Button buttonDecrementRPE;
        Button buttonIncrementRPE;

        exerciseSpinner = findViewById(R.id.spinner_Exercises_Tracking);
        catSpinner = findViewById(R.id.spinner_Category_Tracking);
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
