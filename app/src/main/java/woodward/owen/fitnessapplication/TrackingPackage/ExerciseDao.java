package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;

@Dao
public interface ExerciseDao {
    @Insert
    void Insert (Exercise exercise);

    @Update
    void Update (Exercise exercise);

    @Delete
    void Delete (Exercise exercise);

    @Query("DElETE FROM exercise_table") //WHERE [date] = GETDATE()
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table") //WHERE [date] = GETDATE()
    LiveData<List<Exercise>> getAllExercises();
}
