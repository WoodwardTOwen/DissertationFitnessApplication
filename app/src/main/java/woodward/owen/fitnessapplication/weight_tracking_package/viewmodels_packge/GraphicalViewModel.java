package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class GraphicalViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private MutableLiveData<String> currentName = new MutableLiveData<>();
    private MutableLiveData<List<String>> exerciseDates = new MutableLiveData<>();

    private LiveData<List<Exercise>> listOfExercisesGraphical = Transformations.switchMap(currentName, (name) -> repository.GetAllDataForExerciseType(name));
    private LiveData<List<Exercise>> listOfExercisesGraphicalWeekly = Transformations.switchMap(exerciseDates, (exerciseDates) ->
            repository.GetAllExercisesWeeklyVolume(exerciseDates.get(0), exerciseDates.get(1), exerciseDates.get(2), exerciseDates.get(3),exerciseDates.get(4),
                    exerciseDates.get(5), exerciseDates.get(6)));

    public GraphicalViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);
    }

    //Gets all the exercise data from the db via the name
    public LiveData<List<Exercise>> getListOfExercisesGraphical() { return listOfExercisesGraphical; }

    public LiveData<List<Exercise>> getListOfExercisesGraphicalWeekly() { return  listOfExercisesGraphicalWeekly; }

    public MutableLiveData<List<String>> getExerciseDates() { return exerciseDates; }

    public MutableLiveData<String> getCurrentName() { return currentName;}

    public void setName(String name) {
        currentName.setValue(name);
    }

    public void setExerciseDates (List<String> dates){
        exerciseDates.setValue(dates);
    }

    public float convertToFloat (double value) {
        return (float)value;
    }
}
