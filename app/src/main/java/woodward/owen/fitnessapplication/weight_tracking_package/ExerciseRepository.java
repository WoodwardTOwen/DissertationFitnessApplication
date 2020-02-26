package woodward.owen.fitnessapplication.weight_tracking_package;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

public class ExerciseRepository {

    private ExerciseDao dao;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        dao = database.exerciseDao();
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

    public void DeleteAllExercises(String date) {
        new DeleteAllExerciseAsyncTask(dao).execute(date);
    }

    public LiveData<List<Exercise>> getAllExercises(String date) {
        allExercises = dao.getAllExercises(date);
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

    private static class DeleteAllExerciseAsyncTask extends AsyncTask<String, Void, Void> {
        private ExerciseDao exerciseDao; //needed for database operations

        private DeleteAllExerciseAsyncTask(ExerciseDao dao) {
            this.exerciseDao = dao;
        }

        @Override
        protected Void doInBackground(String... date) {
            exerciseDao.deleteAllExercises(date[0]);
            return null;
        }
    }
}
