package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.adapters_package.ExerciseNameAdapter;
import woodward.owen.fitnessapplication.weight_tracking_package.new_cat_or_exercise_name_package.AddExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseNameViewModel;

public class ExerciseRecyclerView extends AppCompatActivity {

    private ExerciseNameAdapter adapter;
    private ExerciseNameViewModel exerciseNameViewModel;
    public static final String EXTRA_DATE_EXERCISE = "woodward.owen.fitnessApplication.EXTRA_EXERCISE";
    private String currentDate;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_recycler_view);
        setToolBar();
        exerciseNameViewModel = new ViewModelProvider(ExerciseRecyclerView.this).get(ExerciseNameViewModel.class);
        emptyView = findViewById(R.id.no_exercise_types_available_textview);

        Intent i = getIntent();
        if (i.hasExtra(EXTRA_DATE_EXERCISE)) {
            currentDate = i.getStringExtra(EXTRA_DATE_EXERCISE);
            exerciseNameViewModel.setDate(currentDate);
        }

        Category category = (Category) i.getSerializableExtra("selectedCategory");
        exerciseNameViewModel.setCurrentCategory(category);

        RecyclerView recyclerView = findViewById(R.id.exerciseRecycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //set true if we know the recycler view size will not change

        adapter = new ExerciseNameAdapter();
        recyclerView.setAdapter(adapter);
        Observe();

        //Implemented Adapter Listener
        adapter.setOnItemClickListener(exerciseName -> {
            Intent intent = new Intent(ExerciseRecyclerView.this, AddExercise.class);
            //Pass string for exercise and category
            intent.putExtra("Exercise", exerciseName.getExerciseName());
            assert category != null;
            intent.putExtra("Category", category.getCategoryName());
            intent.putExtra(AddExercise.EXTRA_DATE, exerciseNameViewModel.getCurrentDate().getValue());
            startActivity(intent);
        });

        adapter.setOnItemLongClickListener(this::DisplayDialogBox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.exercise_name_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addExerciseNameMenuItem) {
            Intent intent = new Intent(ExerciseRecyclerView.this, AddExerciseName.class);
            intent.putExtra(AddExerciseName.EXTRA_CATEGORY_ID, Objects.requireNonNull(exerciseNameViewModel.getCurrentCategory().getValue()).getId());
            intent.putExtra(AddExerciseName.EXTRA_CATEGORY_NAME, exerciseNameViewModel.getCurrentCategory().getValue().getCategoryName());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Observe() {
        exerciseNameViewModel.getAllExercisesForCategory().observe(this, exerciseNames -> {
            adapter.submitList(exerciseNames);
            if (exerciseNames.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        });
    }

    private void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        getSupportActionBar().setTitle(R.string.ChooseExerciseNameHeading);
    }

    private void DisplayDialogBox (ExerciseName exerciseName) {
        Dialog dialog = new Dialog(ExerciseRecyclerView.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_warning);

        //Setting up controls
        Button bntRemoveNo = dialog.findViewById(R.id.remove_button_no), bntRemoveYes = dialog.findViewById(R.id.remove_button_yes);
        TextView currentItem = dialog.findViewById(R.id.nameOfItemWishingToDelete); currentItem.setText(exerciseName.getExerciseName());

        bntRemoveYes.setOnClickListener(v -> {
            exerciseNameViewModel.DeleteExerciseName(exerciseName);
            Toast.makeText(getApplicationContext(), "Successfully Removed " + exerciseName.getExerciseName(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            Intent intent1 = new Intent(ExerciseRecyclerView.this, ExerciseTrackingActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent1);
            finish();
        });

        bntRemoveNo.setOnClickListener(v -> {
            //Cancel Procedure -> do NOT remove barbell
            Toast.makeText(ExerciseRecyclerView.this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
