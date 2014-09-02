package com.hasta.cocoremanager;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Cpu extends Fragment {
    	
	Spinner gov, sched, max_freq, min_freq;
	Button btn;
	TextView gov_tv, sched_tv, max_freq_tv, min_freq_tv;
    ArrayList<String> list_gov, list_sched, list_freq_max, list_freq_min;
	SharedPreferences prefs;
	ArrayAdapter<String>  gov_spn, sched_spn, max_freq_spn, min_freq_spn;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cpu_frag, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        
        // Inizialize layout elements
        gov = (Spinner)v.findViewById(R.id.governor); 
		sched=(Spinner)v.findViewById(R.id.scheduler);
		max_freq=(Spinner)v.findViewById(R.id.max_freq);
		min_freq=(Spinner)v.findViewById(R.id.min_freq);
		gov_tv=(TextView)v.findViewById(R.id.gov_tv);
		max_freq_tv=(TextView)v.findViewById(R.id.max_freq_tv);
		min_freq_tv=(TextView)v.findViewById(R.id.min_freq_tv);
		sched_tv=(TextView)v.findViewById(R.id.sched_tv);
		
		// Update Text Views
		updateGovTextView();
		updateSchedTextView();
		updateMaxFreqTextView();
		updateMinFreqTextView();
		
		//Initialize governor spinner
        list_gov = new ArrayList<String>();
	    gov_spn = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_custom,list_gov );
	    gov_spn.setDropDownViewResource(R.layout.spinner_custom);
	    gov.setAdapter(gov_spn);
	    Utils.split(Strings.AVAILABLE_GOVERNOR, gov_spn);
	    int def_gov= list_gov.indexOf("ondemand");
	    gov.setSelection(prefs.getInt("gov", def_gov));
	    
	    //Initialize scheduler spinner
	    list_sched = new ArrayList<String>();
	    sched_spn = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_custom,list_sched );
	    sched_spn.setDropDownViewResource(R.layout.spinner_custom);
	    sched.setAdapter(sched_spn);
	    Utils.split(Strings.SCHEDULER, sched_spn);
	    int def_sched= list_sched.indexOf("noop");
	    sched.setSelection(prefs.getInt("scheduler", def_sched));
	    
	    //Initialize max. freq spinner
	    list_freq_max = new ArrayList<String>();
	    max_freq_spn = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_custom,list_freq_max );
	    max_freq_spn.setDropDownViewResource(R.layout.spinner_custom);
	    max_freq.setAdapter(max_freq_spn);
	    Utils.split(Strings.AVAILABLE_FREQ, max_freq_spn);
	    int def_maxfreq= list_freq_max.indexOf("1000000");
	    max_freq.setSelection(prefs.getInt("max_freq", def_maxfreq));
	    
	    //Initialize min. freq spinner
	    list_freq_min=new ArrayList<String>();
	    min_freq_spn = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_custom,list_freq_min );
	    min_freq_spn.setDropDownViewResource(R.layout.spinner_custom);
	    min_freq.setAdapter(min_freq_spn);
	    Utils.split(Strings.AVAILABLE_FREQ, min_freq_spn);
	    int def_minfreq= list_freq_min.indexOf("100000");
	    min_freq.setSelection(prefs.getInt("min_freq", def_minfreq));
	    
        
        gov.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {

            		SharedPreferences.Editor editor = prefs.edit();
                	editor.putInt("gov", pos);
                	editor.putString("restore_gov", gov.getSelectedItem().toString());
                	editor.commit();
                	Utils.mSetFilePerm(Strings.GOVERNOR, 777);
        			Utils.writeValue(Strings.GOVERNOR, (String) gov.getSelectedItem());
        			Utils.mSetFilePerm(Strings.GOVERNOR, 664);
        			updateGovTextView();
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        sched.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
            	
            		SharedPreferences.Editor editor = prefs.edit();
                	editor.putInt("scheduler", pos);
                	editor.putString("restore_sched", sched.getSelectedItem().toString().replace("[", "").replace("]", ""));
                	editor.commit();
                	Utils.mSetFilePerm(Strings.SCHEDULER, 777);
        			Utils.writeValue(Strings.SCHEDULER, (String) prefs.getString("restore_sched", "noop"));
        			Utils.mSetFilePerm(Strings.SCHEDULER, 664);
        			updateSchedTextView();
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        max_freq.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {

            		SharedPreferences.Editor editor = prefs.edit();
                	editor.putInt("max_freq", pos);
                	editor.putString("restore_max_freq", max_freq.getSelectedItem().toString());
                	editor.commit();
                	Utils.mSetFilePerm(Strings.MAX_FREQ, 777);
        			Utils.writeValue(Strings.MAX_FREQ, (String) prefs.getString("restore_max_freq", "1000000"));
        			Utils.mSetFilePerm(Strings.MAX_FREQ, 664);
        			updateMaxFreqTextView();
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        min_freq.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {

            		SharedPreferences.Editor editor = prefs.edit();
                	editor.putInt("min_freq", pos);
                	editor.putString("restore_min_freq", min_freq.getSelectedItem().toString());
                	editor.commit();
                	Utils.mSetFilePerm(Strings.MIN_FREQ, 777);
        			Utils.writeValue(Strings.MIN_FREQ, (String) prefs.getString("restore_min_freq", "200000"));
        			Utils.mSetFilePerm(Strings.MIN_FREQ, 664);
        			updateMinFreqTextView();
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        return v;
    }
	
	
	public void updateGovTextView(){
		Utils.readFile(getActivity(), Strings.GOVERNOR, gov_tv, "Current governor:", "gov", null);
	}
	public void updateSchedTextView(){
		Utils.readFile(getActivity(), Strings.SCHEDULER, sched_tv, "Current scheduler:", "sched", prefs);
	}
	public void updateMaxFreqTextView(){
		Utils.readFile(getActivity(), Strings.MAX_FREQ, max_freq_tv, "Current max. frequency:", "freq", null);
	}
	public void updateMinFreqTextView(){
		Utils.readFile(getActivity(), Strings.MIN_FREQ, min_freq_tv, "Current min. frequency:", "freq", null);
	}
}
