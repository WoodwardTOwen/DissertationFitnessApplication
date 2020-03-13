package woodward.owen.fitnessapplication.exercise_package;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "ExerciseName_Table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "CategoryID",
        onDelete = CASCADE, onUpdate = CASCADE), indices = @Index("CategoryID")) //Refers to if one category is deleted, all the exercises are deleted with it (one to many)

public class ExerciseName {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exerciseName;
    @ColumnInfo(name = "CategoryID")
    private int categoryID;

    public ExerciseName () {

    }

    @Ignore
    public ExerciseName (String exerciseName, int categoryID){
        this.exerciseName = exerciseName;
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
