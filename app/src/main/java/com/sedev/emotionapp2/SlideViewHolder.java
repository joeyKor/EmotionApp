package com.sedev.emotionapp2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

class SlideViewHolder extends RecyclerView.ViewHolder {
    private RoundedImageView imageView;

    public SlideViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageSlide);
    }

    void setImage(SlideItem slideItem) {
        imageView.setImageResource(slideItem.getImage());
    }

}
