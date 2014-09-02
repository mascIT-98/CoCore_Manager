package com.hasta.cocoremanager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SyncFailedException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

class  Utils {
	
    // SU
    public static String SU_wop(String cmds) {
        String out = null;
        try {
            out = new String();
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(cmds+"\n");
            os.writeBytes("exit\n");
            os.flush();
            p.waitFor();
            InputStream stdout = p.getInputStream();
            byte[] buffer = new byte[4096];
            int read;
            while (true) {
                read = stdout.read(buffer);
                out += new String(buffer, 0, read);
                if (read < 4096) {
                    break;
                }
            }
        } catch (Exception e) {
            final Activity activity = new Main();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, Strings.E, Toast.LENGTH_SHORT).show();
                }
            });
            Log.e(Strings.TAG, Strings.E);
        }
        return out.substring(0,out.length()-1);
    }

    public static void check(final Context c){
		if (!System.getProperty("os.version").contains("CoCore")){
			new AlertDialog.Builder(c)
	        .setTitle("Error...")
	        .setMessage("Flash CoCore Kernel 9.0 or above before install this app.")
	        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) { 
	            ((Activity) c).finish();    
	            }
	         })
	         .show();		
		}
    	
	}
    // Copy assets to device
    public static void copyAssets(String script,String path,int mode,Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(script);
            File outFile = new File(Environment.getExternalStorageDirectory().getPath(), script);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            Log.e(Strings.TAG, "Failed to handle: " + script, e);
        }
        exec("mkdir -p " + path, "cp -f "+Environment.getExternalStorageDirectory().getPath()+"/"+script+" "+path+"/"+script, "rm "+Environment.getExternalStorageDirectory().getPath()+"/"+script, "chmod "+Integer.toString(mode)+" "+path+"/"+script);
    }
    
    public static void setAlarm(Context context, long time, boolean trigger) {
        Intent i = new Intent(context, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        if (time > 0) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, trigger ? 0 : time, time, pi);
        }
    }
    
    public static boolean alarmExists(Context context) {
        return (PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
    }
       
    public static void readFile(Context c, String path){
    	BufferedReader  buffered_reader=null;
        try 
        {
            buffered_reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = buffered_reader.readLine()) != null) 
            {
            	showToast(c, line);
            }           
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (buffered_reader != null)
                    buffered_reader.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    
    public static void readFile(Context c, String path, TextView tv, String text, String type, SharedPreferences shared){
    	BufferedReader  buffered_reader=null;
        try 
        {
            buffered_reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = buffered_reader.readLine()) != null) 
            {
            	if(type=="gov"){
            	tv.setText(text+" "+line);
            	}
            	else if(type=="sched"){
            		tv.setText(text+" "+shared.getString("restore_sched", "noop"));
            	}else if(type=="freq"){
            		tv.setText(text+" "+Integer.parseInt(line)/1000+" MHz");
            	}
            }           
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (buffered_reader != null)
                    buffered_reader.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    
    
    public static void writeValue(String filename, String value) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(filename), false);
			fos.write(value.getBytes());
			fos.flush();
			// fos.getFD().sync();
		} catch (FileNotFoundException ex) {
			Log.w(Strings.TAG, filename + " not found: " + ex);
		} catch (SyncFailedException ex) {
			Log.w(Strings.TAG,filename + " sync failed: " + ex);
		} catch (IOException ex) {
			Log.w(Strings.TAG, "IOException trying to sync " + filename + ": " + ex);
		} catch (RuntimeException ex) {
			Log.w(Strings.TAG, "exception while syncing file: ", ex);
		} finally {
			if (fos != null) {
				try {
					Log.w(Strings.TAG,filename + ": " + value);
					fos.close();
				} catch (IOException ex) {
					Log.w(Strings.TAG, "IOException while closing synced file: ", ex);
				} catch (RuntimeException ex) {
					Log.w(Strings.TAG, "exception while closing file: ", ex);
				}
			}
		}

	}

	public static void showToast(Context context, String text) {
	    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	public static void showToast(Context context, int text) {
	    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
    
   
    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    
    
    public static void mountSystemRW() {
        exec("mount -o rw,remount /system");
    }
    
    
    public static void mSetFilePerm(String path,int mode) {
		exec("chmod "+mode+" "+path);
	}
   
    public static void split(String file, ArrayAdapter<String> array){
    	BufferedReader  buffered_reader=null;
        try 
        {
        	String[] words;
            buffered_reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = buffered_reader.readLine()) != null) 
            {
            	words=line.split("\\s+");
            	for(String each : words){
                    if(!"".equals(each)){
                        array.add(each);
                    }
                }
            	
            }           
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (buffered_reader != null)
                    buffered_reader.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
    
   
	public static void exec(String... cmds) {
    	Process process;
		try {
			process = Runtime.getRuntime().exec("su");
	        DataOutputStream os = new DataOutputStream(process.getOutputStream());
	        for(int i=0;i<cmds.length;i++)
	            os.writeBytes(cmds[i]+"\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        process.waitFor();
	        
		} catch(Exception e) {
			Log.e(Strings.TAG, Strings.E);
		}
    }
}

class SU extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... cmds) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            for(int i=0;i<cmds.length;i++)
                os.writeBytes(cmds[i]+"\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {}
        return null;
    }
    
    
}