package info.thinkmore.android.tommy.notifier;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;

public class NotifierApp extends Application {

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
        getService();
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

    NotifierService mService;
    boolean mBound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            if( info.thinkmore.SimpleAssert.enable ){
                info.thinkmore.SimpleAssert.assertTrue(service != null);
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

    NotifierService getService(){
        if( !mBound ){
            bindService( NotifierService_.intent(this).get(), mConnection, Context.BIND_AUTO_CREATE );
        }

        return mService;
    }
}
