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
    void delete (Exercise exercise);

    @Query("DElETE FROM exercise_table") //This will have to be delete all from current date -> starting don't change
    void deleteAllNotes();

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();
}
