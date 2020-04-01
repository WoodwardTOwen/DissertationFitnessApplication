package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;

public class ExerciseInfo extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "woodward.owen.fitnessapplication.EXTRA_ID";
    public static final String EXTRA_EXERCISE_NAME = "woodward.owen.fitnessapplication.EXTRA_NAME";
    public static final String EXTRA_WEIGHT = "woodward.owen.fitnessapplication.EXTRA_WEIGHT";
    public static final String EXTRA_REPS = "woodward.owen.fitnessapplication.EXTRA_REPS";
    public static final String EXTRA_RPE = "woodward.owen.fitnessapplication.EXTRA_RPE";
    private EditText editWeightInput;
    private EditText editRepInput;
    private EditText editRpeInput;
    private TextView exerciseTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.close_white);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        Listen();
        setTitle("Exercise Information");

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            exerciseTitle.setText(intent.getStringExtra(EXTRA_EXERCISE_NAME));
            editWeightInput.setText(Double.toString(intent.getDoubleExtra(EXTRA_WEIGHT, 1.0)));
            editRepInput.setText(Integer.toString(intent.getIntExtra(EXTRA_REPS, 1)));
            editRpeInput.setText(Integer.toString(intent.getIntExtra(EXTRA_RPE, 1)));
        }

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().setTitle("Gainz Tracker - Edit Exercise");  // In landscape
        }
    }

    private void UpdateExercise() {
        String exerciseWeight = editWeightInput.getText().toString();
        String exerciseReps = editRepInput.getText().toString();
        String exerciseRPE = editRpeInput.getText().toString();
        String exerciseName = exerciseTitle.getText().toString();

        boolean verify = AddEditMethods.isVerified(exerciseWeight, exerciseReps, exerciseRPE);

        if (verify) {
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
        } else {
            Toast.makeText(ExerciseInfo.this, "Please Ensure All Fields Have an Inputted Value", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.current_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.updateExercise) {
            UpdateExercise();
            return true;
        }
        if(item.getItemId() == R.id.exercise_graphical_analysis) {

            if(CheckIfItemsHaveChanged()) {
                ShowDialogForUnsavedChanges();
            }
            else  {
                showDialogForGraphicalAnalysis();
            }
        }
        return super.onOptionsItemSelected(item);
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

        incrementWeight.setOnClickListener(ExerciseInfo.this);
        decrementWeight.setOnClickListener(ExerciseInfo.this);
        incrementReps.setOnClickListener(ExerciseInfo.this);
        decrementReps.setOnClickListener(ExerciseInfo.this);
        incrementRPE.setOnClickListener(ExerciseInfo.this);
        decrementRPE.setOnClickListener(ExerciseInfo.this);
    }

    @SuppressLint("SetTextI18n")
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
                if (editRpeInput.getText().toString().equals("10")) {
                    break;
                }
                editRpeInput.setText(Integer.toString(AddEditMethods.incrementRepsRPE(inputRPE)));
                break;
            case R.id.decrementRPEEditButton:
                editRpeInput.setText(Integer.toString(AddEditMethods.decrementRepsRPE(inputRPE)));
                break;

        }
    }


    private void showDialogForGraphicalAnalysis() {
        Dialog dialog = new Dialog(ExerciseInfo.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_graphical_option);

        Button bntWeight = dialog.findViewById(R.id.graphical_Analysis_Max_Weight_Button);
        Button bntVolume = dialog.findViewById(R.id.graphical_Analysis_Max_Volume_Button);
        bntWeight.setOnClickListener(v -> {

            Intent intentMaxWeight = new Intent(ExerciseInfo.this, GraphicalLoadingScreen.class);
            intentMaxWeight.putExtra(GraphicalLoadingScreen.EXTRA_EXERCISE_TITLE, exerciseTitle.getText().toString());
            intentMaxWeight.putExtra(GraphicalLoadingScreen.EXTRA_GRAPHICAL_OPTION, "MaxWeight");
            startActivity(intentMaxWeight);

            dialog.dismiss();
        });

        bntVolume.setOnClickListener(v -> {

            Intent intentMaxVolume = new Intent(ExerciseInfo.this, GraphicalLoadingScreen.class);
            intentMaxVolume.putExtra(GraphicalLoadingScreen.EXTRA_EXERCISE_TITLE, exerciseTitle.getText().toString());
            intentMaxVolume.putExtra(GraphicalLoadingScreen.EXTRA_GRAPHICAL_OPTION, "MaxVolume");
            startActivity(intentMaxVolume);

            dialog.dismiss();
        });

        dialog.show();
    }

    private boolean CheckIfItemsHaveChanged () {
        String exerciseWeight = editWeightInput.getText().toString();
        String exerciseReps = editRepInput.getText().toString();
        String exerciseRPE = editRpeInput.getText().toString();

        Intent intent = getIntent();
        String exerciseValue = Double.toString(intent.getDoubleExtra(EXTRA_WEIGHT, 1.0));
        String repValue = Integer.toString(intent.getIntExtra(EXTRA_REPS, 1));
        String rpeValue = Integer.toString(intent.getIntExtra(EXTRA_RPE, 1));


        boolean exerciseWeightBool = AddEditMethods.isTheSameValue(exerciseWeight, exerciseValue);
        boolean exerciseRepBool = AddEditMethods.isTheSameValue(exerciseReps, repValue);
        boolean exerciseRPEBool = AddEditMethods.isTheSameValue(exerciseRPE, rpeValue);

        return !exerciseWeightBool || !exerciseRepBool || !exerciseRPEBool;

    }


    private void ShowDialogForUnsavedChanges () {
        Dialog dialog = new Dialog(ExerciseInfo.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_warning);

        Button bntContinue = dialog.findViewById(R.id.continue_dialog_button_graphical);
        Button btnReturn = dialog.findViewById(R.id.go_back_dialog_button);
        bntContinue.setOnClickListener(v -> {
            dialog.dismiss();
            showDialogForGraphicalAnalysis();
        });

        btnReturn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        if(CheckIfItemsHaveChanged()){

        }
        else {
            super.onBackPressed();
        }
    }

    private void unSavedChangesDialog () {
        Dialog dialog = new Dialog(ExerciseInfo.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alert_unsaved_changes);

        Button applyChanges = dialog.findViewById(R.id.apply_changes_unsaved_changes_dialog);
        Button neglectChanges = dialog.findViewById(R.id.do_not_apply_changes_un_saved_changes_dialog);
        TextView weightTV = dialog.findViewById(R.id.weightTextViewUnsavedChanges), repTV = dialog.findViewById(R.id.repsTextViewUnsavedChanges),
                rpeTV = dialog.findViewById(R.id.rpeTextViewUnsavedChanges), newWeightTV = dialog.findViewById(R.id.newWeightTextViewUnsavedChange),
                newRepsTV = dialog.findViewById(R.id.newRepsTextViewUnsavedChanges), newRpeTV = dialog.findViewById(R.id.newRPETextViewUnsavedChanges);

        Intent intent = getIntent();
        String exerciseValue = Double.toString(intent.getDoubleExtra(EXTRA_WEIGHT, 1.0)),
                repValue = Integer.toString(intent.getIntExtra(EXTRA_REPS, 1)),
                rpeValue = Integer.toString(intent.getIntExtra(EXTRA_RPE, 1));

        String exerciseWeight = editWeightInput.getText().toString(), exerciseReps = editRepInput.getText().toString(),
                exerciseRPE = editRpeInput.getText().toString();



        weightTV.setText(exerciseValue); weightTV.setTextColor(Color.RED);
        repTV.setText(repValue); repTV.setTextColor(Color.RED);
        rpeTV.setText(rpeValue); rpeTV.setTextColor(Color.RED);

        newWeightTV.setText(exerciseWeight); newWeightTV.setTextColor(Color.GREEN);
        newRepsTV.setText(exerciseReps); newRepsTV.setTextColor(Color.GREEN);
        newRpeTV.setText(exerciseRPE); newRpeTV.setTextColor(Color.GREEN);


        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateExercise();
                dialog.dismiss();
                ExerciseInfo.super.onBackPressed();
            }
        });

        neglectChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ExerciseInfo.super.onBackPressed();
            }
        });

        dialog.show();

    }
}
