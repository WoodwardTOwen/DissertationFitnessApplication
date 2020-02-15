package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import woodward.owen.fitnessapplication.ExercisePackage.Category;
import woodward.owen.fitnessapplication.ExercisePackage.CategoryType;
import woodward.owen.fitnessapplication.ExercisePackage.Exercise;
import woodward.owen.fitnessapplication.ExercisePackage.IO;
import woodward.owen.fitnessapplication.R;

public class AddEditExercise extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "woodward.owen.fitnessapplication.EXTRA_ID";
    public static final String EXTRA_EXERCISE_NAME = "woodward.owen.fitnessapplication.EXTRA_NAME";
    public static final String EXTRA_WEIGHT = "woodward.owen.fitnessapplication.EXTRA_WEIGHT";
    public static final String EXTRA_REPS = "woodward.owen.fitnessapplication.EXTRA_REPS";
    public static final String EXTRA_RPE = "woodward.owen.fitnessapplication.EXTRA_RPE";

    private static final Map<CategoryType, List<String>> PossibleNames = new Hashtable<>();
    private static final Map<CategoryType, Category> Categories = new Hashtable<>();
    private Spinner catSpinner;
    private Spinner exerciseSpinner;
    private List<String> exerciseList;
    private Button buttonIncrementWeight;
    private Button buttonDecrementWeight;
    private Button buttonIncrementRep;
    private Button buttonDecrementRep;
    private EditText weightInput;
    private EditText repInput;
    private EditText rpeInput;
    private AddExerciseViewModel exerciseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_layout_file);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_black);

        assignIOValues(getApplication());
        listen();
        checkSelectedItem();

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            exerciseSpinner.setSelection(getIndex(exerciseSpinner, EXTRA_EXERCISE_NAME));
            //Spinner goes here
            weightInput.setText(Double.toString(intent.getDoubleExtra(EXTRA_WEIGHT, 1)));
            repInput.setText(Integer.toString(intent.getIntExtra(EXTRA_REPS, 1)));
            rpeInput.setText(Integer.toString(intent.getIntExtra(EXTRA_RPE, 1)));


        } else {
            setTitle("Add Exercise");
        }

    }

    private void saveExercise() {
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        String exerciseWeight = weightInput.getText().toString();
        String exerciseReps = repInput.getText().toString();
        String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String exerciseRPE;
        if (rpeInput.getText().equals(0)) {
            exerciseRPE = "N/A";
        } else {
            exerciseRPE = rpeInput.getText().toString();
        }
        Exercise exercise = new Exercise(exerciseName, Integer.parseInt(exerciseReps), Double.parseDouble(exerciseWeight), Integer.parseInt(exerciseRPE));

        exerciseViewModel = new ViewModelProvider(AddEditExercise.this).get(AddExerciseViewModel.class);
        exerciseViewModel.Insert(exercise);
        Toast.makeText(AddEditExercise.this, "Exercise Saved", Toast.LENGTH_SHORT).show();

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveExercise:
                saveExercise();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        catSpinner = findViewById(R.id.spinner_Category_Tracking);

        List<CategoryType> catList = new ArrayList<>(PossibleNames.keySet());

        ArrayAdapter<CategoryType> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        return catSpinner;
    }

    private void checkSelectedItem() {
        catSpinner = setAdapterCatAdapter();
        exerciseSpinner = findViewById(R.id.spinner_Exercises_Tracking);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Map.Entry<CategoryType, List<String>> entry : PossibleNames.entrySet()) {
                    if (entry.getKey().equals(catSpinner.getSelectedItem())) {
                        exerciseList = new ArrayList(entry.getValue());

                        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(AddEditExercise.this,
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

    public void listen() {
        buttonIncrementWeight = findViewById(R.id.incrementWeightBnt);
        buttonDecrementWeight = findViewById(R.id.decrementWeightBnt);
        buttonIncrementRep = findViewById(R.id.incrementRepBnt);
        buttonDecrementRep = findViewById(R.id.decrementRepBnt);
        weightInput = findViewById(R.id.editWeightInputET);
        repInput = findViewById(R.id.editRepInputET);
        rpeInput = findViewById(R.id.editRPEInput);

        buttonIncrementWeight.setOnClickListener(AddEditExercise.this);
        buttonDecrementWeight.setOnClickListener(AddEditExercise.this);
        buttonIncrementRep.setOnClickListener(AddEditExercise.this);
        buttonDecrementRep.setOnClickListener(AddEditExercise.this);
    }

    @Override
    public void onClick(View v) {
        String tempInput = weightInput.getText().toString().trim();
        String inputRep = repInput.getText().toString().trim();
        switch (v.getId()) {
            case R.id.incrementWeightBnt:
                weightInput.setText(Double.toString(incrementWeight(tempInput)));
                break;
            case R.id.decrementWeightBnt:
                weightInput.setText(Double.toString(decrementWeight(tempInput)));
                break;
            case R.id.incrementRepBnt:
                repInput.setText(Integer.toString(incrementReps(inputRep)));
                break;
            case R.id.decrementRepBnt:
                repInput.setText(Integer.toString(decrementReps(inputRep)));

        }
    }

    public double incrementWeight(String tempInput) {
        double value;
        if (tempInput.matches("")) {
            value = 2.5;
            return value;
        }

        value = Double.parseDouble(tempInput);
        value = value + 2.5;
        return value;
    }

    public double decrementWeight(String tempInput) {
        double value = 0;
        if (!tempInput.matches("") && Double.parseDouble(tempInput) >= 2.5) {
            value = Double.parseDouble(tempInput);
            value = value - 2.5;
            return value;
        }
        return value;
    }

    public int incrementReps(String input) {
        int value;
        if (input.matches("")) {
            value = 1;
            return value;
        }
        value = Integer.parseInt(input);
        value = value + 1;
        return value;
    }

    public int decrementReps(String input) {
        int value = 0;
        if (!input.matches("") && Integer.parseInt(input) >= 1) {
            value = Integer.parseInt(input);
            value = value - 1;
            return value;
        }
        return value;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
