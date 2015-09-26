package com.shekhar.olaalarma.receiver;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shekhar.olaalarma.MainActivity;
import com.shekhar.olaalarma.R;
import com.shekhar.olaalarma.beans.Categories;
import com.shekhar.olaalarma.beans.Category;
import com.shekhar.olaalarma.utils.CallBack;
import com.shekhar.olaalarma.utils.NetworkClient;

import org.json.JSONObject;

import java.util.PriorityQueue;

/**
 * Created by ShekharKG on 9/27/2015.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver implements CallBack {

  private Context context;

  @Override
  public void onReceive(Context context, Intent intent2) {
    Log.e("Receiver", "Alarm");

    this.context = context;
    getCurrentLocation();
  }

  void createNotification(int notificationId, String message, PendingIntent contentIntent) {
    try {
      NotificationManager mNotificationManager = (NotificationManager)
          context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (contentIntent != null) {
        Log.d("ContentIntent", "contentIntent not found null ");

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ola Alarma")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message))
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Snooze", contentIntent)
                .addAction(R.mipmap.ic_launcher, "Book Ola", contentIntent)
                .setContentText(message);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notificationId, mBuilder.build());
      } else {
        Log.d("ContentIntent", "contentIntent found null ");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void getCurrentLocation() {
    Location location = null;
    LocationManager locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);
    Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    //Get address using GPS
    location = gpsLocation;

    //If failed to get address from GPS, Get address using Network
    if (location == null) {
      location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    callAvailabilityApi(location);
  }

  private void callAvailabilityApi(Location location) {
    if (location != null) {
      RequestParams requestParams = new RequestParams();
      requestParams.add(NetworkClient.PICKUP_LAT, String.valueOf(location.getLatitude()));
      requestParams.add(NetworkClient.PICKUP_LNG, String.valueOf(location.getLongitude()));
      requestParams.add(NetworkClient.CATEGORY, NetworkClient.MINI);
      new NetworkClient().getRideAvailability(context, this, requestParams);
    }
  }

  @Override
  public void successOperation(String response, int statusCode) {
    Log.e("Response", response);
    try {
      Categories categories = new Gson().fromJson(response, Categories.class);
      if (categories.getCategories().size() > 0) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(String.valueOf(Math.random()));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        StringBuilder stringBuilder = new StringBuilder();
        Category category = categories.getCategories().get(0);
        stringBuilder.append("Your Ola ").append(category.getGetCabCategoryName()).append(" is ")
            .append(category.getCabDistance()).append(" ").append(category.getDistanceUnit())
            .append(" away, will arrive in ").append(category.getEstimatedArrivalTime())
            .append(" ").append(category.getTimeUnits()).append(".");

        createNotification((int) System.currentTimeMillis(), stringBuilder.toString(),
            PendingIntent.getActivity(context, 0, intent, 0));
      }
    } catch (Exception e) {
      String message = "";
      try {
        JSONObject jsonObject = new JSONObject(response);
        message = jsonObject.getString("message");
      } catch (Exception ex) {
        message = "Unable to find Ola.";
      }
      createNotification((int) System.currentTimeMillis(), message, null);
    }


  }

  @Override
  public void failureOperation(String response, int statusCode) {
    createNotification((int) System.currentTimeMillis(), "Unable to find Ola.", null);
  }

}
