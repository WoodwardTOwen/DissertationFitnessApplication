package woodward.owen.fitnessapplication.plate_math_calculator_package;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import woodward.owen.fitnessapplication.R;

public class HelpClass extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_plate_math);
        Display();
    }

    public void goBack (View v) {
        finish();
    }

    private void Display () {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels, height = displayMetrics.heightPixels;
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((int) (width * .8), (int) (height * .7));  // In landscape
        } else {
            getWindow().setLayout((int) (width * .8), (int) (height * .7));  // In portrait
        }
    }

}

