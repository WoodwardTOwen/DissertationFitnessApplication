package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.ExercisePackage.Category;
import woodward.owen.fitnessapplication.ExercisePackage.CategoryType;
import woodward.owen.fitnessapplication.ExercisePackage.Exercise;
import woodward.owen.fitnessapplication.ExercisePackage.IO;

public class AddExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;

    public AddExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
    }

    //Wrapper methods for the repository from the ViewModel
    public void Insert(Exercise exercise) {
        repository.Insert(exercise);
    }

    public void Update(Exercise exercise) {
        repository.Update(exercise);
    }
}
