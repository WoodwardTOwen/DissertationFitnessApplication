package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

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
    private LiveData<List<Exercise>> listOfExercisesGraphical = Transformations.switchMap(currentName, (name) -> repository.GetAllDataForExerciseType(name));

    public GraphicalViewModel(@NonNull Application application) {
        super(application);
        repository = ExerciseRepository.getInstance(application);
    }

    //Gets all the exercise data from the db via the name
    public LiveData<List<Exercise>> getListOfExercisesGraphical() { return listOfExercisesGraphical; }

    public MutableLiveData<String> getCurrentName() { return currentName;}

    public void setName(String name) {
        currentName.setValue(name);
    }

    public float convertToFloat (double value) {
        return (float)value;
    }
}
