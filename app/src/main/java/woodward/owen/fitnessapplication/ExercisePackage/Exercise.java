package woodward.owen.fitnessapplication.ExercisePackage;


import java.util.Calendar;
import java.util.Date;

public class Exercise implements Cloneable{
    private Date date;
    private int reps;
    private double weight;
    private int rpe;
    private int sets;

    private Exercise ( int pReps, double pWeight, int pRPE){
        this.date = Calendar.getInstance().getTime(); //Might need reformatting -> might get unneeded time
        this.reps = pReps;
        this.weight = pWeight;
        this.rpe = pRPE;
    }

    public Object Clone() {
        return new Exercise(reps, weight, rpe);
    }

    //Getters
    public int getReps() {return reps; }
    public double getWeights() { return weight; }
    public int getRPE() { return rpe; }
    public int getSets() { return sets; }
    //Setters
    public void setReps (int pReps) { reps = pReps; }
    public void setWeight (double pWeight) {
        if(pWeight % 2.5 == 0 && pWeight != 0) {
            weight = pWeight;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    public void setRPE (int pRPE) {
        if(pRPE < 10) {
            throw new IllegalArgumentException();
        }
        rpe = pRPE;
    }
    public void setSets(int pSets) {
        sets = pSets; }
}
