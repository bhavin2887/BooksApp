package com.app.books.booksapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	
	private final int SPLASH_DISPLAY_LENGTH = 3000;
	public static boolean temp = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(new Runnable() {

			public void run() {
				temp = false;
				SplashActivity.this.finish();
				Intent i = new Intent();
				i.setClass(SplashActivity.this, WelcomeActivity.class);
				startActivity(i);
			}
        }, SPLASH_DISPLAY_LENGTH);        
    }   
}
