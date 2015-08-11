package info.thinkmore.android.tommy.notifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

@EService
public class NotifierService extends Service {
    static final String TAG = "TommyNotifer_Service";

    // Binder given to clients
    private final Binder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        NotifierService getService() {
            // Return this instance of LocalService so clients can call public methods
            return NotifierService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if( phoneRingScheduler != null ){
            phoneRingScheduler.shutdownNow();
            phoneRingScheduler = null;
            nm.cancelAll();
        }
    }

    @SystemService
    NotificationManager nm;

    public void phoneIdle(){
        Log.v( TAG, "phoneIdle()" );
        stopPhoneRinging();
    }

    public void phoneRinging(String incomingNumber){
        startPhoneRinging(incomingNumber);
    }

    public void phoneOffhook(){
        Log.v( TAG, "phoneOffhook()" );
        stopPhoneRinging();
    }

    int phoneStateNotificationId = 12;

    Notification phoneStateNotification;

    ScheduledExecutorService phoneRingScheduler;

    final Runnable doCancel = new Runnable(){
        @Override
        public void run(){
            nm.cancel( phoneStateNotificationId );
        }
    };

    final Runnable doNotify = new Runnable(){
        @Override
        public void run(){
            nm.notify( phoneStateNotificationId, phoneStateNotification );
        }
    };

    Handler uiHandler = new Handler();

    final Runnable invokeNotifyOperation = new Runnable(){
        @Override
        public void run(){
            uiHandler.post( doNotify );
        }
    };

    void startPhoneRinging(String incomingNumber){
        if( phoneRingScheduler == null ){
            Log.v(TAG, "Start ringing!" );
            phoneRingScheduler = Executors.newSingleThreadScheduledExecutor();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("incoming call:"+incomingNumber).setSmallIcon( R.drawable.ic_action_search );
            phoneStateNotification = builder.build();
            phoneRingScheduler.scheduleAtFixedRate( invokeNotifyOperation, 5, 2, TimeUnit.SECONDS);
        }
    }

    void stopPhoneRinging(){
        if( phoneRingScheduler != null ){
            Log.v(TAG, "Stop ringing!" );
            phoneRingScheduler.shutdownNow();
            uiHandler.post( doCancel );
            phoneRingScheduler = null;
            //??? Maybe reuse phoneStateNotification
            phoneStateNotification = null;
        }
    }
}
