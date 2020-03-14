package woodward.owen.fitnessapplication.weight_tracking_package.database_package;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.CategoryDao;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseNameDao;

@Database(entities = { Category.class, Exercise.class, ExerciseName.class}, version = 3)
@TypeConverters({DatabaseTypeConverters.class})
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseNameDao exerciseNameDao();
    public abstract CategoryDao categoryDao();

    public static synchronized ExerciseDatabase getInstance(Context context) { //synchronized means only one thread at a time can access the method
        if(instance == null) {
            synchronized (ExerciseDatabase.class){ //This was altered here with an extra synchronized
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exericse_database")
                            .fallbackToDestructiveMigration().addCallback(roomCallBack).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;
        private ExerciseNameDao exerciseNameDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(ExerciseDatabase db){
            exerciseDao = db.exerciseDao();
            exerciseNameDao = db.exerciseNameDao();
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            exerciseDao.Insert(new Exercise("Use + button to add an exercise", 10, 50, 4, dateNow));
            exerciseDao.Insert(new Exercise("Edit Exercise by tapping me", 15, 70, 9, dateNow));
            exerciseDao.Insert(new Exercise("Swipe Left to Remove Items", 15, 60, 6, dateNow));

            Shoulders();
            Chest();
            Back();
            Biceps();
            Triceps();
            Legs();

            return null;
        }

        private void Shoulders (){
            categoryDao.Insert(new Category("Shoulders"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Seated Dumbbell Shoulder Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Military Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Smith Machine Overhead Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Overhead Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Arnold Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Standing Dumbbell Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Lat Raises", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Cable Face Pull", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hammer Strength Shoulder Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Reverse Pec Fly", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Viking Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Lat Raise Machine", 1));

        }

        private void Chest () {
            categoryDao.Insert(new Category("Chest"));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Flat Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Decline Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Incline Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Cable CrossOver", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Flat Dumbbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Decline Dumbbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Incline Dumbbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Flyes", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hammer Strength Chest Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hammer Strength Incline Chest Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Svend Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Pec Fly", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Chest Dips", 2));

        }

        private void Back () {
            categoryDao.Insert(new Category("Back"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Lat PullDown", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Seated Cable Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("PullUp", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Straight Arm PullOver", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("T-Bar Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Barbell DeadLift", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Barbell Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Neutral Lat PullDown", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Incline Dumbbell Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Single Arm Dumbbell Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Wide Grip Seated Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Low Row Machine", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Pendlay Row", 3));

        }

        private void Biceps() {
            categoryDao.Insert(new Category("Biceps"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Hammer Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Bicep Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Cable Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Ez-Bar Preacher Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Barbell Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Single Arm Cable Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Incline Dumbbell Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Concentration Curl",4 ));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Crucifix Curl",4 ));

        }

        private void Triceps() {

            categoryDao.Insert(new Category("Triceps"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Overhead Tricep Extension", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Close Grip Barbell Bench Press", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Ez-Bar SkullCrusher", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Rope Push Down", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Tricep Extension", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Tricep Dips", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Tricep Kickbacks", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Single Arm Tricep Extension", 5));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hammer Strength Dip Machine", 5));
        }

        private void Legs () {
            categoryDao.Insert(new Category("Legs"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Barbell Squats", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Sumo DeadLift", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Romanian DeadLifts", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Stiff-Legged DeadLifts", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Front Squats", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Goblet Squats", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Leg Extensions", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hack Squat", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Lying Leg Curl", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Seated Leg Curl", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Weighted Lunges", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Leg Press", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Hip Thrusts", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Seated Calf Raise", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Standing Calf Raise", 6));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Abductor Machine", 6));
        }



    }
}
