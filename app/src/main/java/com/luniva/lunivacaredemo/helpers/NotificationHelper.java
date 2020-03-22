package com.luniva.lunivacaredemo.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.luniva.lunivacaredemo.R;
import com.luniva.lunivacaredemo.activities.MainActivity;
import com.luniva.lunivacaredemo.constants.AppConstants;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.luniva.lunivacaredemo.constants.AppConstants.NOTIFICATION_REQUEST;

//    Notification Builder
//    Notification Channel
//    Notification Manager

public class NotificationHelper extends ContextWrapper {

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            createChannel();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void createChannel() {
//        NotificationChannel channel = new NotificationChannel(AppConstants.NOTIFICATION_CHANNEL_ID,
//                AppConstants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//        channel.enableVibration(true);
//        channel.enableLights(true);
//        channel.setLightColor(R.color.colorPrimary);
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//
//        getNotificationManager().createNotificationChannel(channel);
//    }
//
//    public NotificationManager getNotificationManager(){
//        if(notificationManager == null){
//            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        return notificationManager;
//    }
//
//    public NotificationCompat.Builder getChannelNotification(String title, String message){
//        return new NotificationCompat.Builder(getApplicationContext(), AppConstants.NOTIFICATION_CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp);
//    }

//    initialize in activity

//    NotificationHelper notificationHelper;
//    notificationHelper = new NotificationHelper(this);
//
//    NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message);
//    notificationHelper.getManager().notify(notification_id, nb.build());


    public static void displayNotification(Context context, String title, String body){

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST /* Request code */, intent,
                FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_24dp );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setLargeIcon(largeIcon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.This is big text style.")
                        .setBigContentTitle("Big content Title").setSummaryText("This is summar text"))
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(AppConstants.NOTIFICATION_CHANNEL_ID, AppConstants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(AppConstants.NOTIFICATION_CHANNEL_DESCRIPTION);
            channel.setSound(defaultSoundUri, attributes);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.colorPrimary);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setVibrationPattern(new long[] {500, 500, 500, 500, 500});
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationManager.notify(NOTIFICATION_REQUEST, notificationBuilder.build());

        } else {

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
            mNotificationManager.notify(AppConstants.NOTIFICATION_REQUEST, notificationBuilder.build());
        }

    }
}
