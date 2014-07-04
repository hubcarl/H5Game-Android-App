package com.blue.sky.control.tab.webview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WebViewPagerAdapter extends FragmentPagerAdapter {
	private Fragment[] fragments;

	private CharSequence[] titles;

	public WebViewPagerAdapter(FragmentManager fm, Fragment[] fragments,
			CharSequence[] titles) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
