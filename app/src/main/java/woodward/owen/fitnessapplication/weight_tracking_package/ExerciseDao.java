package woodward.owen.fitnessapplication.weight_tracking_package;

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

    @Query("DElETE FROM exercise_table WHERE date LIKE :date")
    void deleteAllExercises(String date);

    @Query("SELECT * FROM exercise_table WHERE date LIKE :date")
    LiveData<List<Exercise>> getAllExercises(String date);

    @Query("SELECT * FROM exercise_table WHERE exerciseName LIKE :exerciseName")
    LiveData<List<Exercise>> getSpecificExercise(String exerciseName);
}
