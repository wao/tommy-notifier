package info.thinkmore.android.tommy.notifier;

import java.lang.reflect.Field;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

@EService
public class NotifierService extends Service {
    static final String TAG = "TommyNotifer_Service";

    @SystemService
    NotificationManager nm;

    @SystemService
    TelephonyManager telephonyMgr;

    
    class MyPhoneStateListener extends PhoneStateListener{

        Field subscription;
        
        public MyPhoneStateListener(){
            try{
                subscription = this.getClass().getDeclaredField("mSubscription");
                subscription.setAccessible(true);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        int getSubscription(){
            try{
                return subscription.getInt( this );
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        void setSubscription(int value){
            try{
                subscription.setInt( this, value );
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    //when Idle i.e no call
                    Log.v( TAG, "Phone idle" );
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //when Off hook i.e in call
                    Log.v( TAG, "Phone off hook" );
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //when Ringing
                    Log.v( TAG, "Phone ringing" );
                    break;
                default:
                    break;
            }
        }
    };

    MyPhoneStateListener phoneStateListenerA;
    MyPhoneStateListener phoneStateListenerB;

    boolean alreadyRun = false;


    //ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v( TAG, "Service started!" );
        if( !alreadyRun ){
            alreadyRun = true;
            Log.v( TAG, "Listener begin!" );
            phoneStateListenerA = new MyPhoneStateListener();
            phoneStateListenerB = new MyPhoneStateListener();
            phoneStateListenerA.setSubscription( 0 );
            phoneStateListenerB.setSubscription( 1 );
            telephonyMgr.listen( phoneStateListenerA, PhoneStateListener.LISTEN_CALL_STATE );
            telephonyMgr.listen( phoneStateListenerB, PhoneStateListener.LISTEN_CALL_STATE );
            Log.v( TAG, "Listener registered!" );
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //don't support bind
    }

}
