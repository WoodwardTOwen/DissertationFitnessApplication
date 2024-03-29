package woodward.owen.fitnessapplication.exercise_package;

import androidx.databinding.BaseObservable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Category_Table")
public class Category extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String categoryName;

    public Category () {

    }

    public Category (String pCategoryName){
        this.categoryName = pCategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.category);
    }
}
