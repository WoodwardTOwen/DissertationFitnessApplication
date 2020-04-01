package woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;

import woodward.owen.fitnessapplication.weight_tracking_package.ExerciseTrackingActivity;
import woodward.owen.fitnessapplication.weight_tracking_package.NotificationHelp;

import static android.content.Context.MODE_PRIVATE;

public class TimerViewModel extends AndroidViewModel {

    private long START_TIME_IN_MILLIS= 60000;
    private long endTime;
    private static final String START_BUTTON_NAME = "Start";
    private static final String PAUSE_BUTTON_NAME = "Pause";
    private static final String RESET_BUTTON_NAME = "Reset";
    private boolean timerRunning;
    private long timeLeftInMillis;
    private static final String SHARED_PREFS = "timerPrefs";
    private static final String START_TIME = "StartTime";
    private final SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

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
        SharedPreferences myPrefs = getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        long time = myPrefs.getLong(START_TIME,0);

        if(time == 0){
            return START_TIME_IN_MILLIS = 60000;
        }else {
            return START_TIME_IN_MILLIS = time;
        }
    }

    public void setStartTimeInMillis(long timer) {
        timer = (timer * 1000);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(START_TIME, timer);
        editor.apply();
        this.START_TIME_IN_MILLIS = timer;
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

    public long getEndTime() { return endTime; }

    public void setEndTime(long time) { this.endTime = time; }

}
