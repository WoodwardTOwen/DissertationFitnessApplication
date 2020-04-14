package woodward.owen.fitnessapplication.weight_tracking_package;

import android.text.InputFilter;
import android.text.Spanned;

public class FilterInputs implements InputFilter {
    private final int min;
    private final int max;

    FilterInputs(String min, String max){
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;

        } catch (NumberFormatException ex){
            ex.getMessage();
        }
        return "";
    }

    //Checks whether the inputted RPE is in range
    private boolean isInRange(int a, int b, int c){
        if(b > a){
            return c >= a && c <= b;
        }
        else {
            return a >= c && b <= c;
        }
    }
}
