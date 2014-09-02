package com.hasta.cocoremanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Battery extends PreferenceFragment{

    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.battery);
        Utils.mountSystemRW();
    }
    
    @Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());

		if (key.equals("real_charge")) {
			Utils.readFile(getActivity().getApplicationContext(), Strings.REAL_CHARGE);
		}else if (key.equals("wifi")) {
   			SharedPreferences.Editor editor = shared.edit();
   			
			editor.putInt("dhdpm_val", 1);
			editor.commit();
   			Utils.mSetFilePerm(Strings.DHDPM, 777);
   			Utils.writeValue(Strings.DHDPM, (((CheckBoxPreference) preference)
   					.isChecked())? "0" : "1");
   			Utils.mSetFilePerm(Strings.DHDPM, 664);
   		}
		return true;
    }
    
}