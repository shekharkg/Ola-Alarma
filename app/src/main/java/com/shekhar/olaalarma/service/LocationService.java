package com.shekhar.olaalarma.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.shekhar.olaalarma.MainActivity;
import com.shekhar.olaalarma.utils.CallBack;
import com.shekhar.olaalarma.utils.NetworkClient;


/**
 * Created by ShekharKG on 9/26/2015.
 */
public class LocationService extends Service implements CallBack {

  private static final long INTERVAL = 1000 * 3;
  public static LocationManager locationManager;


  @Override
  public void onCreate() {
    super.onCreate();

    //Thread to update location every one minute
    Thread thread = new Thread() {
      public void run() {
        while (true) {
          try {

            if (locationManager == null)
              locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            getCurrentLocation();
            sleep(INTERVAL);
          } catch (Exception qe) {
            qe.printStackTrace();
          }
        }
      }
    };

    thread.start();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  /**
   * Get user's current location
   */
  public void getCurrentLocation() {
    Location location = null;
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
      new NetworkClient().getRideAvailability(this, this, requestParams);
    }
  }

  @Override
  public void successOperation(String response, int statusCode) {
    Log.e("Response", response);
  }

  @Override
  public void failureOperation(String response, int statusCode) {

  }
}
