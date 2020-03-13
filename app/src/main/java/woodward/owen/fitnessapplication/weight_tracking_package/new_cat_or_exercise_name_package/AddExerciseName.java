package woodward.owen.fitnessapplication.weight_tracking_package.new_cat_or_exercise_name_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.AddExerciseNameViewModel;

public class AddExerciseName extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "woodward.owen.fitnessapplication.EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "woodward.owen.fitnessapplication.EXTRA_CATEGORY_NAME";
    private TextView categoryTitleTextView;
    private AddExerciseNameViewModel addExerciseNameViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_name);
        setToolBar();
        addExerciseNameViewModel = new ViewModelProvider(AddExerciseName.this).get(AddExerciseNameViewModel.class);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_CATEGORY_ID)){
            addExerciseNameViewModel.setCatID(i.getIntExtra(EXTRA_CATEGORY_ID, 1));
            addExerciseNameViewModel.setCatName(i.getStringExtra(EXTRA_CATEGORY_NAME));

            categoryTitleTextView.setText(String.format("Category Selected - %s", addExerciseNameViewModel.getCatTitle().getValue()));
        }
    }

    private void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        categoryTitleTextView = findViewById(R.id.category_title_Add_exercise_name);
    }


}
