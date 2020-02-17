package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;
import woodward.owen.fitnessapplication.R;

public class EditExercise extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "woodward.owen.fitnessapplication.EXTRA_ID";
    public static final String EXTRA_EXERCISE_NAME = "woodward.owen.fitnessapplication.EXTRA_NAME";
    public static final String EXTRA_WEIGHT = "woodward.owen.fitnessapplication.EXTRA_WEIGHT";
    public static final String EXTRA_REPS = "woodward.owen.fitnessapplication.EXTRA_REPS";
    public static final String EXTRA_RPE = "woodward.owen.fitnessapplication.EXTRA_RPE";
    private EditText editWeightInput;
    private EditText editRepInput;
    private EditText editRpeInput;
    private TextView exerciseTitle;


    private AddExerciseViewModel addExerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_black);
        Listen();
        setTitle("Update Exercise");

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            exerciseTitle.setText(intent.getStringExtra(EXTRA_EXERCISE_NAME));
            editWeightInput.setText(Double.toString(intent.getDoubleExtra(EXTRA_WEIGHT, 1.0)));
            editRepInput.setText(Integer.toString(intent.getIntExtra(EXTRA_REPS, 1)));
            editRpeInput.setText(Integer.toString(intent.getIntExtra(EXTRA_RPE, 1)));
        }

    }

    private void UpdateExercise() {
        String exerciseWeight = editWeightInput.getText().toString();
        String exerciseReps = editRepInput.getText().toString();
        String exerciseRPE = editRpeInput.getText().toString();
        String exerciseName = exerciseTitle.getText().toString();


        Intent data = new Intent();
        data.putExtra(EXTRA_EXERCISE_NAME, exerciseName);
        data.putExtra(EXTRA_WEIGHT, exerciseWeight);
        data.putExtra(EXTRA_REPS, exerciseReps);
        data.putExtra(EXTRA_RPE, exerciseRPE);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
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
                UpdateExercise();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Listen() {
        Button incrementWeight;
        Button decrementWeight;
        Button incrementReps;
        Button decrementReps;
        Button incrementRPE;
        Button decrementRPE;

        incrementWeight = findViewById(R.id.incrementWeightEditButton);
        decrementWeight = findViewById(R.id.decrementWeightEditButton);
        incrementReps = findViewById(R.id.incrementRepEditButton);
        decrementReps = findViewById(R.id.decrementRepEditButton);
        incrementRPE = findViewById(R.id.incrementRPEEditButton);
        decrementRPE = findViewById(R.id.decrementRPEEditButton);
        editWeightInput = findViewById(R.id.inputWeightTxtBox);
        editRepInput = findViewById(R.id.inputRepTxtBox);
        editRpeInput = findViewById(R.id.inputRPETxtBox);
        exerciseTitle = findViewById(R.id.editActivityExerciseName);

        incrementWeight.setOnClickListener(EditExercise.this);
        decrementWeight.setOnClickListener(EditExercise.this);
        incrementReps.setOnClickListener(EditExercise.this);
        decrementReps.setOnClickListener(EditExercise.this);
        incrementRPE.setOnClickListener(EditExercise.this);
        decrementRPE.setOnClickListener(EditExercise.this);


    }

    @Override
    public void onClick(View v) {
        String tempInput = editWeightInput.getText().toString().trim();
        String inputRep = editRepInput.getText().toString().trim();
        String inputRPE = editRpeInput.getText().toString().trim();
        switch (v.getId()) {
            case R.id.incrementWeightEditButton:
                editWeightInput.setText(Double.toString(AddEditMethods.incrementWeight(tempInput)));
                break;
            case R.id.decrementWeightEditButton:
                editWeightInput.setText(Double.toString(AddEditMethods.decrementWeight(tempInput)));
                break;
            case R.id.incrementRepEditButton:
                editRepInput.setText(Integer.toString(AddEditMethods.incrementRepsRPE(inputRep)));
                break;
            case R.id.decrementRepEditButton:
                editRepInput.setText(Integer.toString(AddEditMethods.decrementRepsRPE(inputRep)));
                break;
            case R.id.incrementRPEEditButton:
                editRpeInput.setText(Integer.toString(AddEditMethods.incrementRepsRPE(inputRPE)));
                break;
            case R.id.decrementRPEEditButton:
                editRpeInput.setText(Integer.toString(AddEditMethods.decrementRepsRPE(inputRPE)));
                break;

        }
    }
}
