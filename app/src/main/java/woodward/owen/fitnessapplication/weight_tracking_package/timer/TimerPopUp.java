package woodward.owen.fitnessapplication.weight_tracking_package.timer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.TimerViewModel;

public class TimerPopUp extends AppCompatActivity {
    private TextView timerCountDownTextView;
    private Button buttonStartPause;
    private CountDownTimer countDownTimer;
    private TimerViewModel timerViewModel;
    private long endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_pop_up_activity);

        timerCountDownTextView = findViewById(R.id.textView_Countdown);
        buttonStartPause = findViewById(R.id.start_Button_Timer);
        Display();
        timerViewModel = new ViewModelProvider(TimerPopUp.this).get(TimerViewModel.class);

        buttonStartPause.setOnClickListener(v -> {
            if (timerViewModel.getIsTimerRunning()) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timerViewModel.getTimeRemaining();

        countDownTimer = new CountDownTimer(timerViewModel.getTimeRemaining(), 1000) { //How fast the countdown goes down, currently 1 second
            @Override
            public void onTick(long millisUntilFinished) {
                timerViewModel.setTimeRemaining(millisUntilFinished); //Number of milliseconds until the countdown is finished
                UpdateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerViewModel.setIsTimerRunning(true);
        UpdateButton();

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerViewModel.setIsTimerRunning(false);
        UpdateButton();
    }

    private void UpdateCountDownText() {
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", timerViewModel.calculateMinutesRemaining(), timerViewModel.calculateSecondsRemaining());
        timerCountDownTextView.setText(timeLeft);
    }

    private void Display() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels, height = displayMetrics.heightPixels;
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((int) (width * .6), (int) (height * .5));  // In landscape
        } else {
            getWindow().setLayout((int) (width * .8), (int) (height * .35));  // In portrait
        }
    }

    private void UpdateButton() {
        if (timerViewModel.getIsTimerRunning()) {
            buttonStartPause.setText("Pause");
        } else {
            buttonStartPause.setText("Start");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("MillisLeft", timerViewModel.getTimeRemaining());
        outState.putBoolean("timerRunning", timerViewModel.getIsTimerRunning());
        outState.putLong("endTime", endTime);

    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        timerViewModel.setTimeRemaining(saveInstanceState.getLong("MillisLeft"));
        timerViewModel.setIsTimerRunning(saveInstanceState.getBoolean("timerRunning"));
        UpdateCountDownText();
        UpdateButton();

        if (timerViewModel.getIsTimerRunning()) {
            endTime = saveInstanceState.getLong("endTime");
            timerViewModel.setTimeRemaining(endTime - System.currentTimeMillis());
            startTimer();
        }
    }

    private void hideToolBar() {
        try {
            if (getActionBar() != null) {
                this.getActionBar().hide();
            }
        } catch (Exception ex) {
            Log.i("Error With Task Bar", "Something went wrong " + ex);
        }
    }
}
