package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;


public class AddCategoryViewModel extends AndroidViewModel {

    private ExerciseRepository repository;

    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);
    }

    public void Insert(Category category) {
        repository.InsertCategory(category);
    }
}
