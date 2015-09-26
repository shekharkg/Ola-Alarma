package com.shekhar.olaalarma;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shekhar.olaalarma.receiver.AlarmReceiver;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, (int) System.currentTimeMillis(), intent, 0);
    // get the alarm manager, and schedule an alarm that calls the receiver
    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC, System.currentTimeMillis() + 10 * 1000, pendingIntent);
    Toast.makeText(MainActivity.this, "Timer set to " + 10 + " seconds.", Toast.LENGTH_SHORT).show();

  }

}
