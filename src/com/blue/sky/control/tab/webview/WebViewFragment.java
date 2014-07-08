package com.blue.sky.control.tab.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blue.sky.component.R;
import com.blue.sky.web.api.webview.CrossDomainWebView;

@SuppressLint("ValidFragment")
public class WebViewFragment extends Fragment {
	private String uri;
	private Context context;

	public WebViewFragment(String uri,Context context) {
		this.uri = uri;
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_fragment_web_view, container, false);
		CrossDomainWebView webView = (CrossDomainWebView) (rootView.findViewById(R.id.webview));
		webView.openCrossDomain();
		webView.openSetting(context); 
		webView.loadUrl(uri);
		return rootView;
	}
}
