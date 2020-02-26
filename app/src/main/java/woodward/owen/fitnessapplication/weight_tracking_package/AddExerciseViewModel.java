package woodward.owen.fitnessapplication.weight_tracking_package;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

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
