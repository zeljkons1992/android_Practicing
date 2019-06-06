package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";

    @Override
    public void onCreate(Bundle icicle) {
        final int SPLASH_DISPLAY_LENGTH = 1000;
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent;
                if(sharedpreferences.getString("mail", "")!= "") {
                    mainIntent = new Intent(SplashScreenActivity.this, RecyclerViewActivity.class);
                }
                else {
                    mainIntent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                }
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
