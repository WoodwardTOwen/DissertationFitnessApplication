package woodward.owen.fitnessapplication.ExercisePackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


interface ICategory {
    void AddExercise (Exercise e);
    void RemoveExercise(Exercise e);
}

enum CategoryType {
    CHEST,
    BACK,
    SHOULDERS,
    FOREARMS,
    BICEPS,
    TRICEPS,
    LEGS,
    ABS,
    CARDIO,
}

public class Category implements ICategory{

    //private isWeighLifting
    //Type of exercise has been removed for now -> redundant boolean -> isWeightLifting
    private static final Map<CategoryType, Category> Categories = new HashMap<>();
    private ArrayList<Exercise> exercises;

    private Category (boolean pIsWeightlifting) {
        this.exercises = new ArrayList<>();
    }

    public static Category getCategory(CategoryType type) {
        return Category.Categories.get(type);
    }

    public void AddExercise(Exercise e) {
        String tempName = e.getExerciseName();
        if(CheckIfExists(tempName, exercises)) {
            this.exercises.add(e);
        }
    }

    public void RemoveExercise(Exercise e){
        this.exercises.remove(e);
    }

    private boolean CheckIfExists (String exerciseName, ArrayList<Exercise> arrayList) {

        if(ValidateExercise(exerciseName)){
            for(Exercise str : arrayList) {
                String tempStr = str.getExerciseName().toLowerCase();
                exerciseName = exerciseName.toLowerCase();

                if(tempStr.equals(exerciseName)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean ValidateExercise (String exercise){

        char[] chExercise = exercise.toCharArray();
        for (char c : chExercise) {
            if(!Character.isLetter(c) && !Character.isDigit(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }
        return true;
    }

}
