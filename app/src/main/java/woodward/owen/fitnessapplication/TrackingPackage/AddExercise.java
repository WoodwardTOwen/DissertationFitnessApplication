package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.ExercisePackage.Category;
import woodward.owen.fitnessapplication.ExercisePackage.CategoryType;
import woodward.owen.fitnessapplication.ExercisePackage.IO;
import woodward.owen.fitnessapplication.R;

public class AddExercise extends AppCompatActivity implements View.OnClickListener{

    private static final Map<CategoryType, List<String>> PossibleNames = new Hashtable<>();
    private static final Map<CategoryType, Category> Categories = new Hashtable<>();
    private Spinner catSpinner;
    private Spinner exerciseSpinner;
    private List<String> exerciseList;
    private Button buttonIncrementWeight;
    private Button buttonDecrementWeight;
    private EditText weightInput;
    private Button buttonIncrementRep;
    private Button buttonDecrementRep;
    private EditText repInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_layout_file);
        assignIOValues(getApplicationContext());
        checkSelectedItem();

        Toolbar toolbar = findViewById(R.id.tracking_toolbar);
        setSupportActionBar(toolbar);

    }

    private void assignIOValues (Context context){
        IO io = new IO(context);
        Map<CategoryType, String[]> pair = new HashMap<>(io.ReadData());
        for(Map.Entry<CategoryType,String[]> entry : pair.entrySet()){
            PossibleNames.put(entry.getKey(), new ArrayList<>(Arrays.asList(entry.getValue())));
        }

        for(CategoryType type : CategoryType.values()) {

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

    private Spinner setAdapterCatAdapter () {
        catSpinner = findViewById(R.id.spinner_Category_Tracking);

        List<CategoryType> catList = new ArrayList<>(PossibleNames.keySet());

        ArrayAdapter<CategoryType> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        return  catSpinner;
    }

    private void checkSelectedItem () {
        catSpinner = setAdapterCatAdapter();
        exerciseSpinner = findViewById(R.id.spinner_Exercises_Tracking);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Map.Entry<CategoryType,List<String>>entry : PossibleNames.entrySet()) {
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

    public void listen() {
        buttonIncrementWeight = findViewById(R.id.incrementWeightBnt);
        buttonDecrementWeight = findViewById(R.id.decrementWeightBnt);
        buttonIncrementRep = findViewById(R.id.incrementRepBnt);
        buttonDecrementRep = findViewById(R.id.decrementRepBnt);
        weightInput = findViewById(R.id.editWeightInputET);
        repInput = findViewById(R.id.editRepInputET);

        buttonIncrementWeight.setOnClickListener(AddExercise.this);
        buttonDecrementWeight.setOnClickListener(AddExercise.this);
        buttonIncrementRep.setOnClickListener(AddExercise.this);
        buttonDecrementRep.setOnClickListener(AddExercise.this);
    }

    @Override
    public void onClick(View v) {
        String tempInput = weightInput.getText().toString().trim();
        String inputRep = repInput.getText().toString().trim();
        switch(v.getId()) {
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

    public double incrementWeight (String tempInput) {
        double value;
        if(tempInput.matches("")){
            value = 2.5; return value;
        }

        value = Double.parseDouble(tempInput);
        value = value + 2.5; return value;
    }

    public double decrementWeight(String tempInput) {
        double value = 0;
        if(!tempInput.matches("") && Double.parseDouble(tempInput) >= 2.5){
            value = Double.parseDouble(tempInput);
            value = value - 2.5; return value;
        }
        return value;
    }

    public int incrementReps (String input) {
        int value;
        if(input.matches("")) {
            value = 1; return value;
        }
        value = Integer.parseInt(input);
        value = value + 1; return value;
    }

    public int decrementReps (String input) {
        int value = 0;
        if(!input.matches("") && Integer.parseInt(input) >= 1) {
            value = Integer.parseInt(input);
            value = value - 1; return value;
        }
        return value;
    }
}
