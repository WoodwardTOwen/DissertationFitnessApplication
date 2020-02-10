package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.ExercisePackage.Category;
import woodward.owen.fitnessapplication.ExercisePackage.CategoryType;
import woodward.owen.fitnessapplication.ExercisePackage.IO;
import woodward.owen.fitnessapplication.PlateMathCalculatorPackage.BarbellType;
import woodward.owen.fitnessapplication.R;

public class AddExercise extends Activity {

    private static final Map<CategoryType, List<String>> PossibleNames = new HashMap<>();
    private static final Map<CategoryType, Category> Categories = new HashMap<>();
    private Spinner catSpinner;
    private Spinner exerciseSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_layout_file);
        assignIOValues(getApplicationContext());
        checkSelectedItem();
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
                exerciseSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
