package com.hasta.cocoremanager;


import java.io.IOException;
import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Net extends PreferenceFragment{


	Spinner tcp_cong;
	private static  ArrayList<String> congestions;
	SharedPreferences prefs;
	static ArrayAdapter<String>  tcp_spn;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.net_frag, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        tcp_cong = (Spinner)v.findViewById(R.id.tcp_cong); 
		
		//Initialize tcp spinner
        congestions = new ArrayList<String>();
	    tcp_spn = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_custom, congestions );
	    tcp_spn.setDropDownViewResource(R.layout.spinner_custom);
	    tcp_cong.setAdapter(tcp_spn);
	    Utils.split(Strings.TCP_CONG_AVAILABLE, tcp_spn);
	    tcp_cong.setSelection(prefs.getInt("tcp_cong", 1));
 
        tcp_cong.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {
            		SharedPreferences.Editor editor = prefs.edit();
                	editor.putInt("tcp_cong", pos);
                	editor.putString("restore_tcp_cong", tcp_cong.getSelectedItem().toString());
                	editor.commit();
                	try {
        				Process tcp = Runtime.getRuntime().exec(new String[]{
        						"su", "-c", "sysctl -w net.ipv4.tcp_congestion_control=" + tcp_cong.getSelectedItem().toString()});
        				tcp.waitFor();
        			} catch (IOException e) {
        				e.printStackTrace();
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		return v;	
	}
}