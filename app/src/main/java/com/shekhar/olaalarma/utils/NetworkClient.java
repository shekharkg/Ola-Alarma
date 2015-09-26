package com.shekhar.olaalarma.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


/**
 * Created by ShekharKG on 9/26/2015.
 */
public class NetworkClient {

  private final String BASE_URL = "http://sandbox-t.olacabs.com/v1/products";

  private final String X_APP_TOKEN_KEY = "X-APP-Token";
  private final String X_APP_TOKEN_VALUE = "25878cf1728147e5b6564f2f35b0f4fc";

  private final String PICKUP_LAT = "pickup_lat";
  private final String PICKUP_LNG = "pickup_lng";
  private final String CATEGORY = "category";

  private final String MINI = "mini";
  private final String SEDAN = "sedan";
  private final String PRIME = "prime";

  private void getRideAvailability(Context context, final CallBack callBack, RequestParams requestParams) {

    AsyncHttpClient client = new AsyncHttpClient();
    client.addHeader(X_APP_TOKEN_KEY, X_APP_TOKEN_VALUE);

    client.get(context, BASE_URL, requestParams, new AsyncHttpResponseHandler() {

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {

      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200) {
          try {
            callBack.successOperation(new String(responseBody), statusCode);
          } catch (Exception e) {
            e.printStackTrace();
            onFailure(statusCode, headers, responseBody, null);
          }
        }
      }
    });

  }
}
