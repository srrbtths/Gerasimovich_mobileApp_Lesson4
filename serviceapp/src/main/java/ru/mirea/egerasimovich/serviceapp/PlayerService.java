package ru.mirea.egerasimovich.serviceapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public	int	onStartCommand(Intent	intent,	int	flags,	int	startId)	{
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new	MediaPlayer.OnCompletionListener()	{
            public	void	onCompletion(MediaPlayer	mp)	{
                stopForeground(true);
            }
        });
        return	super.onStartCommand(intent,	flags,	startId);
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Playing DJ Smash - Moscow Never Sleeps")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("DJ Smash - Moscow Never Sleeps"))
                .setContentTitle("Music Player");
        int	importance	=	NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel	=	new	NotificationChannel(CHANNEL_ID,	"Student EGerasimovich Notification",	importance);
        channel.setDescription("DJ Smash");

        NotificationManagerCompat notificationManager	=	NotificationManagerCompat.from(this);
        notificationManager.createNotificationChannel(channel);

        startForeground(1,	builder.build());
        mediaPlayer = MediaPlayer.create(this,	R.raw.music);
        mediaPlayer.setLooping(false);
    }

    @Override
    public	void	onDestroy()	{
        stopForeground(true);
        mediaPlayer.stop();
    }
}