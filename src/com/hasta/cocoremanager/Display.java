package com.hasta.cocoremanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Display extends PreferenceFragment {
    
   
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
    	addPreferencesFromResource(R.xml.display);
    	Utils.mountSystemRW();
    }
    
    @Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// String boxValue;
		String key = preference.getKey();
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
		if (key.equals("crt")) {
   			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("crt_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.FBDELAY, 777);
   			Utils.writeValue(Strings.FBDELAY, (((CheckBoxPreference) preference)
   					.isChecked())? "1" : "0");
   			Utils.mSetFilePerm(Strings.FBDELAY, 664);
   			Utils.writeValue(Strings.FBDELAY_MS, (((CheckBoxPreference) preference)
   					.isChecked())? "350" : "0");
   			Utils.mSetFilePerm(Strings.FBDELAY_MS, 664);
   		}
		else if (key.equals("sweep2wake")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("sweeptowake_val", 1);
			editor.commit();
			Utils.mSetFilePerm(Strings.SWEEP, 777);
			Utils.writeValue(Strings.SWEEP, (((CheckBoxPreference) preference)
					.isChecked())? "on" : "off");
			Utils.mSetFilePerm(Strings.SWEEP, 664);
		} else if (key.equals("touch2wake")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("touchtowake_val", 1);
			editor.commit();
			Utils.mSetFilePerm(Strings.TOUCHTOWAKE, 777);
			Utils.writeValue(Strings.TOUCHTOWAKE, (((CheckBoxPreference) preference)
					.isChecked())? "on" : "off");
			Utils.mSetFilePerm(Strings.TOUCHTOWAKE, 664);
		} else if (key.equals("led")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("led_val", 1);
			editor.commit();
			Utils.mSetFilePerm(Strings.LED, 777);
			Utils.writeValue(Strings.LED, (((CheckBoxPreference) preference)
					.isChecked())? "on" : "off");
			Utils.mSetFilePerm(Strings.LED, 664);
		} else if (key.equals("n_mode")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("nmode_val", 0);
			editor.commit();
			Utils.mSetFilePerm(Strings.NIGHT_MODE, 777);
			Utils.writeValue(Strings.NIGHT_MODE, (((CheckBoxPreference) preference)
					.isChecked())? "on" : "off");
			Utils.mSetFilePerm(Strings.NIGHT_MODE, 664);
		}
		return true;
	}
    
    
}