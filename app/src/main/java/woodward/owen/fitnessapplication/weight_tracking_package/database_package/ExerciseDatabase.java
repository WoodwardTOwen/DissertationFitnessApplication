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

@Database(entities = { Category.class, Exercise.class, ExerciseName.class}, version = 2)
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

        protected void Shoulders (){
            categoryDao.Insert(new Category("Shoulders"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Dumbbell Shoulder Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Military Press", 1));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Smith Machine Overhead Press", 1));

        }

        protected void Chest () {
            categoryDao.Insert(new Category("Chest"));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Flat Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Decline Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Incline Barbell Bench Press", 2));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Cable Flyes", 2));

        }

        protected void Back () {
            categoryDao.Insert(new Category("Back"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Lat PullDown", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("Seated Cable Row", 3));
            exerciseNameDao.InsertExerciseName(new ExerciseName("PullUp", 3));
        }

        protected void Biceps() {
            categoryDao.Insert(new Category("Biceps"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Bicep Curl",4 ));
        }

        protected void Triceps() {

            categoryDao.Insert(new Category("Triceps"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Ez-Bar SkullCrusher", 5));
        }

        protected void Legs () {
            categoryDao.Insert(new Category("Legs"));

            exerciseNameDao.InsertExerciseName(new ExerciseName("Barbell Squats", 6));
        }



    }
}
