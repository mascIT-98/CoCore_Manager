
package com.hasta.cocoremanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	Utils.setAlarm(context, 3600000, true);
    }
}