package com.shekhar.olaalarma;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shekhar.olaalarma.utils.CallBack;

/**
 * Created by ShekharKG on 9/27/2015.
 */
public class NotificationGenerator {

  public static final int NOTIFICATION_CASE_SUCCESS = 1;
  public static final int NOTIFICATION_CASE_DIFFERENT_CAB_AVAILABLE = 2;
  public static final int NOTIFICATION_CASE_NO_CAB_AVAILABLE = 3;
  public static final int NOTIFICATION_CASE_FAILURE = -1;

  private String message;
  private Context context;

  public NotificationGenerator(int notificationCase, String message, Context context) {
    this.context = context;
    this.message = message;

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
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
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

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Notify when available!", pendingIntent)
                .setContentText("Unfortunately no Ola is available nearby. :(");

        mBuilder.setContentIntent(pendingIntent);
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

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Unfortunately no Ola MINI is nearby, "
                        + message.replace("Your", "instead")))
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Snooze", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Book Ola", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Notify for MINI", pendingIntent)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
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

    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (pendingIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Snooze", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Book Ola", pendingIntent)
                .setContentText(message);

        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
