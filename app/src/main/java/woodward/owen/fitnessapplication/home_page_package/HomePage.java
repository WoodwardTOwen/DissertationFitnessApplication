package woodward.owen.fitnessapplication.home_page_package;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import woodward.owen.fitnessapplication.plate_math_calculator_package.PlateMathCalcActivity;
import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.ExerciseTrackingActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate((R.menu.options_home_page), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.item1Home:
                Toast.makeText(getApplicationContext(), "Option 1 for Home Page selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2Home:
                Toast.makeText(getApplicationContext(), "Option 2 for Home Page selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openExerciseItems (View view) {
        Intent intent = new Intent(getApplicationContext(), ExerciseTrackingActivity.class);
        startActivity(intent);
    }

    public void onClickPlateMath (View view) {
        Intent intent = new Intent(getApplicationContext(), PlateMathCalcActivity.class);
        startActivity(intent);
    }

    private void screenConfig () {

    }

    private void screenAnimation() {

    }
}
