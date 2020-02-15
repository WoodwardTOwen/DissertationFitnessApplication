package woodward.owen.fitnessapplication.ExercisePackage;

import androidx.room.Entity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


@Entity (tableName = "category_table")
public class Category {

    //private isWeighLifting
    //Type of exercise has been removed for now -> redundant boolean -> isWeightLifting
    private CategoryType type;
    private Map<String, List<Exercise>> exercises;
    private boolean isWeightlifting;

    public Category(CategoryType type, boolean weightlifting) {
        this.type = type;
        this.isWeightlifting = weightlifting;
        this.exercises = new HashMap<>();
    }

    public static Category GetCategory(CategoryType type){
        //HERE SHOULD REFERENCE WHERE THE MAIN CATEGORIES MAP SHOULD BE PLACED

        return null;
    }

    public void AddExercise(String name, Exercise e) {
        if(exercises.containsKey(name)) {
            this.exercises.put(name, exercises.get(e));
        }
        else {
            List<Exercise> temp = new ArrayList<>();
            this.exercises.put(name, temp);
            this.exercises.put(name, exercises.get(e));
        }
    }

    public void RemoveExercise(String name, Exercise e){

        if(exercises.containsKey(name)) {
            this.exercises.remove(e);
        }
    }
}
