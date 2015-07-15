package info.thinkmore.android.tommy.notifier;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.telephony.TelephonyManager;
import android.util.Log;

@EReceiver
public class PhoneStateBroadcastReceiver extends BroadcastReceiver {
    static final String TAG = "TommyNotifer_Receiver";

    NotifierApp getApplication(){
        return NotifierApp.getInstance();
    }

    NotifierService getService(){
        return getApplication().getService();
    }

    @ReceiverAction( "android.intent.action.BOOT_COMPLETED" )
    void bootCompleted(){
        Log.v( TAG, "Boot completed!" );
        NotifierService_.intent(getApplication()).start();
    }

    @ReceiverAction( TelephonyManager.ACTION_PHONE_STATE_CHANGED )
    void phoneStateChangedAction(@ReceiverAction.Extra("state") String state, @ReceiverAction.Extra(TelephonyManager.EXTRA_INCOMING_NUMBER) String incomingNumber ) {
        Log.v( TAG, "Call State Changed: " + state  );
        if( getService() != null ){
            if( "IDLE".equals(state) ){
                getService().phoneIdle();
            }else if( "RINGING".equals(state) ){
                getService().phoneRinging(incomingNumber);
            }else if( "OFFHOOK".equals(state) ){
                getService().phoneOffhook();
            }else{
                throw new RuntimeException(String.format( "Unknown phone state: %s", state ) );
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
