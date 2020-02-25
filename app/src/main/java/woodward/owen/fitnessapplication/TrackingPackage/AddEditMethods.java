package woodward.owen.fitnessapplication.TrackingPackage;

public class AddEditMethods {

    //Increments Weight for UI Buttons
    public static double incrementWeight(String tempInput) {
        double value;
        if (tempInput.matches("")) {
            value = 2.5;
            return value;
        }

        value = Double.parseDouble(tempInput);
        value = value + 2.5;
        return value;
    }

    //Decrements Weight for UI  Buttons
    public static double decrementWeight(String tempInput) {
        double value = 0;
        if (!tempInput.matches("") && Double.parseDouble(tempInput) >= 2.5) {
            value = Double.parseDouble(tempInput);
            value = value - 2.5;
            return value;
        }
        return value;
    }

    //Increments Reps and RPE for the UI buttons
    public static int incrementRepsRPE(String input) {
        int value;
        if (input.matches("")) {
            value = 1;
            return value;
        }
        value = Integer.parseInt(input);
        value = value + 1;
        return value;
    }

    //Decrements Reps and RPE for the UI buttons
    public static int decrementRepsRPE(String input) {
        int value = 0;
        if (!input.matches("") && Integer.parseInt(input) >= 1) {
            value = Integer.parseInt(input);
            value = value - 1;
            return value;
        }
        return value;
    }

    public static boolean isVerified(String weightInput, String repInput, String rpeInput) {
        return isNullOrWhiteSpace(weightInput) && isNullOrWhiteSpace(repInput) && isNullOrWhiteSpace(rpeInput);
    }

    public static boolean isNullOrWhiteSpace(String x){
        return x != null && !x.isEmpty();
    }

}
