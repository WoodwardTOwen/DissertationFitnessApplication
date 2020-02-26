package woodward.owen.fitnessapplication.weight_tracking_package.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import java.util.Locale;

import woodward.owen.fitnessapplication.R;

public class TimerPopUp extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 100000;
    private TextView timerCountDownTextView;
    private Button buttonStartPause;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private TimerViewModel timerViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_pop_up_activity);

        timerCountDownTextView = findViewById(R.id.textView_Countdown);
        buttonStartPause = findViewById(R.id.start_Button_Timer);

        buttonStartPause.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            }
            else {
                startTimer();
            }
        });
    }

    private void startTimer () {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { //How fast the countdown goes down, currently 1 second
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished; //Number of milliseconds until the countdown is finished
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
        buttonStartPause.setText("Pause");

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText("Start");
    }

    private void updateCountDownText () {
        int minutes = (int) (timeLeftInMillis / 1000) /60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timerCountDownTextView.setText(timeLeft);

    }
}
