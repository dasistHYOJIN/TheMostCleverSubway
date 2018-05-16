package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 스플래시 화면은 뷰를 inflate 하지 않음 : setContentView(R.layout.activity_splash);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        //startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
