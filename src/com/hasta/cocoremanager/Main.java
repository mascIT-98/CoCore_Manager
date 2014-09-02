package com.hasta.cocoremanager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Main extends Activity {

	String[] menutitles;
	TypedArray menuIcons;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private List<RowItem> rowItems;
	private CustomAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menutitles = getResources().getStringArray(R.array.titles);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.slider_list);
		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i]);
			rowItems.add(items);
		}
		adapter = new CustomAdapter(getApplicationContext(), rowItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideitemListener());
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);	
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer,R.string.app_name,R.string.app_name) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			switchFrag(new HomeFragment());
		}
		
		
	}

	class SlideitemListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				switchFrag(new HomeFragment());
				break;
			case 1:
				switchFrag(new Performance());
				break;
			case 2:
				switchFrag(new Mali());
				break;
			case 3:
				switchFrag(new Battery());
				break;
			case 4:
				switchFrag(new Cpu());
				break;
			case 5:
				switchFrag(new Display());
				break;
			case 6:
				switchFrag(new Misc());
				break;
			case 7:
				switchFrag(new Net());
				break;
			case 8:
				switchFrag(new Guide());
				break;
			default:
				break;
			}
			
		}

	}
    
	public void switchFrag(final Fragment f){
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();  
        	  }
        	}, 250);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
			switch(item.getItemId())
		    {
		    case R.id.thanks:
		    	Utils.showToast(Main.this, R.string.thanks);
		        break;
		    case R.id.changelog:
		    	new AlertDialog.Builder(this)
		        .setTitle(R.string.changelog_menu_item)
		        .setMessage(R.string.changelog)
		        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) { 
		                dialog.dismiss();
		            }
		         })
		         .show();
		    	break;
		    case R.id.defaults:
		    	Utils.showToast(this, "Reboot now");
		    	PreferenceManager.getDefaultSharedPreferences(Main.this).edit().clear().commit();
		        break;
		    }
			return true;
		}
		
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	

}