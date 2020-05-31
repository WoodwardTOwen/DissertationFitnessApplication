package woodward.owen.fitnessapplication.weight_tracking_package.dao_package;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

@Dao
public interface ExerciseDao {
    @Insert
    void Insert (Exercise exercise);

    @Update
    void Update (Exercise exercise);

    @Delete
    void Delete (Exercise exercise);

    //Delete all exercises for a specific date
    @Query("DElETE FROM exercise_table WHERE date = :date")
    void DeleteAllExercises(String date);

    //Select All Exercises From the table
    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> GetEveryExercise();

    //Get all exercises that match the current date
    @Query("SELECT * FROM exercise_table WHERE date = :date")
    LiveData<List<Exercise>> GetAllExercisesByDate(String date);

    //Get all exercises that match the current exerciseName
    @Query("SELECT * FROM exercise_table WHERE exerciseName = :exerciseName")
    LiveData<List<Exercise>> GetSpecificExercisesByName(String exerciseName);

    //Used for testing purposes -> live data is lazily loaded struggles to operate
    @Query("SELECT * FROM exercise_table WHERE exerciseName = :exerciseName")
    List<Exercise> TestGetSpecificExercise(String exerciseName);

    //Weekly Volume Query
    @Query("SELECT * FROM exercise_table WHERE date = :date1 OR date = :date2 OR date = :date3 OR date = :date4 " +
            "OR date = :date5 OR date = :date6 OR date = :date7")
    LiveData<List<Exercise>> GetAllExercisesWeeklyVolume(String date1, String date2, String date3, String date4,
                                                         String date5, String date6, String date7);
}
