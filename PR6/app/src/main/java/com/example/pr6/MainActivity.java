package com.example.pr6;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1234;

    private NotificationManager notificationManager;
    private Button notificationButton;
    private MyService MyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationButton = findViewById(R.id.notificationButton);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        MyService = new MyService();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notificationManager.areNotificationsEnabled()) {
                    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(intent);
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                    } else {
                        startOverlayService();
                        showNotification();
                    }
                } else {
                    startOverlayService();
                    showNotification();
                }
            }
        });
    }

    private void startOverlayService() {
        Intent overlayIntent = new Intent(getApplicationContext(), MyService.class);
        overlayIntent.putExtra("callingPackage", getPackageName());
        startService(overlayIntent);

    }

    private void showNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Intent appIntent = getPackageManager().getLaunchIntentForPackage("com.example.pr6");
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Уведомление")
                .setContentText("Тут уведомление")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(MainActivity.this)) {
                    startOverlayService();
                    showNotification();
                } else {
                    Toast.makeText(MainActivity.this, "Разрешение на оверлей не предоставлено", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

