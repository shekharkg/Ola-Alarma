package com.shekhar.olaalarma.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ShekharKG on 9/26/2015.
 */
public class NetworkClient {

  private static final String BASE_URL = "http://sandbox-t.olacabs.com/v1/products";

  private static final String X_APP_TOKEN_KEY = "X-APP-Token";
  private static final String X_APP_TOKEN_VALUE = "25878cf1728147e5b6564f2f35b0f4fc";

  public static final String PICKUP_LAT = "pickup_lat";
  public static final String PICKUP_LNG = "pickup_lng";
  public static final String CATEGORY = "category";

  public static final String MINI = "mini";
  public static final String SEDAN = "sedan";
  public static final String PRIME = "prime";

  public static List<String> searchedFor = new ArrayList<>();

  public void getRideAvailability(Context context, final CallBack callBack,
                                  RequestParams requestParams, String category) {

    AsyncHttpClient client = new AsyncHttpClient();
    client.addHeader(X_APP_TOKEN_KEY, X_APP_TOKEN_VALUE);

    requestParams.add(CATEGORY, category);
    searchedFor.add(category);


      client.get(context, BASE_URL, requestParams, new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                              Throwable throwable) {

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
