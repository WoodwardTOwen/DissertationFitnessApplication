package woodward.owen.fitnessapplication.weight_tracking_package;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

import static org.junit.Assert.*;

public class GraphicalAnalysisMethodsTest {

    private final List<Exercise> exercises = new ArrayList<>();

    @Test
    public void convertDates() {
        GraphicalAnalysisMethods.convertListOfDates(exercises);
        String output = exercises.get(0).getDate();
        String output2 = exercises.get(4).getDate();

        assertEquals("02/08/20", output);
        assertEquals("11/12/20", output2);
    }

    @Test
    public void sortDatesInOrder() {
        GraphicalAnalysisMethods.convertListOfDates(exercises); //Requires convert to turn hyphens to forward slashes for date time conversion
        GraphicalAnalysisMethods.sortDatesInOrder(exercises);
        String startValue = exercises.get(0).getDate();
        String endValue = exercises.get(5).getDate();

        assertEquals("14/06/20", startValue);
        assertEquals("11/12/20", endValue);
    }

    @Test
    public void sortDataForWeightEntries() {
        GraphicalAnalysisMethods.convertListOfDates(exercises);
        GraphicalAnalysisMethods.sortDatesInOrder(exercises);
        GraphicalAnalysisMethods.sortDataForWeightEntries(exercises);
    }

    @Test
    public void sortDataForVolumeDisplay() {
    }

    @Test
    public void findXAxisValue() {
    }

    @Before
    public void MakeEntries (){
        Exercise exercise = new Exercise("Test1", 6, 15, 8, "02-08-20");
        Exercise exercise2 = new Exercise("Test2", 4, 45, 5, "14-06-20");
        Exercise exercise3 = new Exercise("Test3", 67, 80, 4, "06-11-20");
        Exercise exercise4 = new Exercise("Test4", 14, 60, 4, "10-12-20");
        Exercise exercise5 = new Exercise("Test5", 24, 130, 10, "11-12-20");
        Exercise exercise6 = new Exercise("Test6", 50, 150, 9, "4-08-20");

        exercises.add(exercise);
        exercises.add(exercise2);
        exercises.add(exercise3);
        exercises.add(exercise4);
        exercises.add(exercise5);
        exercises.add(exercise6);
    }
}