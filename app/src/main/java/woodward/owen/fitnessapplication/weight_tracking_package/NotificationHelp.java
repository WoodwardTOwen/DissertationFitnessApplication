package woodward.owen.fitnessapplication.weight_tracking_package;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.INotificationSideChannel;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import io.grpc.SynchronizationContext;
import woodward.owen.fitnessapplication.R;

public class NotificationHelp extends ContextWrapper {
    public static final String channelID = "Channel_1_ID";
    public static final String channelAlarmAlert = "Channel_1_Alarm";
    private NotificationManager manager;

    public NotificationHelp(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel() {
        //Notification Manager can be altered in order to disrupted the user -> this could be changed for the alarm
        NotificationChannel channel = new NotificationChannel(channelID, channelAlarmAlert, NotificationManager.IMPORTANCE_HIGH);


        //Settings for the Notification Channel
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorDatePicker);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager (){
        if(manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent resultIntent = new Intent(this, ExerciseTrackingActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //AutoCancel means the notification will disappear once interacted with
        return new NotificationCompat.Builder(getApplicationContext(), channelID).setContentTitle(title).setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.timer_black).setSound(alarmSound).setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true)
                .setContentIntent(resultPendingIntent).setColor(getResources().getColor(R.color.colorDatePicker));
    }
}
