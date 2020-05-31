package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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
import woodward.owen.fitnessapplication.weight_tracking_package.adapters_package.CategoryAdapter;
import woodward.owen.fitnessapplication.weight_tracking_package.new_cat_or_exercise_name_package.AddCategory;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.CategoryViewModel;

public class CategoryRecyclerView extends AppCompatActivity {

    private CategoryAdapter adapter;
    private CategoryViewModel categoryViewModel;
    public static final String EXTRA_DATE_CATEGORY = "woodward.owen.fitnessApplication.EXTRA_DATE_CATEGORY";
    private TextView emptyView;
    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_recycler_view);
        setToolBar();
        categoryViewModel = new ViewModelProvider(CategoryRecyclerView.this).get(CategoryViewModel.class);
        emptyView = findViewById(R.id.no_categories_available_TextView);
        //Attempt to get intent in order to maintain persists of date for the inputted exercise
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DATE_CATEGORY)) {
            String dateForExercise = intent.getStringExtra(EXTRA_DATE_CATEGORY);
            categoryViewModel.setDate(dateForExercise);
        }


        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); //set true if we know the recycler view size will not change
        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);
        Observe();

        //Implemented Adapter Listener
        adapter.setOnItemClickListener(this::openExerciseItemList);
        adapter.setOnItemLongClickListener(category -> {
            DisplayDialogBox(category);
            return true;
        });

    }

    private void openExerciseItemList(Category category) {
        Intent intent = new Intent(CategoryRecyclerView.this, ExerciseRecyclerView.class);
        intent.putExtra("selectedCategory", category);
        intent.putExtra(ExerciseRecyclerView.EXTRA_DATE_EXERCISE, categoryViewModel.getCurrentDate().getValue());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addCategoryMenuItem) {
            Intent intent = new Intent(CategoryRecyclerView.this, AddCategory.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Observe() {
        categoryViewModel.getAllCategories().observe(this, categories -> {
            adapter.submitList(categories);
            if (categories.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        });
    }

    private void setToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        getSupportActionBar().setTitle(R.string.ChooseCategoryHeading);
    }

    private void DisplayDialogBox (Category category) {
        Dialog dialog = new Dialog(CategoryRecyclerView.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_warning);

        //Setting up controls
        Button bntRemoveNo = dialog.findViewById(R.id.remove_button_no), bntRemoveYes = dialog.findViewById(R.id.remove_button_yes);
        TextView currentItem = dialog.findViewById(R.id.nameOfItemWishingToDelete); currentItem.setText(category.getCategoryName());

        bntRemoveYes.setOnClickListener(v -> {
            categoryViewModel.Delete(category);
            Toast.makeText(getApplicationContext(), "Successfully Removed " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            Intent intent1 = new Intent(CategoryRecyclerView.this, ExerciseTrackingActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent1);
            finish();
        });

        bntRemoveNo.setOnClickListener(v -> {
            //Cancel Procedure -> do NOT remove barbell
            Toast.makeText(CategoryRecyclerView.this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
