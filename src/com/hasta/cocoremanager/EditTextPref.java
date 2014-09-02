package com.hasta.cocoremanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;

public class EditTextPref extends EditTextPreference implements OnPreferenceChangeListener {


	public EditTextPref(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());
		if (key.equals("zram")) {
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("zram_val", 1);
			editor.commit();
			preference.setSummary((String)newValue);
			Utils.writeValue(Strings.ZRAM, "$(("+(String) newValue+" * 1024 * 1024))");
			Utils.exec("mkswap /dev/block/zram0");
			Utils.exec("swapon /dev/block/zram0");
		}

		return true;
	}
	
}
