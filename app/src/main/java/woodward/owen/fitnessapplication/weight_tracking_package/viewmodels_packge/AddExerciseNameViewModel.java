package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class AddExerciseNameViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> catID = new MutableLiveData<>();
    private MutableLiveData<String> catName = new MutableLiveData<>();
    private ExerciseRepository repository;

    public AddExerciseNameViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
    }

    public void Insert(ExerciseName exercise) {
        repository.InsertExerciseName(exercise);
    }

    public void setCatID (int value) {
        catID.setValue(value);
    }

    public void setCatName (String value) {
        catName.setValue(value);
    }

    public MutableLiveData<Integer> getCatID () {
        return catID;
    }

    public MutableLiveData<String> getCatTitle () {
        return catName;
    }

}
