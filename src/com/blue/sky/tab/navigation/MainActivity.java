package com.blue.sky.tab.navigation;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.blue.sky.tab.navigation.entity.NavDrawerItem;
import com.blue.sky.tab.navigation.adapter.NavDrawerListAdapter;
import com.blue.sky.tab.navigation.fragment.*;
import com.blue.sky.component.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnItemClickListener {

	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private String[] mNavMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private List<NavDrawerItem> mNavDrawerItems;
	private TypedArray mNavMenuIconsTypeArray;
	private NavDrawerListAdapter mAdapter;
	private ActionBarDrawerToggle mDrawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_main);
		
		findView();
		
		if (savedInstanceState == null) {
	        selectItem(0);
	    }
	}

	@SuppressLint("NewApi")
	private void findView() {
		
		mTitle = mDrawerTitle = getTitle();
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
     	mNavMenuIconsTypeArray = getResources()
     				.obtainTypedArray(R.array.nav_drawer_icons);
     		
        mNavDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[0], mNavMenuIconsTypeArray
				.getResourceId(0, -1)));
		// Find People
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[1], mNavMenuIconsTypeArray
				.getResourceId(1, -1)));
		// Photos
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[2], mNavMenuIconsTypeArray
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[3], mNavMenuIconsTypeArray
				.getResourceId(3, -1), true, "22"));
		// Pages
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[4], mNavMenuIconsTypeArray
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[5], mNavMenuIconsTypeArray
				.getResourceId(5, -1), true, "50+"));

		// Recycle the typed array
		mNavMenuIconsTypeArray.recycle();
        
		// setting the nav drawer list adapter
		mAdapter = new NavDrawerListAdapter(getApplicationContext(),
						mNavDrawerItems);
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(this);
		
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_search_refresh, menu);
		return true;
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_search:
            
             Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT).show();
            
            return true;
        case R.id.action_refresh:
        	
        	Toast.makeText(this, R.string.action_refresh, Toast.LENGTH_SHORT).show();
        	
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
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
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		selectItem(position);
	}
	
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new FindPeopleFragment();
			break;
		case 2:
			fragment = new PhotosFragment();
			break;
		case 3:
			fragment = new CommunityFragment();
			break;
		case 4:
			fragment = new PagesFragment();
			break;
		case 5:
			fragment = new WhatsHotFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(mNavMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

}
