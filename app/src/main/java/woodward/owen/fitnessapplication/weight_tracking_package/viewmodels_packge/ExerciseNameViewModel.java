package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class ExerciseNameViewModel extends AndroidViewModel {

    private MutableLiveData<Category> currentCategory = new MutableLiveData<>();
    private MutableLiveData<String> currentDate = new MutableLiveData<>();
    private ExerciseRepository repository;
    private LiveData<List<ExerciseName>> allExercisesForCategory = Transformations.switchMap(currentCategory, (currentCategory) -> repository.FindExercisesForCategory(currentCategory.getId()));

    public ExerciseNameViewModel(@NonNull Application application) {
        super(application);
        this.repository = ExerciseRepository.getInstance(application);
    }

    public void DeleteExerciseName (ExerciseName exerciseName) {repository.DeleteExerciseName(exerciseName);}

    /*public void UpdateExerciseName (ExerciseName exerciseName) {repository.UpdateExerciseName(exerciseName);}*/

    public void setDate(String date) {
        currentDate.setValue(date);
    }

    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }

    public MutableLiveData<Category> getCurrentCategory () {
        return currentCategory;
    }

    public LiveData<List<ExerciseName>> getAllExercisesForCategory() {
        return allExercisesForCategory;
    }
    public void setCurrentCategory(Category category) {
        currentCategory.setValue(category);
    }

}

