package woodward.owen.fitnessapplication.weight_tracking_package.database_package;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Category;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.CategoryDao;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseNameDao;

public class ExerciseRepository {

    private static ExerciseRepository exerciseRepository;
    private ExerciseDao exerciseDao;
    private ExerciseNameDao exerciseNameDao;
    private CategoryDao catDao;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> allExercisesGraphics;
    private LiveData<List<ExerciseName>> allExerciseNames;
    private LiveData<List<Category>> allCategories;

    private ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        exerciseDao = database.exerciseDao();
        exerciseNameDao = database.exerciseNameDao();
        catDao = database.categoryDao();
    }

    public static ExerciseRepository getInstance(Application application){
        if(exerciseRepository == null) {
            exerciseRepository = new ExerciseRepository(application);
        }
        return  exerciseRepository;
    }

    //Exercise
    public void Insert(Exercise exercise){
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void Update(Exercise exercise) {
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void Delete(Exercise exercise) {
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void DeleteAllExercises(String date) {
        new DeleteAllExerciseAsyncTask(exerciseDao).execute(date);
    }

    public LiveData<List<Exercise>> GetAllExercisesWeeklyVolume(String date1, String date2, String date3, String date4, String date5, String date6, String date7){
        allExercisesGraphics = exerciseDao.GetAllExercisesWeeklyVolume(date1, date2, date3, date4, date5, date6, date7);
        return  allExercisesGraphics;
    }

    public LiveData<List<Exercise>> GetAllExercisesByDate(String date) {
        allExercises = exerciseDao.GetAllExercisesByDate(date);
        return allExercises;
    }

    public LiveData<List<Exercise>> GetEveryExercise() {
        return exerciseDao.GetEveryExercise();
    }

    public LiveData<List<Exercise>> GetAllDataForExerciseType(String name) {
        allExercisesGraphics = exerciseDao.GetSpecificExercisesByName(name);
        return allExercisesGraphics;
    }

    //ExerciseName executions
    public void InsertExerciseName(ExerciseName exercise){
        new InsertExerciseNameAsyncTask(exerciseNameDao).execute(exercise);
    }

    public void UpdateExerciseName(ExerciseName exercise) {
        new UpdateExerciseNameAsyncTask(exerciseNameDao).execute(exercise);
    }

    public void DeleteExerciseName(ExerciseName exercise) {
        new DeleteExerciseNameAsyncTask(exerciseNameDao).execute(exercise);
    }

    public LiveData<List<ExerciseName>> FindExercisesForCategory (int categoryID){
        allExerciseNames = exerciseNameDao.FindExercisesFromCategories(categoryID);
        return allExerciseNames;
    }

    //Category Executions
    public void InsertCategory(Category category) {
        new InsertCategoryAsyncTask(catDao).execute(category);
    }

    public LiveData<List<Category>> FindCatNames () {
        allCategories = catDao.FindCatNames();
        return allCategories;
    }

    public void DeleteCategory(Category category) {
        new DeleteCategoryAsyncTask(catDao).execute(category);
    }

    public LiveData<List<Category>> GetAllCategories () {
        allCategories = catDao.getAllCategories();
        return allCategories;
    }


    //Exercise Async tasks
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
            exerciseDao.DeleteAllExercises(date[0]);
            return null;
        }
    }

    //ExerciseName Async Tasks
    private static class InsertExerciseNameAsyncTask extends AsyncTask<ExerciseName, Void, Void> {
        private ExerciseNameDao exerciseNameDao; //needed for database operations

        private InsertExerciseNameAsyncTask(ExerciseNameDao dao) {
            this.exerciseNameDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseName... exercises) {
            exerciseNameDao.InsertExerciseName(exercises[0]);
            return null;
        }
    }

    private static class UpdateExerciseNameAsyncTask extends AsyncTask<ExerciseName, Void, Void> {
        private ExerciseNameDao exerciseNameDao; //needed for database operations

        private UpdateExerciseNameAsyncTask(ExerciseNameDao dao) {
            this.exerciseNameDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseName... exercises) {
            exerciseNameDao.UpdateExerciseName(exercises[0]);
            return null;
        }
    }

    private static class DeleteExerciseNameAsyncTask extends AsyncTask<ExerciseName, Void, Void> {
        private ExerciseNameDao exerciseNameDao; //needed for database operations

        private DeleteExerciseNameAsyncTask(ExerciseNameDao dao) {
            this.exerciseNameDao = dao;
        }

        @Override
        protected Void doInBackground(ExerciseName... exercises) {
            exerciseNameDao.DeleteExerciseName(exercises[0]);
            return null;
        }
    }

    //Category Async Tasks
    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao; //needed for database operations

        private InsertCategoryAsyncTask(CategoryDao dao) {
            this.categoryDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.Insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao; //needed for database operations

        private UpdateCategoryAsyncTask(CategoryDao dao) {
            this.categoryDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.Update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao; //needed for database operations

        private DeleteCategoryAsyncTask(CategoryDao dao) {
            this.categoryDao = dao;
        }

        @Override
        protected Void doInBackground(Category... exercises) {
            categoryDao.Delete(exercises[0]);
            return null;
        }
    }
}
