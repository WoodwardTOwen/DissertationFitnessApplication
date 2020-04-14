package woodward.owen.fitnessapplication.weight_tracking_package;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

class GraphicalAnalysisMethods {
    //The dates are stored in a different way in comparison to the standard for simpleDateFormat thus need to be converted for sorting
    static void convertDates(List<Exercise> exercises) {
        String str;
        for(int x= 0; x< exercises.size(); x++){
            str = exercises.get(x).getDate();
            str = str.replace("-", "/");
            exercises.get(x).setDate(str);
        }
    }

    //Gets all the dates in order so they are not all over the place
    static void sortDatesInOrder(List<Exercise> exercises) {
        Collections.sort(exercises, new Comparator<Exercise>() {
            @SuppressLint("SimpleDateFormat")
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Exercise exerciseDate1, Exercise exerciseDate2) {
                try{
                    return Objects.requireNonNull(format.parse(exerciseDate1.getDate())).compareTo(format.parse(exerciseDate2.getDate()));
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

    static List<Exercise> sortDataForWeightEntries(List<Exercise> exercises) {
        String currentDate = "";
        List<Exercise> tempStorageList = new ArrayList<>(); // Every exercise for one a certain date
        List<Exercise> newExerciseData = new ArrayList<>(); // Finalised list that will be used for graphical analysis
        int counter = 0;

        for (Exercise x : exercises) {
            counter++;
            if (x.getDate().equals(currentDate)) {
                tempStorageList.add(x);
            } else if (tempStorageList.size() != 0 && !x.getDate().equals(currentDate)) {
                newExerciseData.add(findHighestWeightForDate(tempStorageList));
                currentDate = x.getDate();
                tempStorageList.clear();
                tempStorageList.add(x);
            }
            else {
                currentDate = x.getDate();
                tempStorageList.clear(); //It's a new date so the list should be cleared to review the next date
                tempStorageList.add(x);
            }

            //Final Check
            if(counter == exercises.size()) {
                newExerciseData.add(findHighestWeightForDate(tempStorageList));
            }

        }
        return newExerciseData;

    }

    private static Exercise findHighestWeightForDate (List<Exercise> exercises) {
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


    static List<Exercise> sortDataForVolumeDisplay(List<Exercise> exercises) {
        String currentDate = "";
        List<Exercise> tempStorageList = new ArrayList<>(); // Every exercise for one a certain date
        List<Exercise> newExerciseData = new ArrayList<>(); // Finalised list that will be used for graphical analysis
        int counter = 0;

        for (Exercise x : exercises) {
            counter++;
            if (x.getDate().equals(currentDate)) {
                tempStorageList.add(x);
            } else if (tempStorageList.size() != 0 && !x.getDate().equals(currentDate)) {
                newExerciseData.add(findHighestVolumeForDate(tempStorageList));
                currentDate = x.getDate();
                tempStorageList.clear();
                tempStorageList.add(x);
            } else {
                currentDate = x.getDate();
                tempStorageList.clear(); //It's a new date so the list should be cleared to review the next date
                tempStorageList.add(x);
            }

            //Final Check
            if(counter == exercises.size()) {
                newExerciseData.add(findHighestVolumeForDate(tempStorageList));
            }

        }
        return newExerciseData;

    }

    private static Exercise findHighestVolumeForDate (List<Exercise> exercises) {
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


    static String FindXAxisValue(List<Exercise> exercises, int xValue) {
        int counter = 0;
        for(Exercise ex : exercises) {
            if(counter == xValue) {
                return ex.getDate();
            }
            counter++;
        }
        return "Something Went Wrong";
    }
}
