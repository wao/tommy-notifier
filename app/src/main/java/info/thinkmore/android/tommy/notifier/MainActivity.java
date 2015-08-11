package info.thinkmore.android.tommy.notifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.val;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.provider.Settings.Secure;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    final static String TAG = "TommyNofity_MainActivity";

    Handler handler = new Handler();

    @SystemService
    NotificationManager nm;

    @SystemService
    TelephonyManager telephonyMgr;

    @ViewById
    TextView tvAccess;


    @AfterViews
    void afterViews() {
        //telephonyMgr.listen( phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE ); 
        //NotifierService_.intent( getApplicationContext() ).start();
        String listeners = Secure.getString(getContentResolver(), "enabled_notification_listeners");
        Log.v(TAG, listeners);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();
        return true;
    }

    boolean ring = false;


    @Click(R.id.btnNotify)
    public void btnNotifyClicked(View clickedView){
        if( ring ){
            ring = false;
            NotifierApp.getInstance().getService().phoneOffhook();
        }else{
            ring = true;
            NotifierApp.getInstance().getService().phoneRinging("12345678");
        }
    }

    @Click(R.id.btnSetting)
    public void btnSettingClicked(View clickedView) {
        startActivity( new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }
}
