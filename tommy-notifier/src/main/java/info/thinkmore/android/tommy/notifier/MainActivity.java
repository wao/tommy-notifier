package info.thinkmore.android.tommy.notifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.val;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity
    extends Activity
{

    Handler handler = new Handler();

    @SystemService
    NotificationManager nm;

    @SystemService
    TelephonyManager telephonyMgr;

    PhoneStateListener phoneStateListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    //when Idle i.e no call
                    Toast.makeText(MainActivity.this, "Phone state Idle", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //when Off hook i.e in call
                    //Make intent and start your service here
                    Toast.makeText(MainActivity.this, "Phone state Off hook", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //when Ringing
                    Toast.makeText(MainActivity.this, "Phone state Ringing", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @AfterViews
    void afterViews() {
        //telephonyMgr.listen( phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE ); 
        //NotifierService_.intent( getApplicationContext() ).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();
        return true;
    }

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Click(R.id.btnNotify)
    public void btnNotifyClicked(View clickedView){
        //NotifierService_.intent( getApplicationContext() ).start();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Test annotation").setContentText("Content info").setSmallIcon( R.drawable.ic_action_search );
        final Notification note = builder.build();
        nm.notify( 12, note );



        final Runnable reload = new Runnable(){
            @Override
            public void run(){
                nm.cancel( 12 );
                nm.notify( 12, note );
            }
        };

        final Runnable reload2 = new Runnable(){
            @Override
            public void run(){
                handler.post( reload );
            }
        };


        scheduler.scheduleAtFixedRate( reload2, 0, 1, TimeUnit.SECONDS);

        handler.postDelayed( new Runnable(){
            @Override
            public void run(){
                scheduler.shutdown();
            }
        }, 1000 * 10L );

    }  
}
