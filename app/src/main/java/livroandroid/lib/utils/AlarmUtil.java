package livroandroid.lib.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Ricardo Lecheta on 08/03/2015.
 */
public class AlarmUtil {
    private static final String TAG = "livroandroid-alarm";

    public static void scheduleAfterXSeconds(Context context, Intent intent, int seconds, int repeat) {
        // Data para executar o alarme depois de x segundos a partir de agora
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, seconds);
        long time = c.getTimeInMillis();

        schedule(context, intent, time, repeat);

        Log.d(TAG, "Alarme agendado para daqui a " + seconds + " segundos.");
    }

    public static void schedule(Context context, Intent intent, long time, int repeat) {
        // Intent para disparar o broadcast
        PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Agenda o alarme
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (repeat > 0) {
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, time, repeat * 1000, p);
        } else {
            alarme.set(AlarmManager.RTC_WAKEUP, time, p);
        }

        Log.d(TAG, "Alarme agendado com sucesso.");
    }

    public static void cancel(Context context, Intent intent) {
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarme.cancel(p);
    }
}
