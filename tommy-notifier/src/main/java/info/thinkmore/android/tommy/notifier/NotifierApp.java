package info.thinkmore.android.tommy.notifier;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class NotifierApp extends Application {
    static final String TAG = "TommyNotifer_Application";

    private static NotifierApp singleton;

    // Returns the application instance
    public static NotifierApp getInstance() {
        return singleton;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;
        //try to bind service here.
        loadService();
    }

    @Override
    public final void onTerminate() {
        super.onTerminate();
    }

    @Override
    public final void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public final void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected NotifierService mService;
    protected boolean  mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        Handler handler = new Handler();

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            if( service == null ){
                Log.e( TAG, "Bind server got null:retry 1000ms later" );
                handler.postDelayed( new Runnable(){
                    @Override
                    public void run(){
                        loadService();
                    }
                }, 1000 );
                return;
            }
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            NotifierService.LocalBinder binder = (NotifierService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void loadService(){
        if( !mBound ){
            bindService( NotifierService_.intent(this).get(), mConnection, Context.BIND_AUTO_CREATE );
        }
    }

    NotifierService getService(){
        return mService;
    }
}
