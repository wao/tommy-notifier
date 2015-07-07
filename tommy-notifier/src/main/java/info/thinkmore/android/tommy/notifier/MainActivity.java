package info.thinkmore.android.tommy.notifier;

import lombok.val;

import android.app.Activity;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity
    extends Activity
{

    @SystemService
    NotificationManager nm;

    @AfterViews
    void afterViews() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();
        return true;
    }

    @Click(R.id.btnNotify)
    public void btnNotifyClicked(View clickedView){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Test annotation").setContentText("Content info").setSmallIcon( R.drawable.ic_action_search );
        nm.notify( 12, builder.build() );
    }  
}
