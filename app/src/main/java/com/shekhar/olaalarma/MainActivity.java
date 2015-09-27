package com.shekhar.olaalarma;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shekhar.olaalarma.receiver.AlarmReceiver;
import com.shekhar.olaalarma.utils.NetworkClient;
import com.shekhar.olaalarma.utils.StorageHelper;

import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener {

  private RelativeLayout noAlarmAddedLL;
  private LinearLayout selectedTimeLL, daysLL;
  private TextView timeTV, amPmTV;
  private ImageView miniIV, sedanIV, primeIV, delete;
  private CheckBox repeatCB;
  private TextView suTV, mTV, tTV, wTV, thTV, fTV, sTV;
  private Drawable btnSelected, btnUnSelected;
  private int txtSelected, txtUnSelected;
  private String selectedWeekDays;

  private String selectedCategory;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    checkForFromNotification(getIntent().getBooleanExtra("book_ola", false));

    setContentView(R.layout.activity_main);
    initialize();
  }

  private void initialize() {
    String alarmTIme = StorageHelper.getPreference(this, StorageHelper.TIME);

    selectedCategory = StorageHelper.getPreference(this, StorageHelper.CATEGORY);
    if (selectedCategory.isEmpty())
      selectedCategory = NetworkClient.MINI;

    noAlarmAddedLL = (RelativeLayout) findViewById(R.id.noAlarmAddedLL);
    selectedTimeLL = (LinearLayout) findViewById(R.id.selectedTimeLL);
    daysLL = (LinearLayout) findViewById(R.id.daysLL);
    timeTV = (TextView) findViewById(R.id.timeTV);
    amPmTV = (TextView) findViewById(R.id.amPmTV);
    selectedTimeLL = (LinearLayout) findViewById(R.id.selectedTimeLL);

    if (!alarmTIme.isEmpty()) {
      timeTV.setText(alarmTIme.split("##")[0]);
      amPmTV.setText(alarmTIme.split("##")[1]);
      selectedTimeLL.setVisibility(View.VISIBLE);
      noAlarmAddedLL.setVisibility(View.GONE);
    }

    repeatCB = (CheckBox) findViewById(R.id.repeatCB);

    selectedWeekDays = StorageHelper.getPreference(this, StorageHelper.WEEK_DAYS);
    if (selectedWeekDays.isEmpty())
      selectedWeekDays = "Su M T W Th F S ";
    else {
      repeatCB.setChecked(true);
      daysLL.setVisibility(View.VISIBLE);
    }

    miniIV = (ImageView) findViewById(R.id.miniIV);
    sedanIV = (ImageView) findViewById(R.id.sedanIV);
    primeIV = (ImageView) findViewById(R.id.primeIV);
    delete = (ImageView) findViewById(R.id.delete);

    setCategorySelection();

    btnSelected = getResources().getDrawable(R.drawable.button_selected);
    btnUnSelected = getResources().getDrawable(R.drawable.button_unselected);
    txtUnSelected = getResources().getColor(android.R.color.darker_gray);
    txtSelected = getResources().getColor(android.R.color.white);

    suTV = (TextView) findViewById(R.id.su);
    mTV = (TextView) findViewById(R.id.m);
    tTV = (TextView) findViewById(R.id.t);
    wTV = (TextView) findViewById(R.id.w);
    thTV = (TextView) findViewById(R.id.th);
    fTV = (TextView) findViewById(R.id.f);
    sTV = (TextView) findViewById(R.id.s);

    setSelectedDays();


    noAlarmAddedLL.setOnClickListener(this);
    timeTV.setOnClickListener(this);
    amPmTV.setOnClickListener(this);
    delete.setOnClickListener(this);
    miniIV.setOnClickListener(this);
    sedanIV.setOnClickListener(this);
    primeIV.setOnClickListener(this);

    suTV.setOnClickListener(this);
    mTV.setOnClickListener(this);
    tTV.setOnClickListener(this);
    wTV.setOnClickListener(this);
    thTV.setOnClickListener(this);
    fTV.setOnClickListener(this);
    sTV.setOnClickListener(this);

    repeatCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          daysLL.setVisibility(View.VISIBLE);
          selectedWeekDays = "Su M T W Th F S ";
          setSelectedDays();
        } else {
          daysLL.setVisibility(View.GONE);
          StorageHelper.storePreference(MainActivity.this, StorageHelper.WEEK_DAYS, "");
        }
      }
    });

    findViewById(R.id.dummyAlarmSet).setOnClickListener(this);
  }

  private void setSelectedDays() {
    if (selectedWeekDays.contains("Su ")) {
      suTV.setBackgroundDrawable(btnSelected);
    } else {
      suTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("M ")) {
      mTV.setBackgroundDrawable(btnSelected);
    } else {
      mTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("T ")) {
      tTV.setBackgroundDrawable(btnSelected);
    } else {
      tTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("W ")) {
      wTV.setBackgroundDrawable(btnSelected);
    } else {
      wTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("Th ")) {
      thTV.setBackgroundDrawable(btnSelected);
    } else {
      thTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("F ")) {
      fTV.setBackgroundDrawable(btnSelected);
    } else {
      fTV.setBackgroundDrawable(btnUnSelected);
    }

    if (selectedWeekDays.contains("S ")) {
      sTV.setBackgroundDrawable(btnSelected);
    } else {
      sTV.setBackgroundDrawable(btnUnSelected);
    }

    if (repeatCB.isChecked())
      StorageHelper.storePreference(this, StorageHelper.WEEK_DAYS, selectedWeekDays);
    else
      StorageHelper.storePreference(this, StorageHelper.WEEK_DAYS, "");
  }

  private void setCategorySelection() {
    if (selectedCategory.equals(NetworkClient.MINI)) {
      miniIV.setImageDrawable(getResources().getDrawable(R.drawable.mini2));
      sedanIV.setImageDrawable(getResources().getDrawable(R.drawable.sedan));
      primeIV.setImageDrawable(getResources().getDrawable(R.drawable.prime));
    } else if (selectedCategory.equals(NetworkClient.SEDAN)) {
      miniIV.setImageDrawable(getResources().getDrawable(R.drawable.mini));
      sedanIV.setImageDrawable(getResources().getDrawable(R.drawable.sedan2));
      primeIV.setImageDrawable(getResources().getDrawable(R.drawable.prime));
    } else if (selectedCategory.equals(NetworkClient.PRIME)) {
      miniIV.setImageDrawable(getResources().getDrawable(R.drawable.mini));
      sedanIV.setImageDrawable(getResources().getDrawable(R.drawable.sedan));
      primeIV.setImageDrawable(getResources().getDrawable(R.drawable.prime2));
    }
  }

  private void checkForFromNotification(boolean isFromNotification) {
    if (isFromNotification) {
      String olaPackageName = "com.olacabs.customer";
      if (checkOlaApp(olaPackageName)) {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(olaPackageName);
        startActivity(LaunchIntent);
      } else {
        startActivity(new Intent(Intent.ACTION_VIEW,
            Uri.parse("market://details?id=" + olaPackageName)));
      }
      NotificationManager manager = (NotificationManager)
          getSystemService(Context.NOTIFICATION_SERVICE);
      manager.cancelAll();
      finish();
    }
  }

  private boolean checkOlaApp(String olaPackage) {

    PackageManager pm = getPackageManager();
    try {
      pm.getPackageInfo(olaPackage, PackageManager.GET_ACTIVITIES);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
    }

    return false;
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.timeTV:
      case R.id.amPmTV:
      case R.id.noAlarmAddedLL:
        showTimePicker();
        break;

      case R.id.delete:
        StorageHelper.clearPreferences(this);
        noAlarmAddedLL.setVisibility(View.VISIBLE);
        selectedTimeLL.setVisibility(View.GONE);
        break;

      case R.id.miniIV:
        selectedCategory = NetworkClient.MINI;
        setCategorySelection();
        break;
      case R.id.sedanIV:
        selectedCategory = NetworkClient.SEDAN;
        setCategorySelection();
        break;
      case R.id.primeIV:
        selectedCategory = NetworkClient.PRIME;
        setCategorySelection();
        break;

      case R.id.su:
        if (selectedWeekDays.contains("Su "))
          selectedWeekDays = selectedWeekDays.replace("Su ", "");
        else
          selectedWeekDays = selectedWeekDays + "Su ";
        setSelectedDays();
        break;
      case R.id.m:
        if (selectedWeekDays.contains("M "))
          selectedWeekDays = selectedWeekDays.replace("M ", "");
        else
          selectedWeekDays = selectedWeekDays + "M ";
        setSelectedDays();
        break;
      case R.id.t:
        if (selectedWeekDays.contains("T "))
          selectedWeekDays = selectedWeekDays.replace("T ", "");
        else
          selectedWeekDays = selectedWeekDays + "T ";
        setSelectedDays();
        break;
      case R.id.w:
        if (selectedWeekDays.contains("W "))
          selectedWeekDays = selectedWeekDays.replace("W ", "");
        else
          selectedWeekDays = selectedWeekDays + "W ";
        setSelectedDays();
        break;
      case R.id.th:
        if (selectedWeekDays.contains("Th "))
          selectedWeekDays = selectedWeekDays.replace("Th ", "");
        else
          selectedWeekDays = selectedWeekDays + "Th ";
        setSelectedDays();
        break;
      case R.id.f:
        if (selectedWeekDays.contains("F "))
          selectedWeekDays = selectedWeekDays.replace("F ", "");
        else
          selectedWeekDays = selectedWeekDays + "F ";
        setSelectedDays();
        break;
      case R.id.s:
        if (selectedWeekDays.contains("S "))
          selectedWeekDays = selectedWeekDays.replace("S ", "");
        else
          selectedWeekDays = selectedWeekDays + "S ";
        setSelectedDays();
        break;

      case R.id.dummyAlarmSet:
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent
            .getBroadcast(MainActivity.this, (int) System.currentTimeMillis(), intent, 0);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC,
            System.currentTimeMillis() + 20 * 1000, pendingIntent);
        Toast.makeText(MainActivity.this, "Alarm set to " + 20 + " seconds.",
            Toast.LENGTH_SHORT).show();
        break;
    }
  }

  private void showTimePicker() {
    Calendar currentTime = Calendar.getInstance();
    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
    int minute = currentTime.get(Calendar.MINUTE);
    TimePickerDialog mTimePicker;
    mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        setAlarm(selectedHour, selectedMinute);
        if (selectedHour > 12) {
          selectedHour = selectedHour % 12;
          amPmTV.setText("pm");
        } else {
          amPmTV.setText("am");
        }
        timeTV.setText((selectedHour < 10 ? ("0" + selectedHour) : selectedHour) + ":"
            + (selectedMinute < 10 ? ("0" + selectedMinute) : selectedMinute));
        noAlarmAddedLL.setVisibility(View.GONE);
        selectedTimeLL.setVisibility(View.VISIBLE);


        StorageHelper.storePreference(MainActivity.this, StorageHelper.TIME, timeTV.getText()
            .toString().trim() + "##" + amPmTV.getText().toString().trim());
      }
    }, hour, minute, true);//Yes 24 hour time
    mTimePicker.setTitle("Select Time");
    mTimePicker.show();
  }

  private void setAlarm(int selectedHour, int selectedMinute) {

    Calendar calendar = Calendar.getInstance();
    long currentTime = calendar.getTimeInMillis();

    calendar.add(Calendar.HOUR_OF_DAY, selectedHour);
    calendar.add(Calendar.MINUTE, selectedMinute);

    long alarmTime = calendar.getTimeInMillis();

    if (alarmTime > currentTime) {
      Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
      // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
          (int) System.currentTimeMillis(), intent, 0);
      // get the alarm manager, and schedule an alarm that calls the receiver
      ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC, alarmTime, pendingIntent);
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(alarmTime);
      StorageHelper.storePreference(this, StorageHelper.CATEGORY, selectedCategory);
      Toast.makeText(MainActivity.this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }


  }
}
