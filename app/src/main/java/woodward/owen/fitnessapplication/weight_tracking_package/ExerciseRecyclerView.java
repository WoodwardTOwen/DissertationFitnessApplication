package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
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
    public static final String EXTRA_DATE_EXERCISE = "woodward.owen.fitnessapplication.EXTRA_EXERCISE";
    private String currentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_recycler_view);
        setToolBar();
        exerciseNameViewModel = new ViewModelProvider(ExerciseRecyclerView.this).get(ExerciseNameViewModel.class);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_DATE_EXERCISE)){
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

        adapter.setOnItemClickListener(new ExerciseNameAdapter.onItemClickListener() { //Implemented Adapter Listener
            @Override
            public void onItemClick(ExerciseName exerciseName) {
                Intent intent = new Intent(ExerciseRecyclerView.this, AddExercise.class);
                //Pass string for exercise and category
                intent.putExtra("Exercise", exerciseName.getExerciseName());
                assert category != null;
                intent.putExtra("Category", category.getCategoryName());
                intent.putExtra(AddExercise.EXTRA_DATE, exerciseNameViewModel.getCurrentDate().getValue());
                startActivity(intent);
            }
        });
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
        exerciseNameViewModel.getAllExercisesForCategory().observe(this, new Observer<List<ExerciseName>>() {
            @Override
            public void onChanged(List<ExerciseName> exerciseNames) {
                adapter.submitList(exerciseNames);
            }
        });
    }

    private void setToolBar(){
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        getSupportActionBar().setTitle(R.string.ChooseExerciseNameHeading);
    }
}
