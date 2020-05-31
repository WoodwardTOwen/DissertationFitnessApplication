package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class ExerciseViewModel extends AndroidViewModel {
    private static ExerciseRepository repository;
    private static MutableLiveData<Exercise> cachedExercise;
    private final MutableLiveData<String> currentDate = new MutableLiveData<>();
    private final LiveData<List<Exercise>> allExercisesByDate = Transformations.switchMap(currentDate, (date) -> repository.GetAllExercisesByDate(date));

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);
    }

    //Used for UNDO function when deleting exercises was by accident
    public void Insert(Exercise exercise) {
        repository.Insert(exercise);
    }

    //Update Exercise Data in Model
    public void Update(Exercise exercise) {
        repository.Update(exercise);
    }

    //Remove Exercise Data from Model
    public void Delete(Exercise exercise) {
        repository.Delete(exercise);
    }

    //Removes all exercise data for a specific date
    public void DeleteAllExercises(String date) {
        repository.DeleteAllExercises(date);
    }

    //Gets all the exercises from the db via the date
    public LiveData<List<Exercise>> GetAllExercisesByDate() {
        return allExercisesByDate;
    }

    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }

    public MutableLiveData<Exercise> getCurrentCachedExercise() {
        return cachedExercise;
    }

    public void setDate(String date) {
        currentDate.setValue(date);
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
