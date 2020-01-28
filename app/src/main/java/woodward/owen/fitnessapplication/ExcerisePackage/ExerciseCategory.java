package woodward.owen.fitnessapplication.ExcerisePackage;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCategory {
    private enum mCategory {
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
    private String mCategoryName;
    private List<Exercise> exerciseList = new ArrayList<>();
}
