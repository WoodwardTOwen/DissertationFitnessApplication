package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;


public class AddCategoryViewModel extends AndroidViewModel {

    private final ExerciseRepository repository;
    private final LiveData<List<Category>> retrievedListOfCategories;

    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);
        retrievedListOfCategories = repository.FindCatNames();
    }

    public void Insert(Category category) {
        repository.InsertCategory(category);
    }

    public LiveData<List<Category>> getAllCategories () {
        return retrievedListOfCategories;
    }
}
