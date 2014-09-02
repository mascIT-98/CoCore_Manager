package com.hasta.cocoremanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	TextView tv;
	RelativeLayout rl;
	Button btn;
	String html, code;
	float newVer, code_str;
	
	SharedPreferences prefs;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tv=(TextView)rootView.findViewById(R.id.txtLabel);  
        btn=(Button)rootView.findViewById(R.id.btn);
        rl=(RelativeLayout)rootView.findViewById(R.id.rl);
        if (!Utils.alarmExists(getActivity())) {
            Utils.setAlarm(getActivity(), 3600000, true);
        }
        checkConn();
       
      return rootView;
      
    }

	public boolean checkConn(){
		boolean isNetworkAvailable = false;
		ConnectivityManager cm = (ConnectivityManager)getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
		 try {

	            if (cm != null) {
	                NetworkInfo netInfo = cm.getActiveNetworkInfo();
	                if (netInfo != null) {
	                    isNetworkAvailable = netInfo.isConnectedOrConnecting();
	                    new DownloadFilesTask().execute(Strings.VER_LINK);
	                    Log.i(Strings.TAG, "Checkin'...");
	                }else {
	                	Log.i(Strings.TAG, "net unavailable");
	                }
	            }
	        } catch (Exception ex) {
	            Log.e("Network Avail Error", ex.getMessage());
	        }
			return isNetworkAvailable;
		
	}
	private class DownloadFilesTask extends AsyncTask<String, String, String> {
    	
		
        protected String doInBackground(String... url) {
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
        			code = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
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
        	if(result.equals(Strings.Y)){
        		rl.setVisibility(View.VISIBLE);
        		tv.setText(getActivity().getString(R.string.ota_quest)+code+"\n"+getActivity().getString(R.string.ota_quest2)+html);
        		btn.setVisibility(View.VISIBLE);     		
        		btn.setOnClickListener(new View.OnClickListener()
        	    {
        	          @Override
        	          public void onClick(View InputFragmentView)
        	          {
        	        	  new DownloadUpdate(getActivity()).execute(Strings.APP_LINK);
        	        	        		
        	          }
                });
        	}
        	else {
        		rl.setVisibility(View.VISIBLE);
        		tv.setText(R.string.uptodate);
        	}
        
         }
    }
	
    private class DownloadUpdate extends AsyncTask<String, String, String> {

        public DownloadUpdate(Context context) {
        }
        
    	ProgressDialog progressDialog;
    	
    	@Override
    	protected void onPreExecute() {

    		progressDialog = new ProgressDialog(getActivity());
    		progressDialog.setMessage(getActivity().getString(R.string.down));
    		progressDialog.setIndeterminate(true);
    		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		progressDialog.setCancelable(true);
    		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    		    @Override
    		    public void onCancel(DialogInterface dialog) {
    		        cancel(true);
    		    }
    		});
    		progressDialog.show();
    	}

		@Override
		protected String doInBackground(String... url) {
			int count;
  		    try {
		        URL dUrl = new URL(url[0]);
		        URLConnection conection = dUrl.openConnection();
	            conection.connect();

	            int lenghtOfFile = conection.getContentLength();
	            
	            InputStream input = new BufferedInputStream(dUrl.openStream(), 8192);
		        
		        File downloadLoc = new File(Environment.getExternalStorageDirectory() + "/cocoremanager/");
				if(!downloadLoc.exists()) {
		            downloadLoc.mkdirs();
		        }
				
				File downloadLoc2 = new File(downloadLoc + "/cocoremanager.apk");
				if(downloadLoc2.exists()) {
		            downloadLoc2.delete();
		        }
				
				OutputStream output = new FileOutputStream(downloadLoc2);
				
				byte data[] = new byte[1024];
				long total = 0;
				
				while ((count = input.read(data)) != -1) {
	                total += count;
	                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

	                output.write(data, 0, count);
	            }
	            output.flush();
	            output.close();
	            input.close();

	           
			} catch (Exception e) {
				return Strings.E;
			}
			return "";
		}
		
		protected void onProgressUpdate(String... progress) {
	        progressDialog.setIndeterminate(false);
	        progressDialog.setMax(100);
	        progressDialog.setProgress(Integer.parseInt(progress[0]));
	    }
    	
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			String filepath = Environment.getExternalStorageDirectory().getPath() + "/cocoremanager/cocoremanager.apk";
			Uri fileLoc = Uri.fromFile(new File(filepath));
        	Intent intent = new Intent(Intent.ACTION_VIEW);
        	intent.setDataAndType(fileLoc, "application/vnd.android.package-archive");
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	getActivity().startActivity(intent);
	
		}
    } 
    
}
	

