package woodward.owen.fitnessapplication.weight_tracking_package.dao_package;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
    @Query("DElETE FROM exercise_table WHERE date LIKE :date")
    void DeleteAllExercises(String date);

    //Get all exercises that match the current date
    @Query("SELECT * FROM exercise_table WHERE date LIKE :date")
    LiveData<List<Exercise>> GetAllExercises(String date);

    @Query("SELECT * FROM exercise_table WHERE exerciseName = :exerciseName")
    LiveData<List<Exercise>> GetSpecificExercisesByName(String exerciseName);

    /*@Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovementOfItems(List<Exercise> exercise);*/

}
