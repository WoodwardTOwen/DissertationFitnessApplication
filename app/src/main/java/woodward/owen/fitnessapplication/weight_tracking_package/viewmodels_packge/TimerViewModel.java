package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TimerViewModel extends AndroidViewModel {

    private static final long START_TIME_IN_MILLIS = 60000;
    private static final String START_BUTTON_NAME = "Start";
    private static final String PAUSE_BUTTON_NAME = "Pause";
    private static final String RESET_BUTTON_NAME = "Reset";
    private boolean timerRunning;
    private long timeLeftInMillis;

    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean getIsTimerRunning () {
        return timerRunning;
    }

    public long getTimeRemaining () {
        if (timeLeftInMillis == 0) {
            return timeLeftInMillis = START_TIME_IN_MILLIS;
        }
        return timeLeftInMillis;
    }

    public void setTimeRemaining (long timeLeft) {
        this.timeLeftInMillis = timeLeft;
    }

    public void setIsTimerRunning (boolean timer) {
        this.timerRunning = timer;
    }

    public long getStartTimeInMillis (){
        return START_TIME_IN_MILLIS;
    }

    public int calculateMinutesRemaining() {
        return (int) (timeLeftInMillis / 1000) / 60;
    }

    public int calculateSecondsRemaining() {
        return (int) (timeLeftInMillis / 1000) % 60;
    }

    public String getStartButtonName() { return START_BUTTON_NAME; }
    public String getPauseButtonName() { return PAUSE_BUTTON_NAME; }
    public String getResetButtonName() { return RESET_BUTTON_NAME; }
}
