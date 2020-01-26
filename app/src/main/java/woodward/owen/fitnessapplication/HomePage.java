package woodward.owen.fitnessapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import woodward.owen.fitnessapplication.PlateMathCalc.PlateMathCalcActivity;

public class HomePage extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Landscape
            constraintLayout = findViewById(R.id.homePageLayoutOrientation);
        }
        else {
            //Portrait
            constraintLayout = findViewById(R.id.homePageLayout);
        }

        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    public void openExerciseItems (View view) {
        Intent intent = new Intent(getApplicationContext(), ExerciseItemList.class);
        startActivity(intent);
    }

    public void onClickPlateMath (View view) {
        Intent intent = new Intent(getApplicationContext(), PlateMathCalcActivity.class);
        startActivity(intent);
    }
}
