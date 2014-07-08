package com.blue.sky.web.api.webview;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blue.sky.component.R;
import com.blue.sky.web.api.JSInterface;
import com.blue.sky.web.api.WebAppInterface;

public class WebViewActivity extends Activity {

	private boolean hasAdobePlayer = false;// ADOBE FLASH PLAYER�����װ״̬
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(metrics);
		
		Log.v("mobilemetric",display.toString());

		WebView webView = (WebView) findViewById(R.id.web_app_view);
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setPluginState(PluginState.ON);  
		setting.setLoadWithOverviewMode(true);
		
		
		setting.setAllowFileAccess(true);
		webView.addJavascriptInterface(new JSInterface(), "JSInterface");
		webView.addJavascriptInterface(new WebAppInterface(this), "Android");
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebChromeClient(new WebChromeClient(){
			public void onConsoleMessage(String message, int lineNumber, String sourceID) {
			       Log.d("console", message + " -- From line "+ lineNumber + " of "+ sourceID);
			     }
		});
		
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("WebView", "onPageFinished ");
				view.loadUrl("javascript:var body=document.getElementsByTagName('html')[0];" +
						"if(body.innerHTML.indexOf('�Ҳ�����ҳ')>-1||body.innerHTML.indexOf('��ַ����ȷ')>-1)" +
						"{console.log('>>>>�Ҳ�����ҳ���ò��ɼ�');console.log(body.innerHTML);body.style.display='none';}");
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				
				if (failingUrl.contains("#")) {
					final int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
					if (sdkVersion > Build.VERSION_CODES.GINGERBREAD) {
						String[] temp;
						temp = failingUrl.split("#");
						// load page without internal link
						view.loadUrl(temp[0]);

						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// try again
					view.loadUrl(failingUrl);
				}
			}
		});

		
		// setting.setAllowUniversalAccessFromFileURLs(true);
		if (Build.VERSION.SDK_INT >= 16) {
			Class<?> clazz = webView.getSettings().getClass();
			try {
				Method method = clazz.getMethod(
						"setAllowUniversalAccessFromFileURLs", boolean.class);
				if (method != null) {
					method.invoke(webView.getSettings(), true);
				}
			} catch (Exception ex) {
				System.out.println("setAllowUniversalAccessFromFileURLs:"
						+ ex.toString());
			}
		}
		
		webView.loadUrl("http://a.9game.cn/whcl/203642.html");
		
		// �򿪱�����assetĿ¼�µ�index.html�ļ�
		///webView.loadUrl("file:///android_asset/web.html");
		
		//webView.loadUrl("file:///android_asset/web.html/#/game/detail?gameId=537550");

		// �򿪱���sd���ڵ�index.html�ļ�
		// wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");

		// ��ָ��URL��html�ļ�
		// wView.loadUrl("http://wap.baidu.com");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	final class InJavaScriptLocalObj {
		public void showSource(String html) {
			Log.d("HTML", html);
		}
	}
	
	
	/**
	 * �ж��Ƿ�װADOBE FLASH PLAYER���
	 * 
	 * @return
	 */
	public boolean OnCheck() {
		// �ж��Ƿ�װADOBE FLASH PLAYER���
		PackageManager pm = getPackageManager();
		List<PackageInfo> lsPackageInfo = pm.getInstalledPackages(0);

		for (PackageInfo pi : lsPackageInfo) {
			if (pi.packageName.contains("com.adobe.flashplayer")) {
				hasAdobePlayer = true;
				break;
			}
		}
		// �������װһ����
		if (hasAdobePlayer == true) {
			return true;
		} else {
			return false;
		}
	}

}
