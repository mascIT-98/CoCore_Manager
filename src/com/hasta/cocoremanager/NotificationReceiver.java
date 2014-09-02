package com.hasta.cocoremanager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		new CheckUpdate(context).execute(Strings.VER_LINK);
	}
	
	
class CheckUpdate extends AsyncTask<String, String, String> {
	
		private Context context;
        private PowerManager.WakeLock mWakeLock;

        public CheckUpdate(Context context) {
            this.context = context;
        }


		@Override
    	protected void onPreExecute() {
    		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
    		mWakeLock.acquire();
    	}
    	
        protected String doInBackground(String... url) {
        	 String html, code = null;
        	 float newVer, code_str;
        	try {
        		// Get new version number
        		HttpClient httpClient = new DefaultHttpClient();
                HttpGet get = new HttpGet(url[0]);
                HttpResponse response = httpClient.execute(get);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                HttpEntity entity = response.getEntity();
                html = EntityUtils.toString(entity);
                if (statusCode != 200) {
                    return Strings.E;
                }
        		try {
        			code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        		} catch (NameNotFoundException e) {
        			
        		} catch (NullPointerException e) {
        			
        		}           
                newVer = Float.parseFloat(html);
                code_str=Float.parseFloat(code);
                if(newVer>code_str){
        			return Strings.Y;
                }else {
        	        return Strings.N;
                }
        	} catch (Exception e) {
                return Strings.E;
        	}     
        }

        protected void onPostExecute(String result) {
        	if (result.equals(Strings.Y)) {
        		Intent resultIntent = new Intent(context, Main.class);
        		PendingIntent resultPendingIntent =
        			    PendingIntent.getActivity(
        			    context,
        			    0,
        			    resultIntent,
        			    PendingIntent.FLAG_UPDATE_CURRENT
        			);
        		
        		NotificationCompat.Builder mBuilder =
        			    new NotificationCompat.Builder(context)
        			    .setSmallIcon(R.drawable.ic_launcher)
        			    .setContentTitle(context.getString(R.string.ota))
        			    .setContentText(context.getString(R.string.ota_tap))
        			    .setContentIntent(resultPendingIntent).setAutoCancel(true);
        		int mNotificationId = 001;
        		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        		mNotifyMgr.notify(mNotificationId, mBuilder.build());
        	} 

        	if (!Utils.alarmExists(context)) {
                Utils.setAlarm(context, 3600000, false);
            }
        	mWakeLock.release();
        }
    }


}
