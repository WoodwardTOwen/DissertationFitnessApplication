package woodward.owen.fitnessapplication.weight_tracking_package;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;

public class AddEditMethods {

    //Increments Weight for UI Buttons
    static double incrementWeight(String tempInput) {
        double value;
        if (tempInput.matches("")) {
            value = 2.5;
            return value;
        }

        value = Double.parseDouble(tempInput);
        value = value + 2.5;
        return value;
    }

    //Decrements Weight for UI  Buttons
    static double decrementWeight(String tempInput) {
        double value = 0;
        if (!tempInput.matches("") && Double.parseDouble(tempInput) >= 2.5) {
            value = Double.parseDouble(tempInput);
            value = value - 2.5;
            return value;
        }
        return value;
    }

    //Increments Reps and RPE for the UI buttons
    static int incrementRepsRPE(String input) {
        int value;
        if (input.matches("")) {
            value = 1;
            return value;
        }
        value = Integer.parseInt(input);
        value = value + 1;
        return value;
    }

    //Decrements Reps and RPE for the UI buttons
    static int decrementRepsRPE(String input) {
        int value = 0;
        if (!input.matches("") && Integer.parseInt(input) >= 1) {
            value = Integer.parseInt(input);
            value = value - 1;
            return value;
        }
        return value;
    }

    static boolean isVerified(String weightInput, String repInput, String rpeInput) {
        return isNullOrWhiteSpace(weightInput) && isNullOrWhiteSpace(repInput) && isNullOrWhiteSpace(rpeInput);
    }

    public static boolean isTheSameCategoryName (List<Category> categories, String name) {
        for(Category cat : categories) {
            if(isTheSameValue(cat.getCategoryName().toLowerCase(), name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTheSameExerciseName (List<ExerciseName> exerciseType, String name) {
        for(ExerciseName ex : exerciseType) {
            if(isTheSameValue(ex.getExerciseName().toLowerCase(), name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    static boolean isTheSameValue(String x, String y) {
        return x.equals(y);
    }

    static boolean isVerifiedTime(String time) { return isNullOrWhiteSpace(time);}

    public static boolean isVerifiedCatExercise(String name) {
        return isNullOrWhiteSpace(name);
    }

    private static boolean isNullOrWhiteSpace(String x){
        return x != null && !x.isEmpty();
    }

}
