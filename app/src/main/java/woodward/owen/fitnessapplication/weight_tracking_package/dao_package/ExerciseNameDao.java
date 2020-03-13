package woodward.owen.fitnessapplication.weight_tracking_package.dao_package;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.ExerciseName;

@Dao
public interface ExerciseNameDao {

    @Insert
    void InsertExerciseName(ExerciseName exerciseName);

    @Delete
    void DeleteExerciseName(ExerciseName exerciseName);

    @Update
    void UpdateExerciseName(ExerciseName exerciseName);

    @Query("SELECT * FROM exercisename_table WHERE categoryID=:categoryID ORDER BY exerciseName ASC")
    LiveData<List<ExerciseName>> FindExercisesFromCategories(int categoryID);
}
