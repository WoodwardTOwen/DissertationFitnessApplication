package woodward.owen.fitnessapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        constraintLayout = findViewById(R.id.homePageLayout);

        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(7000);
        animationDrawable.setExitFadeDuration(7000);
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
