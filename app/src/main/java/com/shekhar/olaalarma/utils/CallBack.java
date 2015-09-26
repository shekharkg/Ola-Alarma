package com.shekhar.olaalarma.utils;

/**
 * Created by ShekharKG on 9/26/2015.
 */
public interface CallBack {

  public void successOperation(String object, int statusCode);

  public void failureOperation(String object, int statusCode);

}
