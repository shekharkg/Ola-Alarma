package com.shekhar.olaalarma.receiver;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shekhar.olaalarma.beans.Categories;
import com.shekhar.olaalarma.beans.Category;
import com.shekhar.olaalarma.utils.CallBack;
import com.shekhar.olaalarma.utils.NetworkClient;
import com.shekhar.olaalarma.utils.NotificationGenerator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShekharKG on 9/27/2015.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver implements CallBack {

  private Context context;
  private int count;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.e("Receiver", "Alarm");

    this.context = context;

    getCurrentLocation(NetworkClient.MINI);
  }

  public String getNetworkAndGpsStatus(Context context) {
    final LocationManager locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);
    // getting GPS status
    boolean isGPSEnabled = locationManager
        .isProviderEnabled(LocationManager.GPS_PROVIDER);

    ConnectivityManager connectivityManager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    if (!(networkInfo != null && networkInfo.isConnected()))
      return "{\"message\": \"No internet connection.\"}";

    if (!isGPSEnabled)
      return "{\"message\": \"Please enable GPS.\"}";

    return null;
  }


  public void getCurrentLocation(String category) {
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
    callAvailabilityApi(location, category);
  }

  private void callAvailabilityApi(Location location, String category) {
    String internetAndGpsStatus = getNetworkAndGpsStatus(context);
    if (location != null) {
      RequestParams requestParams = new RequestParams();
      requestParams.add(NetworkClient.PICKUP_LAT, String.valueOf(location.getLatitude()));
      requestParams.add(NetworkClient.PICKUP_LNG, String.valueOf(location.getLongitude()));
      new NetworkClient().getRideAvailability(context, this, requestParams, category);
    } else if (internetAndGpsStatus != null)
      successOperation(internetAndGpsStatus, -1);
    else if (count++ < 3) {
      getCurrentLocation(category);
    }
  }

  @Override
  public void successOperation(String response, int statusCode) {
    Log.e("Response", response);

    if (statusCode == NotificationGenerator.NOTIFICATION_CASE_FAILURE) {
      try {
        JSONObject jsonObject = new JSONObject(response);
        String message = jsonObject.getString("message");
        new NotificationGenerator(NotificationGenerator.NOTIFICATION_CASE_FAILURE,
            message, context, null);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return;
    }

    try {

      Categories categories = new Gson().fromJson(response, Categories.class);
      if (categories.getCategories().size() > 0) {
        Category category = categories.getCategories().get(0);
        if (Integer.parseInt(category.getEstimatedArrivalTime()) > 0) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Your Ola ").append(category.getGetCabCategoryName()).append(" is ")
              .append(category.getCabDistance()).append(" ").append(category.getDistanceUnit())
              .append(" away, will arrive in ").append(category.getEstimatedArrivalTime())
              .append(" ").append(category.getTimeUnits()).append(".");

          new NotificationGenerator(category.getCabCategoryId().equals(NetworkClient.MINI) ?
              NotificationGenerator.NOTIFICATION_CASE_SUCCESS :
              NotificationGenerator.NOTIFICATION_CASE_DIFFERENT_CAB_AVAILABLE,
              stringBuilder.toString(), context, category.getCabCategoryId());

        } else
          checkInAnotherCategory();
      } else
        checkInAnotherCategory();

    } catch (Exception e) {

      String message = "";
      try {
        JSONObject jsonObject = new JSONObject(response);
        message = jsonObject.getString("message");

      } catch (Exception ex) {

        message = "Unable to find Ola.";

      }

      new NotificationGenerator(NotificationGenerator.NOTIFICATION_CASE_FAILURE,
          message, context, null);
    }


  }

  private void checkInAnotherCategory() {
    if (NetworkClient.searchedFor.size() > 2) {
      NetworkClient.searchedFor.clear();
      new NotificationGenerator(NotificationGenerator.NOTIFICATION_CASE_NO_CAB_AVAILABLE,
          "Unfortunately no Cab is available right now!", context, null);
    } else {
      if (!NetworkClient.searchedFor.contains(NetworkClient.MINI))
        getCurrentLocation(NetworkClient.MINI);
      else if (!NetworkClient.searchedFor.contains(NetworkClient.SEDAN))
        getCurrentLocation(NetworkClient.SEDAN);
      else if (!NetworkClient.searchedFor.contains(NetworkClient.PRIME))
        getCurrentLocation(NetworkClient.PRIME);
    }
  }

  @Override
  public void failureOperation(String response, int statusCode) {
    new NotificationGenerator(NotificationGenerator.NOTIFICATION_CASE_FAILURE,
        "Unable to find Ola.", context, null);
  }

}
