package woodward.owen.fitnessapplication.weight_tracking_package.dao_package;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Category;

@Dao
public interface CategoryDao {

    @Insert
    void Insert (Category category);

    @Delete
    void Delete (Category category);

    @Update
    void Update (Category category);

    @Query("SELECT * FROM Category_Table ORDER BY categoryName ASC")
    LiveData<List<Category>> getAllCategories();
}
