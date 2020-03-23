package woodward.owen.fitnessapplication.weight_tracking_package;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddEditMethodsTest {

    @Test
    public void incrementWeight() {
        String input = "555.5";
        double output;
        double  expected = 558;
        double delta = .1;

        output = AddEditMethods.incrementWeight(input);
        assertEquals(expected, output, delta);
    }

    @Test
    public void decrementWeight() {
        String input = "275.25";
        double output;
        double  expected = 272.75;
        double delta = .1;

        output = AddEditMethods.decrementWeight(input);
        assertEquals(expected, output, delta);
    }
}