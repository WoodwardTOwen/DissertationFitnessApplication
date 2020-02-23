package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
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

    @Query("DElETE FROM exercise_table") //WHERE [date] = GETDATE() -> date LIKE date
    void deleteAllExercises();

   /* @Query("DELETE FROM exercise_table WHERE date LIKE :date")
    void deleteCalenderExercises(String date);*/

    //@Query("SELECT * FROM exercise_table WHERE date LIKE :date") //WHERE [date] = GETDATE()
    //LiveData<List<Exercise>> getAllExercises(String date);

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();
}
