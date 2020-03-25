package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;

import woodward.owen.fitnessapplication.weight_tracking_package.ExerciseTrackingActivity;
import woodward.owen.fitnessapplication.weight_tracking_package.NotificationHelp;

public class TimerViewModel extends AndroidViewModel {

    private long START_TIME_IN_MILLIS;
    private long endTime;
    private static final String START_BUTTON_NAME = "Start";
    private static final String PAUSE_BUTTON_NAME = "Pause";
    private static final String RESET_BUTTON_NAME = "Reset";
    private boolean timerRunning;
    private long timeLeftInMillis;
    private NotificationHelp notificationHelp;

    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean getIsTimerRunning () {
        return timerRunning;
    }

    public long getTimeRemaining () {
        return timeLeftInMillis;
    }

    public void setTimeRemaining (long timeLeft) {
        this.timeLeftInMillis = timeLeft;
    }

    public void setIsTimerRunning (boolean timer) {
        this.timerRunning = timer;
    }

    public long getStartTimeInMillis (){
        if(START_TIME_IN_MILLIS == 0){
            START_TIME_IN_MILLIS = 60000;
        }

        return START_TIME_IN_MILLIS;
    }

    public void setStartTimeInMillis(long timer) { this.START_TIME_IN_MILLIS = (timer * 1000);}

    public int calculateMinutesRemaining() {
        return (int) (timeLeftInMillis / 1000) / 60;
    }

    public int calculateSecondsRemaining() {
        return (int) (timeLeftInMillis / 1000) % 60;
    }

    public String getStartButtonName() { return START_BUTTON_NAME; }
    public String getPauseButtonName() { return PAUSE_BUTTON_NAME; }
    public String getResetButtonName() { return RESET_BUTTON_NAME; }

    public long getEndTime() { return endTime; }

    public void setEndTime(long time) { this.endTime = time; }

}
