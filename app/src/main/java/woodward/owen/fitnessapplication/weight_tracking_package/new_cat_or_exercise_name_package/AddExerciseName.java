package woodward.owen.fitnessapplication.weight_tracking_package.new_cat_or_exercise_name_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.AddEditMethods;
import woodward.owen.fitnessapplication.weight_tracking_package.ExerciseTrackingActivity;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.AddExerciseNameViewModel;

public class AddExerciseName extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "woodward.owen.fitnessApplication.EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "woodward.owen.fitnessApplication.EXTRA_CATEGORY_NAME";
    private TextView categoryTitleTextView;
    private EditText inputExerciseTextView;
    private AddExerciseNameViewModel addExerciseNameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_name);
        setToolBar();
        addExerciseNameViewModel = new ViewModelProvider(AddExerciseName.this).get(AddExerciseNameViewModel.class);
        Observe();

        Intent i = getIntent();
        if (i.hasExtra(EXTRA_CATEGORY_ID)) {
            addExerciseNameViewModel.setCatID(i.getIntExtra(EXTRA_CATEGORY_ID, 1));
            addExerciseNameViewModel.setCatName(i.getStringExtra(EXTRA_CATEGORY_NAME));

            categoryTitleTextView.setText(String.format("Category Selected - %s", addExerciseNameViewModel.getCatTitle().getValue()));
        }
    }

    private void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        categoryTitleTextView = findViewById(R.id.category_title_Add_exercise_name);
        inputExerciseTextView = findViewById(R.id.insert_Edit_Text_Exercise_Name);
        inputExerciseTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_exercise_name_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveExerciseName) {
            SaveExerciseName();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveExerciseName() {
        String exerciseName = inputExerciseTextView.getText().toString();
        int categoryID = 0;
        try {
            //Attempts to get the category ID to assign the new exercise type to
            categoryID = addExerciseNameViewModel.getCatID().getValue();
        } catch (Exception ex) {
            Toast.makeText(this, "Something Went Wrong " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        //Verifies whether the input is valid or not
        boolean verify = AddEditMethods.isVerifiedCatExercise(exerciseName);
        boolean verifyDifferentName = AddEditMethods.isTheSameExerciseName(Objects.requireNonNull(addExerciseNameViewModel.getAllExercisesForCategory().getValue()), exerciseName);
        if (verify) {

            if(!verifyDifferentName) {

                ExerciseName exerciseName1 = new ExerciseName(exerciseName, categoryID);
                addExerciseNameViewModel.Insert(exerciseName1);
                Toast.makeText(AddExerciseName.this, "New Exercise Type Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddExerciseName.this, ExerciseTrackingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Exercise Already Exists Within the Selected Category", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddExerciseName.this, "Please Enter a Valid Exercise Type", Toast.LENGTH_SHORT).show();
        }
    }

    private void Observe() {
        addExerciseNameViewModel.getAllExercisesForCategory().observe(this, exerciseNames -> {

        });
    }

}
