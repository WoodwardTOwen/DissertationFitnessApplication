package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;

public class ExerciseRepository {

    private ExerciseDao dao;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        dao = database.exerciseDao();
        allExercises = dao.getAllExercises();
    }

    public void Insert(Exercise exercise){
        new InsertExerciseAsyncTask(dao).execute(exercise);
    }

    public void Update(Exercise exercise) {
        new UpdateExerciseAsyncTask(dao).execute(exercise);
    }

    public void Delete(Exercise exercise) {
        new DeleteExerciseAsyncTask(dao).execute(exercise);
    }

    //DELETE ALL EXERCISES AND GET ALL EXERCISES COULD MAYBE HAVE THE REQUIRED Data BASED INTO THEM
    public void DeleteAllExercises() {
        new DeleteAllExerciseAsyncTask(dao).execute();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao; //needed for database operations

        private InsertExerciseAsyncTask(ExerciseDao dao) {
            this.exerciseDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.Insert(exercises[0]);
            return null;
        }
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao; //needed for database operations

        private UpdateExerciseAsyncTask(ExerciseDao dao) {
            this.exerciseDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.Update(exercises[0]);
            return null;
        }
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao; //needed for database operations

        private DeleteExerciseAsyncTask(ExerciseDao dao) {
            this.exerciseDao = dao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.Delete(exercises[0]);
            return null;
        }
    }

    private static class DeleteAllExerciseAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao; //needed for database operations

        private DeleteAllExerciseAsyncTask(ExerciseDao dao) {
            this.exerciseDao = dao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            exerciseDao.deleteAllExercises();
            return null;
        }
    }
}
