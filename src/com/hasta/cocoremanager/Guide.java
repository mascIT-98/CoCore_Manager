package com.hasta.cocoremanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Guide extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide, container, false);
        TextView titl = (TextView) rootView.findViewById(R.id.titl);
        titl.setSelected(true);
        return rootView;
    }
	
}
