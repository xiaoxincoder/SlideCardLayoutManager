package com.keepshare.slidecardlayouttest;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.keepshare.slidecard.CommonAdapter;

public class CardListAdapter extends CommonAdapter<CardBean> {

    public CardListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindView(CommonViewHolder holder, CardBean itemData, int position) {
        ImageView itemCover = holder.getImageView(R.id.item_cover);
        Glide.with(itemCover)
                .load(itemData.getPicPath())
                .centerCrop()
                .into(itemCover);
        holder.setText(R.id.item_text, String.valueOf(itemData.getIndex()));
    }

    @Override
    public int bindItemView(int viewType) {
        return R.layout.item_card;
    }
}
