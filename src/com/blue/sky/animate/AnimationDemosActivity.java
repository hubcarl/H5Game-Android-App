package com.blue.sky.animate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.blue.sky.androidwebapp.R;

public class AnimationDemosActivity extends Activity implements OnClickListener
{

	private WebView webView;
	private Button fadeInButton, fadeOutButton, slideInButton, slideOutButton, scaleInButton, scaleOutButton, rotateInButton, rotateOutButton, scaleRotateInButton, scaleRotateOutButton,
			slideFadeInButton, slideFadeOutButton;
	private AnimationController animationController;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animate_main);

		webView = (WebView) findViewById(R.id.webview);
		webView.setBackgroundColor(Color.BLUE);

		fadeInButton = (Button) findViewById(R.id.fadeInButton);
		fadeOutButton = (Button) findViewById(R.id.fadeOutButton);
		slideInButton = (Button) findViewById(R.id.slideInButton);
		slideOutButton = (Button) findViewById(R.id.slideOutButton);

		scaleInButton = (Button) findViewById(R.id.scaleInButton);
		scaleOutButton = (Button) findViewById(R.id.scaleOutButton);
		rotateInButton = (Button) findViewById(R.id.rotateInButton);
		rotateOutButton = (Button) findViewById(R.id.rotateOutButton);

		scaleRotateInButton = (Button) findViewById(R.id.scaleRotateInButton);
		scaleRotateOutButton = (Button) findViewById(R.id.scaleRotateOutButton);
		slideFadeInButton = (Button) findViewById(R.id.slideFadeInButton);
		slideFadeOutButton = (Button) findViewById(R.id.slideFadeOutButton);

		fadeInButton.setOnClickListener(this);
		fadeOutButton.setOnClickListener(this);
		slideInButton.setOnClickListener(this);
		slideOutButton.setOnClickListener(this);

		scaleInButton.setOnClickListener(this);
		scaleOutButton.setOnClickListener(this);
		rotateInButton.setOnClickListener(this);
		rotateOutButton.setOnClickListener(this);

		scaleRotateInButton.setOnClickListener(this);
		scaleRotateOutButton.setOnClickListener(this);
		slideFadeInButton.setOnClickListener(this);
		slideFadeOutButton.setOnClickListener(this);

		animationController = new AnimationController();

	}

	@Override
	public void onClick(View v)
	{
		long durationMillis = 2000, delayMillis = 0;
		View view = webView;

		if (v == fadeInButton)
		{
			animationController.fadeIn(view, durationMillis, delayMillis);
		}
		else if (v == fadeOutButton)
		{
			animationController.fadeOut(view, durationMillis, delayMillis);
		}
		else if (v == slideInButton)
		{
			animationController.slideIn(view, durationMillis, delayMillis);
		}
		else if (v == slideOutButton)
		{
			animationController.slideOut(view, durationMillis, delayMillis);
		}
		else if (v == scaleInButton)
		{
			animationController.scaleIn(view, durationMillis, delayMillis);

		}
		else if (v == scaleOutButton)
		{
			animationController.scaleOut(view, durationMillis, delayMillis);
		}
		else if (v == rotateInButton)
		{
			animationController.rotateIn(view, durationMillis, delayMillis);
		}
		else if (v == rotateOutButton)
		{
			animationController.rotateOut(view, durationMillis, delayMillis);
		}
		else if (v == scaleRotateInButton)
		{
			animationController.scaleRotateIn(view, durationMillis, delayMillis);

		}
		else if (v == scaleRotateOutButton)
		{
			animationController.scaleRotateOut(view, durationMillis, delayMillis);
		}
		else if (v == slideFadeInButton)
		{
			animationController.slideFadeIn(view, durationMillis, delayMillis);
		}
		else if (v == slideFadeOutButton)
		{
			animationController.slideFadeOut(view, durationMillis, delayMillis);
		}

	}
}