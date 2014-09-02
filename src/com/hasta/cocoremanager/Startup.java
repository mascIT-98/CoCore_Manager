package com.hasta.cocoremanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Startup extends BroadcastReceiver{

	@Override
	public void onReceive(final Context c, final Intent bootintent) {
		Restore.shared(c);
	}
}
