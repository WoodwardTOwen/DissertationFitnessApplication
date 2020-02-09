package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;


    public ExerciseViewModel (@NonNull Application application){
        super(application);
//        repository = new ExerciseRepository(application);
//        allExercises = repository.getAllExercises();
    }


}
