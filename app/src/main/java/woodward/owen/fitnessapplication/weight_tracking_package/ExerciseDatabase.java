package woodward.owen.fitnessapplication.weight_tracking_package;

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

import woodward.owen.fitnessapplication.exercise_package.Exercise;

@Database(entities = Exercise.class, version = 1)
@TypeConverters({DatabaseTypeConverters.class})
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

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
        private ExerciseDao dao;

        private PopulateDbAsyncTask(ExerciseDatabase db){
            dao = db.exerciseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String dateNow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            dao.Insert(new Exercise("Use + button to add an exercise", 10, 50, 4, dateNow));
            dao.Insert(new Exercise("Edit Exercise by tapping me", 15, 70, 9, dateNow));
            dao.Insert(new Exercise("Swipe Left to Remove Items", 15, 60, 6, dateNow));

            return null;
        }
    }
}
