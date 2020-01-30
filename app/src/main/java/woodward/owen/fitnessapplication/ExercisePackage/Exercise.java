package woodward.owen.fitnessapplication.ExercisePackage;


public class Exercise {
    private Category mCategory;
    private String mExerciseName;
    private int mReps;
    private double mWeight;
    private int mRPE;
    private int mSets;

    private Exercise (String pExerciseName, int pReps, double pWeight, int pRPE, int pSets){
        this.mExerciseName = pExerciseName;
        this.mReps = pReps;
        this.mWeight = pWeight;
        this.mRPE = pRPE;
        this.mSets = pSets;
    }

    public Exercise newExercise(String pExerciseName, int pReps, double pWeight, int pRPE, int pSets) { //Shallow Copy
        Exercise newExercise = new Exercise(pExerciseName,pReps,pWeight,pRPE,pSets);
        newExercise.mCategory =mCategory;
        return newExercise;
    }

    //Getters
    public String getExerciseName () { return mExerciseName; }
    public int getReps() {return mReps; }
    public double getWeights() { return mWeight; }
    public int getRPE() { return mRPE; }
    public int getSets() { return mSets; }
    //Setters
    public void setExerciseName (String pExerciseName)  { mExerciseName = pExerciseName; }
    public void setReps (int pReps) { mReps = pReps; }
    public void setWeight (double pWeight) { if(pWeight % 2.5 == 0 && pWeight != 0){ mWeight = pWeight; }else { throw new IllegalArgumentException(); } }
    public void setRPE (int pRPE) {mRPE = pRPE; }
    public void setmSets (int pSets) {mSets = pSets; }



}
