package com.sedev.emotionapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EmotionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EmotionItem> emotionList = new ArrayList<>();

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        dialog = new Dialog(this);

        loadData();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new EmotionAdapter(emotionList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EmotionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                dialog.setContentView(R.layout.win_layout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tv_dialog_emotion = dialog.findViewById(R.id.tv_dialog_emotion);
                TextView tv_descrip = dialog.findViewById(R.id.tv_descrip);
                TextView tv_dialog_date = dialog.findViewById(R.id.tv_dialog_date);
                ImageView iv_dialog_pic = dialog.findViewById(R.id.iv_dialog_pic);
                ImageView iv_dialog_emo = dialog.findViewById(R.id.iv_dialog_emo);

                iv_dialog_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                tv_dialog_emotion.setText(emotionList.get(position).getText1());
                tv_descrip.setText(emotionList.get(position).getText2());
                iv_dialog_emo.setImageResource(emotionList.get(position).getImageResource());
                tv_dialog_date.setText(emotionList.get(position).getmDate());

                dialog.show();

            }
        });

    }


    @Override
    public void onBackPressed() {


        Intent i = new Intent(RecordActivity.this, EmotionActivity.class);
        startActivity(i);
        finish();


    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task List",null);
        Type type = new TypeToken<ArrayList<EmotionItem>>() {}.getType();
        emotionList = gson.fromJson(json, type);

        if(emotionList == null){
            emotionList = new ArrayList<>();
        }
    }
}