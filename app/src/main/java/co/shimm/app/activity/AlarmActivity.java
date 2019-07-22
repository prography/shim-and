package co.shimm.app.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import co.shimm.app.R;
import co.shimm.app.media.AudioApplication;

public class AlarmActivity extends AppCompatActivity {
    TimePicker alarmPicker;
    Switch alarmSwitch;
    AlarmManager alarmManager;
    BroadcastReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmPicker =findViewById(R.id.alarmPicker);
        alarmSwitch =findViewById(R.id.alarmSwitch);

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    alarm();
                }else{
                    resetAlarm();
                }
            }
        });

        alarmReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioApplication.getInstance().getServiceInterface().stop();
            }
        };

        registerReceiver(alarmReceiver,new IntentFilter("AlarmService"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void alarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmPicker.getHour());
        calendar.set(Calendar.MINUTE, alarmPicker.getMinute());
        long alarmTime = calendar.getTimeInMillis();
        long gapTime;

        Intent intent = new Intent("AlarmService");
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        /*
        if(alarmTime>SystemClock.elapsedRealtime()){
            gapTime = alarmTime - SystemClock.elapsedRealtime();
        }else{
            gapTime = alarmTime + (long)86400000 - SystemClock.elapsedRealtime();
        }
        */
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }else{
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
        }else{
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
        }
    }

    public void resetAlarm(){
        Intent intent = new Intent("AlarmService");
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
