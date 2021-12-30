package com.sedev.emotionapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder> {
    private ArrayList<EmotionItem> mEmotionList;
    private OnItemClickListener mListner;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListner = listener;
    }

    public static class EmotionViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextview;
        public TextView mTextview2;
        public TextView mDateView;


        public EmotionViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextview  = itemView.findViewById(R.id.textView);
            mTextview2 = itemView.findViewById(R.id.textView2);
            mDateView = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(listener != null){
                            int position =getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                listener.onItemClick(position);
                            }


                        }
                }
            });

        }
    }

    public EmotionAdapter(ArrayList<EmotionItem> emotionList){
        mEmotionList = emotionList;
    }

    @Override
    public EmotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_item,parent,false);
        EmotionViewHolder evh = new EmotionViewHolder(view, mListner);
        return evh;
    }

    @Override
    public void onBindViewHolder(EmotionAdapter.EmotionViewHolder holder, int position) {
        EmotionItem currentItem = mEmotionList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextview.setText(currentItem.getText1());
        holder.mTextview2.setText(currentItem.getText2());
        holder.mDateView.setText(currentItem.getmDate());
    }

    @Override
    public int getItemCount() {
        return mEmotionList.size();
    }
}
