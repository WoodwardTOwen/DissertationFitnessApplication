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

    //The dates are stored in a different way in comparison to the standard for simpleDateFormat thus need to be converted for sorting
    public void convertDates (List<Exercise> exercises) {
        String str;
        for(int x= 0; x< exercises.size(); x++){
            str = exercises.get(x).getDate();
            str = str.replace("-", "/");
            exercises.get(x).setDate(str);
        }
    }

    //Gets all the dates in order so they are not all over the place
    public void sortDatesInOrder(List<Exercise> exercises) {
        Collections.sort(exercises, new Comparator<Exercise>() {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Exercise exerciseDate1, Exercise exerciseDate2) {
                try{
                    return format.parse(exerciseDate1.getDate()).compareTo(format.parse(exerciseDate2.getDate()));
                }
                catch (ParseException ex) {
                    throw new IllegalArgumentException(ex);
                }
            }
        });
    }

    /**
     * Algorithm that sorts everything for transaction of graphical analysis data
     * @param exercises -> data based on one exercise that is currently being reviewed
     * @return the new list of exercises that will be thrown over to the graphical analysis activity
     */

    public List<Exercise> sortDataForWeightEntries(List<Exercise> exercises) {
        String currentDate = "";
        List<Exercise> tempStorageList = new ArrayList<>(); // Every exercise for one a certain date
        List<Exercise> newExerciseData = new ArrayList<>(); // Finalised list that will be used for graphical analysis

        for (int x = 0; x < exercises.size(); x++) {

            if (exercises.get(x).getDate().equals(currentDate)) {
                tempStorageList.add(exercises.get(x));
                if (x == exercises.size() - 1) { //we know its the last instance and should add the last one no matter what
                    newExerciseData.add(findHighestWeightForDate(tempStorageList));
                }
            } else if (tempStorageList.size() != 0 && !exercises.get(x).getDate().equals(currentDate)) {
                newExerciseData.add(findHighestWeightForDate(tempStorageList));
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear();
                tempStorageList.add(exercises.get(x));
            } else {
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear(); //It's a new date so the list should be cleared to review the next date
                tempStorageList.add(exercises.get(x));
                if (x == exercises.size() - 1) { //we know its the last instance and should add the last one no matter what
                    newExerciseData.add(findHighestWeightForDate(tempStorageList));
                }
            }

        }
        return newExerciseData;

    }

    private Exercise findHighestWeightForDate (List<Exercise> exercises) {
        Exercise bestExercise = new Exercise();
        double currentHighest = 0;
        for(int x = 0; x < exercises.size(); x++) {
            if(exercises.get(x).getWeight() > currentHighest){
                currentHighest = exercises.get(x).getWeight();
                bestExercise = exercises.get(x);
            }
        }
        return bestExercise;
    }


    public List<Exercise> sortDataForVolumeDisplay(List<Exercise> exercises) {
        String currentDate = "";
        List<Exercise> tempStorageList = new ArrayList<>(); // Every exercise for one a certain date
        List<Exercise> newExerciseData = new ArrayList<>(); // Finalised list that will be used for graphical analysis

        for (int x = 0; x < exercises.size(); x++) {

            if (exercises.get(x).getDate().equals(currentDate)) {
                tempStorageList.add(exercises.get(x));
                if (x == exercises.size() - 1) { //we know its the last instance and should add the last one no matter what
                    newExerciseData.add(findHighestVolumeForDate(tempStorageList));
                }
            } else if (tempStorageList.size() != 0 && !exercises.get(x).getDate().equals(currentDate)) {
                newExerciseData.add(findHighestVolumeForDate(tempStorageList));
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear();
                tempStorageList.add(exercises.get(x));
            } else {
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear(); //It's a new date so the list should be cleared to review the next date
                tempStorageList.add(exercises.get(x));
                if (x == exercises.size() - 1) { //we know its the last instance and should add the last one no matter what
                    newExerciseData.add(findHighestVolumeForDate(tempStorageList));
                }
            }

        }
        return newExerciseData;

    }

    private Exercise findHighestVolumeForDate (List<Exercise> exercises) {
        String name ="", date = "";
        double totalVolume = 0;

        for(int x = 0; x < exercises.size(); x++){
            totalVolume = totalVolume + (exercises.get(x).getWeight() * exercises.get(x).getReps());
            if(x == exercises.size() -1 ) { //To stop exerciseName and Date being continuously reassigned every iteration (unneeded reassignments)
                name = exercises.get(x).getExerciseName();
                date = exercises.get(x).getDate();
            }
        }

        return new Exercise(name, totalVolume, date);
    }
}
