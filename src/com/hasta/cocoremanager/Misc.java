package com.hasta.cocoremanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Misc extends PreferenceFragment{
      
   
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
    	addPreferencesFromResource(R.xml.misc);
    	Utils.mountSystemRW();
    }
    
    @Override
   	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
   			Preference preference) {
   		// String boxValue;
   		String key = preference.getKey();
   		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
   		 if (key.equals("usb_share")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("usbshare_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.USBSHARE, 777);
   			Utils.writeValue(Strings.USBSHARE, (((CheckBoxPreference) preference)
   					.isChecked())? "1" : "0");
   			Utils.mSetFilePerm(Strings.USBSHARE, 664);
   		} else if (key.equals("usb_switch")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("usbswitch_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.USBSWITCH, 777);
   			Utils.writeValue(Strings.USBSWITCH, (((CheckBoxPreference) preference)
   					.isChecked())? "1" : "0");
   			Utils.mSetFilePerm(Strings.USBSWITCH, 664);
   		} else if (key.equals("log")) {
   			if (((CheckBoxPreference) preference).isChecked()){
   	          Utils.copyAssets("logger", "/data", 777, getActivity().getApplicationContext());
   	  } else {
   		  Utils.exec("rm /data/logger");
   	  }
   		}else if (key.equals("sdcard")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("sdcard_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.SDCARD, 777);
   			Utils.writeValue(Strings.SDCARD, (((CheckBoxPreference) preference)
   					.isChecked())? "2048" : "128");
   			Utils.mSetFilePerm(Strings.SDCARD, 664);
   		} else if (key.equals("vold")) {
   			if (((CheckBoxPreference) preference).isChecked()){
   				new AlertDialog.Builder(getActivity())
		        .setTitle(getActivity().getString(R.string.swap))
		        .setMessage(getActivity().getString(R.string.vold_mess))
		        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) { 
		            	Utils.copyAssets("inverter_on.fstab", "/system/etc", 644, getActivity().getApplicationContext());
		            	Utils.exec("cp -f /system/etc/inverter_on.fstab /system/etc/vold.fstab", "rm /system/etc/inverter_on.fstab");
		     	        Utils.exec("/system/bin/reboot");
		            }
		            
		         }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();					
					}
				})
		         .show();
     	          
     	  } else {
     		 new AlertDialog.Builder(getActivity())
		        .setTitle(getActivity().getString(R.string.swap))
		        .setMessage(getActivity().getString(R.string.vold_mess))
		        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) { 
		            	Utils.exec("rm /system/etc/vold.fstab");
		            	Utils.copyAssets("inverter_off.fstab", "/system/etc", 644, getActivity().getApplicationContext());
		            	Utils.exec("cp -f /system/etc/inverter_off.fstab /system/etc/vold.fstab", "rm /system/etc/inverter_off.fstab");	 
		         		 Utils.exec("/system/bin/reboot");
		            }
		            
		         }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				})
		         .show();
     	  }
   		}
   		return true;
   		}
    
}