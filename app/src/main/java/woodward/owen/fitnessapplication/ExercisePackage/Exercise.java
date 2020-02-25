package woodward.owen.fitnessapplication.ExercisePackage;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "exercise_table")
public class Exercise implements Cloneable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String exerciseName;
    private String date;
    private int reps;
    private double weight;
    private int rpe;
    private int sets;

    public Exercise (String exerciseName, int pReps, double pWeight, int pRPE, String date){
        this.exerciseName = exerciseName;
        this.date = date; //Might need reformatting -> might get unneeded time
        this.reps = pReps;
        this.weight = pWeight;
        this.rpe = pRPE;
    }

    public Exercise() {

    }


    public Object Clone() {
        return new Exercise(exerciseName, reps, weight, rpe, date);
    }

    //Getters


    public String getExerciseName() { return exerciseName; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public int getRpe() {
        return rpe;
    }

    public int getSets() {
        return sets;
    }

    //Setters


    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public void setId(int id) {
        this.id = id;
    }

    public void setReps(int reps) {

        if(reps > 0) {
            this.reps = reps;
        }
        else{
            this.reps = 0;
        }
    }

    public void setWeight(double weight) {

        if(weight > 0){
            this.weight = weight;
        }
        else {
            this.weight = 0;
        }
    }

    public void setRpe(int rpe) {

        if(rpe > 0 ){
            this.rpe = rpe;
        }
        else{
            this.rpe = 0;
        }
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
