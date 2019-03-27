package com.alpyuktug.firebasecloudmessaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServiceForMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Title",remoteMessage.getNotification().getTitle());
        Log.e("Message",remoteMessage.getNotification().getBody());


        createPush(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void createPush(String title,String message){

        NotificationCompat.Builder builder;
        NotificationManager pushcontroller =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(this,MainActivity.class);
        PendingIntent showIntent = PendingIntent.getActivity(this,1,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Oreo

            String ChannelID = "ChannelID";
            String ChannelName = "ChannelName";
            String ChannelMean = "ChannelMean";
            int ChannelImp = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = pushcontroller.getNotificationChannel(ChannelID);

            if (channel == null) {
                channel = new NotificationChannel(ChannelID, ChannelName, ChannelImp);
                channel.setDescription(ChannelMean);
                pushcontroller.createNotificationChannel(channel);
            }

            builder = new NotificationCompat.Builder(this, ChannelID);
            builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.batery)
                    .setContentIntent(showIntent);


        } else { // Not Oreo

            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.batery)
                    .setContentIntent(showIntent)
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        pushcontroller.notify(1,builder.build());
    }

}
