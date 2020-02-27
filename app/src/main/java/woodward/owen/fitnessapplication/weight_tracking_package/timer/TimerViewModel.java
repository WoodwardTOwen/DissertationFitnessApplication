package woodward.owen.fitnessapplication.weight_tracking_package.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

public class TimerViewModel extends AndroidViewModel {

    private static final long START_TIME_IN_MILLIS = 60000;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

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

    public int calculateMinutesRemaining() {
        return (int) (timeLeftInMillis / 1000) / 60;
    }

    public int calculateSecondsRemaining() {
        return (int) (timeLeftInMillis / 1000) % 60;
    }
}
