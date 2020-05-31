package woodward.owen.fitnessapplication;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseDatabase;

import static org.hamcrest.Matchers.equalTo;
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
    public void FindExercise() {

        Exercise exercise = new Exercise("Lat PullDown", 0, 40, 8, "00/00/20");
        Exercise exercise1 = new Exercise("Chest Press", 10, 50, 10,  "00/01/21");
        exerciseDao.Insert(exercise); exerciseDao.Insert(exercise1);
        List<Exercise> exerciseList = exerciseDao.TestGetSpecificExercise("Chest Press");
        assertThat(exerciseList.get(0), equalTo(exercise1));
    }
}
