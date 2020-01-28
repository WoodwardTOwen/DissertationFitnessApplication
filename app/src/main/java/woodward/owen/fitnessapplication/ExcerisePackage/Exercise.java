package woodward.owen.fitnessapplication.ExcerisePackage;

import java.util.Locale;


interface ExerciseInterface {

}

public class Exercise implements ExerciseInterface{
    private ExerciseCategory mCategory;
    private String mExerciseName;



    //Getters
    public String getExerciseName () { return mExerciseName; }
    //Setters
    public void setExerciseName (String pExerciseName)  { mExerciseName = pExerciseName; }


}
