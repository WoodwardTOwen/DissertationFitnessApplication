package woodward.owen.fitnessapplication.graphical_analysis_package;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.CategoryType;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.exercise_package.IO;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseRepository;

public class GraphViewModel extends AndroidViewModel {
    private List<Entry> entries;
    private static final Map<CategoryType, List<String>> PossibleNames = new Hashtable<>();
    private static final Map<CategoryType, Category> Categories = new Hashtable<>();
    private ArrayList<String> exerciseList;
    private ExerciseRepository repository;
    private MutableLiveData<String> exerciseName = new MutableLiveData<>();
    private LiveData<List<Exercise>> listOfExerciseData = Transformations.switchMap(exerciseName, (exercise) -> repository.getAllExercises(exercise));

    public GraphViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
    }

    public void AddEntry(Entry e) {

    }

    public void RemoveEntry(Entry e) {

    }

    public LiveData<List<Exercise>> getAllExercises() {
        return listOfExerciseData;
    }

    public void setExerciseName(String date) {
        exerciseName.setValue(date);
    }

    public MutableLiveData<String> getExerciseName() {
        return exerciseName;
    }

    private void setEntries () {
        for(int x = 0; x < Objects.requireNonNull(listOfExerciseData.getValue()).size(); x++) {
            entries.add(new Entry(listOfExerciseData.getValue().get(x).getReps(),
                    convertToFloat(listOfExerciseData.getValue().get(x).getWeight())));
        }
    }

    private List<Entry> getAllEntries() { return entries;}

    private static Float convertToFloat(double doubleValue) {
        return (float) doubleValue;
    }


    /*public void assignIOValues(Application context) {
        IO.setInstance(context);
        Map<CategoryType, String[]> pair = new Hashtable<>(IO.ReadData());
        for (Map.Entry<CategoryType, String[]> entry : pair.entrySet()) {
            PossibleNames.put(entry.getKey(), new ArrayList<>(Arrays.asList(entry.getValue())));
        }

        for (CategoryType type : CategoryType.values()) {

            switch (type) {
                case CARDIO:
                    Categories.put(type, new Category(type, false));
                    break;
                default:
                    Categories.put(type, new Category(type, true));
                    break;
            }
        }

    }*/

    public Map<CategoryType, List<String>> getPossibleNames () {
        return PossibleNames;
    }

    public void setExerciseList (List<String> entryValue) {
        if (exerciseList == null) {
            exerciseList = new ArrayList<>();
        }
        exerciseList.clear();
        exerciseList.addAll(entryValue);

    }

    public List<String> getExerciseList () {
        return exerciseList;
    }


}
