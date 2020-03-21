package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class CategoryViewModel extends AndroidViewModel {
    private final ExerciseRepository repository;
    private final MutableLiveData<String> currentDate = new MutableLiveData<>();
    private final LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.repository = ExerciseRepository.getInstance(application);
        this.allCategories = repository.GetAllCategories();
    }

    public void Delete(Category category){
        repository.DeleteCategory(category);
    }

    /*public void Update(Category category){
        repository.UpdateCategory(category);
    }*/

    public LiveData<List<Category>> getAllCategories () {
        return allCategories;
    }

    public void setDate(String date) {
        currentDate.setValue(date);
    }

    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }

}
