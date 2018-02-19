package fr.mbds.hamdigazzah.barcode_battler.Services;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hamdigazzah on 11/02/2018.
 */

public class BackgroundSoundService extends Service {
    private static final String TAG = null;

    MediaPlayer player;
    public BackgroundSoundService() throws IOException {
    }

    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();


        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd("soundtrack.mp3");
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.setLooping(true); // Set looping
            player.setVolume(100,100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}