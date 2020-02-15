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

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
    }

    //Wrapper methods for the repository from the ViewModel
    public void Insert(Exercise exercise) {
        repository.Insert(exercise);
    }

    public void Update(Exercise exercise) {
        repository.Update(exercise);
    }

    public void Delete(Exercise exercise){
        repository.Delete(exercise);
    }

    public void DeleteAllExercises(){
        repository.DeleteAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

}