package info.thinkmore.android.tommy.notifier;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.SystemService;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

@EReceiver
public class PhoneStateBroadcastReceiver extends BroadcastReceiver {
    static final String TAG = "TommyNotifer_Receiver";

    @SystemService
    NotificationManager nm;

    @SystemService
    TelephonyManager telephonyMgr;

    class MyPL extends PhoneStateListener{
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

    static PhoneStateListener phoneStateListener;

	@Override
	public void onReceive(Context context, Intent intent) {
        Log.v( TAG, "Call State Changed: " + intent.getStringExtra("state")  );
        //if( phoneStateListener == null ){
            //phoneStateListener = new MyPL();
            //telephonyMgr.listen( phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE );
        //}
	}
}
