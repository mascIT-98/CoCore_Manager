package com.hasta.cocoremanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class ListPref extends ListPreference implements
OnPreferenceChangeListener {

	public ListPref(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
		String key = preference.getKey();
		if (key.equals("deep")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("deep_val", 1);
			editor.commit();
		    Utils.mSetFilePerm(Strings.DEEPSLEEP, 777);
		    Utils.writeValue(Strings.DEEPSLEEP, (String) newValue);
	        Utils.mSetFilePerm(Strings.DEEPSLEEP, 664);
	    } else if (key.equals("voltage")) {
	    	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("chargecurr_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.CHARGE_CURR, 777);
	        Utils.writeValue(Strings.CHARGE_CURR, (String) newValue);
            Utils.mSetFilePerm(Strings.CHARGE_CURR, 664);
        } else if (key.equals("save_max")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("savemax_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.POWERSAVE_MAX, 777);
	        Utils.writeValue(Strings.POWERSAVE_MAX, (String) newValue);
            Utils.mSetFilePerm(Strings.POWERSAVE_MAX, 664);
        } else if (key.equals("lcdclk")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("lcdclk_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.LCDCLK, 777);
	        Utils.writeValue(Strings.LCDCLK, (String) newValue);
            Utils.mSetFilePerm(Strings.LCDCLK, 664);
        } else if (key.equals("tparam")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("tparam_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.TPARAM, 777);
	        Utils.writeValue(Strings.TPARAM, (String) newValue);
            Utils.mSetFilePerm(Strings.TPARAM, 664);
        } else if (key.equals("gpu_oc")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("gpuoc_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.GPU_OC, 777);
	        Utils.writeValue(Strings.GPU_OC, (String) newValue);
            Utils.mSetFilePerm(Strings.GPU_OC, 664);
        } else if (key.equals("lmk")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("lmk_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.LMK, 777);
	        Utils.writeValue(Strings.LMK, (String) newValue);
            Utils.mSetFilePerm(Strings.LMK, 664);
        } else if (key.equals("mali_max_reads")) {
        	SharedPreferences.Editor editor = shared.edit();
			editor.putInt("malimax_val", 1);
			editor.commit();
	        Utils.mSetFilePerm(Strings.MALI_MAX_READS, 777);
	        Utils.writeValue(Strings.MALI_MAX_READS, (String) newValue);
            Utils.mSetFilePerm(Strings.MALI_MAX_READS, 664);
        }
		return true;
	}
}