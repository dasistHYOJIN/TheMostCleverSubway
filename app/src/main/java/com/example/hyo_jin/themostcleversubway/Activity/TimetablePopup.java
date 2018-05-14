package com.example.hyo_jin.themostcleversubway.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.R;

public class TimetablePopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀바 제거
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        // 뒷배경 블러 처리
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_timetable_popup);
        getSupportActionBar().hide(); // 타이틀바 제거

        // 팝업창이 모바일 디스플레이에 맞게 크기 재설정
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        getWindow().getAttributes().width = (int)(metrics.widthPixels * 0.9);
        getWindow().getAttributes().height = (int) (metrics.heightPixels * 0.6);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
            finish();
        return super.onTouchEvent(event);
    }

}
