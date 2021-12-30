package com.sedev.emotionapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH = 3000;
    private static String SCHOOL_NAME ="여수충무고 학습도움실";


    Animation topAnim, bottomAnim;
    ImageView iv_logo;
    TextView tv_school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        iv_logo = (ImageView)findViewById(R.id.iv_logo);
        tv_school = (TextView)findViewById(R.id.tv_school);

        tv_school.setText( SCHOOL_NAME);

        iv_logo.setAnimation(topAnim);
        tv_school.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            Intent i = new Intent(MainActivity.this, EmotionActivity.class);
            startActivity(i);
            finish();

            }
        },SPLASH);

//    button.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//
//            Intent i = new Intent(MainActivity.this, EmotionActivity.class);
//            startActivity(i);
////            finish();
//        }
//    });

    }
}