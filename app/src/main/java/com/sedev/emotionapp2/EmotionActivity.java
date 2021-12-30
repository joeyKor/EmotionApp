package com.sedev.emotionapp2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class EmotionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private ViewPager2 viewPager2;
    Random r;
    Button btn_select, btn_diary;
    TextView tv_num;
    EditText et_emotion, et_description;
    int pointA, picture_cnt;
    ArrayList<EmotionItem> mEmotionList = new ArrayList<>();
    List<SlideItem> slideItem = new ArrayList<>();
    String datestr;
    ImageData imgData;
    int pick_num[] = new int [13];
    String emotion_sel;
    Spinner spinner;

    Animation scaleUp, scaleDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        tv_num =(TextView)findViewById(R.id.tv_num);
        r = new Random();
        picture_cnt =0;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //get date of today
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        datestr = sdf.format(cal.getTime());

        loadData();
        imgData = new ImageData();

        viewPager2 = findViewById(R.id.ViewPagerslide);
        btn_select = findViewById(R.id.btn_select);
        btn_diary = (Button)findViewById(R.id.btn_diary);

        et_emotion = (EditText)findViewById(R.id.et_emotion);
        et_description = (EditText)findViewById(R.id.et_descrip);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emotion_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        for(int i=0; i<13; i++) {
            pick_num[i] = (int)(Math.random() * 99);

            // 중복 번호 제거
            for(int j=0; j<i; j++) {
                if(pick_num[i] == pick_num[j]) {
                    i--;
                    break;
                }
            }
        }

        btn_select.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    btn_select.startAnimation(scaleUp);
                }else if(event.getAction() == MotionEvent.ACTION_UP)
                    btn_select.startAnimation(scaleDown);
                return false;
            }
        });

        btn_diary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    btn_diary.startAnimation(scaleUp);
                }else if(event.getAction() == MotionEvent.ACTION_UP)
                    btn_diary.startAnimation(scaleDown);
                return false;
            }
        });


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.i("this","this is point"+ pointA);

                saveData();

                Intent i = new Intent(EmotionActivity.this, RecordActivity.class);
                startActivity(i);
                finish();

            }
        });

        btn_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmotionActivity.this, RecordActivity.class);
                startActivity(i);
                finish();
            }
        });


        slideItem.add(new SlideItem(imgData.ImageD[pick_num[0]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[1]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[2]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[3]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[4]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[5]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[6]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[7]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[8]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[9]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[10]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[11]]));
        slideItem.add(new SlideItem(imgData.ImageD[pick_num[12]]));


        viewPager2.setAdapter(new SlideAdapter(slideItem, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r =1 - Math.abs(position);
                page.setScaleY(0.85f +r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tv_num.setText(position+1 +"/13");
                pointA = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void saveData() {

        if (et_emotion.isEnabled()){
            emotion_sel = et_emotion.getText().toString();
        }else{

        }

        mEmotionList.add(0, new EmotionItem(slideItem.get(pointA).getImage(), emotion_sel, et_description.getText().toString(), datestr));
        et_emotion.setText("");
        et_description.setText("");

        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mEmotionList);
        editor.putString("task List", json);
        editor.apply();

    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task List",null);
        Type type = new TypeToken<ArrayList<EmotionItem>>() {}.getType();
        mEmotionList = gson.fromJson(json, type);

        if(mEmotionList == null){
            mEmotionList = new ArrayList<>();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 50){
            et_emotion.setEnabled(true);
            et_emotion.setText("");
        }else{
            et_emotion.setEnabled(false);
            et_emotion.setText(parent.getItemAtPosition(position).toString());
        }

        emotion_sel = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}