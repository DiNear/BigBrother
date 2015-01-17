package bigbrother.bigbrotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Tony on 1/17/2015.
 */
public class MyService extends Service {
    public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds

    private Handler mHandler = new Handler();

    private Timer mTimer = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (mTimer != null){
            mTimer.cancel();
        }else{
            mTimer = new Timer();
        }

        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable(){

                @Override
                public void run() {

                }
            });
        }
    }
}
