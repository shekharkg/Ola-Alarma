package com.shekhar.olaalarma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shekhar.olaalarma.service.LocationService;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    startService(new Intent(MainActivity.this, LocationService.class));

  }

}
