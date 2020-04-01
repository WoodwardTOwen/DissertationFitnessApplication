package woodward.owen.fitnessapplication;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseDatabase;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExerciseDaoInstrumentedTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ExerciseDao exerciseDao;
    private ExerciseDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ExerciseDatabase.class).build();
        exerciseDao = mDb.exerciseDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void getAllExercises() throws Exception {
        Exercise exercise = new Exercise("aaa", 0, 1, 3, "00/00/20");
        exerciseDao.Insert(exercise);
        Exercise exercise2 = new Exercise("bbb", 4, 2, 5, "00/00/20");
        exerciseDao.Insert(exercise2);
        Exercise exercise3 = new Exercise("ccc", 2, 20, 8, "00/00/20");
        exerciseDao.Insert(exercise2);

        LiveData<List<Exercise>> exerciseList = exerciseDao.GetAllExercisesByDate("00/00/20");
        assertEquals(Objects.requireNonNull(exerciseList.getValue()).get(0).getExerciseName(), exercise.getExerciseName());
        assertEquals(exerciseList.getValue().get(1).getReps(), exercise2.getReps());
        assertEquals(exerciseList.getValue().get(2).getDate(), exercise3.getDate());
    }
}
