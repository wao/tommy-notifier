package info.thinkmore.android.tommy.notifier;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationMonitorService extends NotificationListenerService {
    static final String TAG = "TommyNotifer_NotifictaionMonitorService";

    @Override
    public void onNotificationPosted( StatusBarNotification sbn ) {
        Log.v(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved( StatusBarNotification sbn ) {
    }
}
