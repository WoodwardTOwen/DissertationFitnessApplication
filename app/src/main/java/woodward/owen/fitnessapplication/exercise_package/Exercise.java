package woodward.owen.fitnessapplication.exercise_package;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String exerciseName;
    private String date;
    private int reps;
    private double weight;
    private int rpe;
    private int sets;

    @Ignore
    public Exercise (String exerciseName, int pReps, double pWeight, int pRPE, String date){
        this.exerciseName = exerciseName;
        this.date = date;
        this.reps = pReps;
        this.weight = pWeight;
        this.rpe = pRPE;
    }

    public Exercise() {

    }

    //Parcelable Constructor
    protected Exercise(Parcel in) { //Might need to change level of protection to public instead of protected
        id = in.readInt();
        exerciseName = in.readString();
        date = in.readString();
        reps = in.readInt();
        weight = in.readDouble();
        rpe = in.readInt();
        sets = in.readInt();
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

        this.reps = Math.max(reps, 0);
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

        this.rpe = Math.max(rpe, 0);
    }

    public void setSets(int sets) {
        this.sets = sets;
    }



    //Parcelable for the graphical analysis phase
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(exerciseName);
        dest.writeString(date);
        dest.writeInt(reps);
        dest.writeDouble(weight);
        dest.writeInt(rpe);
        dest.writeInt(sets);
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}
