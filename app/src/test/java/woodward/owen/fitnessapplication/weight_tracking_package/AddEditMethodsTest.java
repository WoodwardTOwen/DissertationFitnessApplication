package woodward.owen.fitnessapplication.weight_tracking_package;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddEditMethodsTest {

    @Test
    public void incrementWeight() {
        assertEquals(558, AddEditMethods.incrementWeight("555.5"), .1);
        assertEquals(29.5, AddEditMethods.incrementWeight("27"), .1);
        assertEquals(6.25, AddEditMethods.incrementWeight("3.75"), .1);
    }
    @Test
    public void decrementWeight() {
        assertEquals(272.75, AddEditMethods.decrementWeight("275.25"), .1);
        assertEquals(12.5, AddEditMethods.decrementWeight("15"), .1);
        assertEquals(123, AddEditMethods.decrementWeight("125.5"), .1);
    }
    @Test
    public void VerifyExerciseInputs() {
        //Letters not tested in inputs due to it been error checked before these methods
        assertFalse(AddEditMethods.isVerified("","", ""));
        assertFalse(AddEditMethods.isVerified(null, null, null));
        assertFalse(AddEditMethods.isVerified("", null, "3"));
        assertTrue(AddEditMethods.isVerified("65", "10", "8"));
    }
}