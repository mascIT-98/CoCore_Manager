package com.hasta.cocoremanager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class Mali extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mali);   
        Utils.mountSystemRW();
        
}
    
    @Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// String boxValue;
		String key = preference.getKey();
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
		if (key.equals("mali_debug")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("malideb_val", 1);
			editor.commit();
			Utils.mSetFilePerm(Strings.MALI_DEB, 777);
			Utils.writeValue(Strings.MALI_DEB, (((CheckBoxPreference) preference)
					.isChecked())? "0" : "2");
			Utils.mSetFilePerm(Strings.MALI_DEB, 664);
		} else if (key.equals("pp")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("pp_val", 1);
			editor.commit();
			Utils.mSetFilePerm(Strings.PP, 777);
			Utils.writeValue(Strings.PP, (((CheckBoxPreference) preference)
					.isChecked())? "1" : "0");
			Utils.mSetFilePerm(Strings.PP, 664);
		}else if (key.equals("mali_driver")) {
			if (((CheckBoxPreference) preference).isChecked()){
		    Utils.exec("cp -f /system/lib/egl/libGLES_android.so /system/lib/egl/android.so", "rm /system/lib/egl/libGLES_android.so");
			Utils.copyAssets("egl.cfg", Strings.EGL,644,getActivity().getApplicationContext());
            Utils.showToast(getActivity().getApplicationContext(), "Reboot to apply changes.");
			}
			else {
			Utils.exec("cp -f /system/lib/egl/android.so /system/lib/egl/libGLES_android.so", "rm /system/lib/egl/android.so");
            Utils.copyAssets("egl_backup.cfg", Strings.EGL,644,getActivity().getApplicationContext());
            Utils.exec("cp -f /system/lib/egl/egl_backup.cfg /system/lib/egl/egl.cfg", "rm /system/lib/egl/egl_backup.cfg");			
			}
		}
		return true;
	}

}
