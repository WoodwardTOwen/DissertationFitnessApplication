package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseDatabase;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class ExerciseViewModel extends AndroidViewModel {
    private static ExerciseRepository repository;
    private static MutableLiveData<Exercise> cachedExercise;
    private MutableLiveData<String> currentDate = new MutableLiveData<>();
    private MutableLiveData<String> currentName = new MutableLiveData<>();
    private LiveData<List<Exercise>> allExercises = Transformations.switchMap(currentDate, (date) -> repository.GetAllExercises(date));
    private LiveData<List<Exercise>> listOfExercisesGraphical = Transformations.switchMap(currentName, (name) -> repository.GetAllDataForExerciseType(name));

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);

    }

    private static LiveData<List<Exercise>> ProcessData (String name) {
            return repository.GetAllDataForExerciseType(name);
    }
    //Wrapper methods for the repository from the ViewModel
    public void Insert(Exercise exercise) {
        repository.Insert(exercise);
    }

    public void Update(Exercise exercise) {
        repository.Update(exercise);
    }

    //public void UpdateMovedExercise(Exercise exercise) {repository.UpdateMovedExercises(exercise);}

    public void Delete(Exercise exercise) {
        repository.Delete(exercise);
    }

    public void DeleteAllExercises(String date) {
        repository.DeleteAllExercises(date);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<List<Exercise>> getListOfExercisesGraphical() { return listOfExercisesGraphical; }

    public void setDate(String date) {
        currentDate.setValue(date);
    }

    public void setName(String name) {
        currentName.setValue(name);
    }

    public MutableLiveData<String> getCurrentName() { return currentName;}

    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }

    public MutableLiveData<Exercise> getCurrentCachedExercise() {
        return cachedExercise;
    }

    public void setCachedExercise(Exercise e) {
        if (cachedExercise == null) {
            cachedExercise = new MutableLiveData<>();
        }
        cachedExercise.setValue(e);
    }

    //Conversion of date to stop data conversion conflicts
    public String ConvertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + (input);
        }
    }
}
