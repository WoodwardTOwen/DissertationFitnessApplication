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
import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.weight_tracking_package.AddEditMethods;
import woodward.owen.fitnessapplication.weight_tracking_package.ExerciseTrackingActivity;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.AddCategoryViewModel;

public class AddCategory extends AppCompatActivity {

    private AddCategoryViewModel addCategoryViewModel;
    private EditText inputValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        setToolBar();
        addCategoryViewModel = new ViewModelProvider(AddCategory.this).get(AddCategoryViewModel.class);
    }

    private void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        inputValue = findViewById(R.id.insert_Edit_Text_Category_Name);
        inputValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveCategory) {
            saveCategory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCategory() {
        String categoryName = inputValue.getText().toString();

        boolean verify = AddEditMethods.isVerifiedCatExercise(categoryName);
        if (verify) {
            Category category = new Category(categoryName);
            addCategoryViewModel.Insert(category);
            Toast.makeText(AddCategory.this, "New Category Type Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddCategory.this, ExerciseTrackingActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(AddCategory.this, "Please Enter a Valid Category Type", Toast.LENGTH_SHORT).show();
        }

    }
}
