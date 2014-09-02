package com.hasta.cocoremanager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Performance extends PreferenceFragment{
       
   
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.perf);
        Utils.mountSystemRW();   
    }
    
    @Override
   	public boolean onPreferenceTreeClick(PreferenceScreen ps,Preference preference) {
   		// String boxValue;
   		String key = preference.getKey();
   		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
   		if (key.equals("fsync")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("fsync_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.FSYNCODE, 777);
   			Utils.writeValue(Strings.FSYNCODE, (((CheckBoxPreference) preference)
   					.isChecked())? "1" : "0");
   			Utils.mSetFilePerm(Strings.FSYNCODE, 664);
   		} else if (key.equals("mmc")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("mmc_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.MMC, 777);
   			Utils.writeValue(Strings.MMC, (((CheckBoxPreference) preference)
   					.isChecked())? "0" : "1");
   			Utils.mSetFilePerm(Strings.MMC, 664);
   		} else if (key.equals("t_boost")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("tboost_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.TBOOST, 777);
   			Utils.writeValue(Strings.TBOOST, (((CheckBoxPreference) preference)
   					.isChecked())? "on" : "off");
   			Utils.mSetFilePerm(Strings.TBOOST, 664);
   			
   		} else if (key.equals("frandom")) {
   			if (((CheckBoxPreference) preference).isChecked()){
   			Utils.exec("insmod /lib/modules/frandom.ko");
        	Utils.exec("chmod 0666 /dev/frandom");
        	Utils.exec("chmod 0666 /dev/erandom");
        	Utils.exec("mv /dev/random /dev/random.orig");
        	Utils.exec("mv /dev/urandom /dev/urandom.orig");
        	Utils.exec("ln -s /dev/frandom /dev/random");
        	Utils.exec("ln -s /dev/frandom /dev/urandom");
   			}
   			else{
   				Utils.exec("rm /dev/random");
   				Utils.exec("mv /dev/random.orig /dev/random");
   			}
   			
   		}
   		return true;
   		}
}