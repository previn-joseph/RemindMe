package cmpsci211.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;

public class OnetimeAlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent data) {

        String name = data.getStringExtra("name");
        String desc = data.getStringExtra("desc");
        int index = data.getIntExtra("index", -1);

        if(index != -1){
            SplashScreen.readPrefs(context);
            MainActivity.events.remove(index);
            SplashScreen.savePrefs(context);
        }

        Intent intent = new Intent(context, SplashScreen.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(context)
                .setContentTitle(name)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);


    }
}
