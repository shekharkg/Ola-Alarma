package com.shekhar.olaalarma.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shekhar.olaalarma.MainActivity;
import com.shekhar.olaalarma.R;
import com.shekhar.olaalarma.receiver.AlarmReceiver;

/**
 * Created by ShekharKG on 9/27/2015.
 */
public class NotificationGenerator {

  public static final int NOTIFICATION_CASE_SUCCESS = 1;
  public static final int NOTIFICATION_CASE_DIFFERENT_CAB_AVAILABLE = 2;
  public static final int NOTIFICATION_CASE_NO_CAB_AVAILABLE = 3;
  public static final int NOTIFICATION_CASE_FAILURE = -1;

  public static final int SNOOZE_INTENT = 5;
  public static final int NOTIFY_INTENT = 6;

  private String message;
  private Context context;
  private int notificationIcon;

  public NotificationGenerator(int notificationCase, String message, Context context, String cabCategoryID) {
    this.context = context;
    this.message = message;

    if (cabCategoryID == null)
      notificationIcon = R.mipmap.ic_launcher;
    else
      switch (cabCategoryID) {
        case NetworkClient.MINI:
          notificationIcon = R.drawable.mini;
          break;
        case NetworkClient.SEDAN:
          notificationIcon = R.drawable.sedan;
          break;
        case NetworkClient.PRIME:
          notificationIcon = R.drawable.prime;
          break;
        default:
          notificationIcon = R.mipmap.ic_launcher;
      }

    switch (notificationCase) {
      case NOTIFICATION_CASE_SUCCESS:
        showSuccessNotification();
        break;
      case NOTIFICATION_CASE_DIFFERENT_CAB_AVAILABLE:
        showDifferentCategorySuccessNotification();
        break;
      case NOTIFICATION_CASE_NO_CAB_AVAILABLE:
        showNoCabAvailableNotification();
        break;
      case NOTIFICATION_CASE_FAILURE:
        showFailureNotification();
        break;
    }

  }

  private void showFailureNotification() {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setAction(String.valueOf(Math.random()));
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(notificationIcon)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setSound(Uri.parse(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showNoCabAvailableNotification() {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setAction(String.valueOf(Math.random()));
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    Intent notifyIntent = new Intent(context, AlarmReceiver.class);
    notifyIntent.putExtra("notifyIntent", NOTIFY_INTENT);
    PendingIntent actionNotifyIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, 0);

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(notificationIcon)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .addAction(0, "Notify when available!", actionNotifyIntent)
                .setContentText("Unfortunately no Ola is available nearby. :(");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setSound(Uri.parse(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showDifferentCategorySuccessNotification() {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setAction(String.valueOf(Math.random()));
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    Intent snoozeIntent = new Intent(context, AlarmReceiver.class);
    snoozeIntent.putExtra("snoozeIntent", SNOOZE_INTENT);
    PendingIntent actionSnoozeIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

    Intent bookIntent = new Intent(context, MainActivity.class);
    bookIntent.putExtra("book_ola", true);
    PendingIntent actionBookIntent = PendingIntent.getActivity(context, 0, bookIntent, 0);

    Intent notifyIntent = new Intent(context, AlarmReceiver.class);
    notifyIntent.putExtra("notifyIntent", NOTIFY_INTENT);
    PendingIntent actionNotifyIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, 0);

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(notificationIcon)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Unfortunately no Ola MINI is nearby, "
                        + message.replace("Your", "instead")))
                .setAutoCancel(true)
                .addAction(0, "Snooze", actionSnoozeIntent)
                .addAction(0, "Book", actionBookIntent)
                .addAction(0, "Notify", actionNotifyIntent)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setSound(Uri.parse(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showSuccessNotification() {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setAction(String.valueOf(Math.random()));
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    Intent snoozeIntent = new Intent(context, AlarmReceiver.class);
    snoozeIntent.putExtra("snoozeIntent", SNOOZE_INTENT);
    PendingIntent actionSnoozeIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

    Intent bookIntent = new Intent(context, MainActivity.class);
    bookIntent.putExtra("book_ola", true);
    PendingIntent actionBookIntent = PendingIntent.getActivity(context, 0, bookIntent, 0);

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(notificationIcon)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .addAction(0, "Snooze", actionSnoozeIntent)
                .addAction(0, "Book Ola", actionBookIntent)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setSound(Uri.parse(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))));
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
