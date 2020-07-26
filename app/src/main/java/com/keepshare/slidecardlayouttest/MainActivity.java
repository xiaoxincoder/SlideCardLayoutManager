package com.keepshare.slidecardlayouttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.keepshare.slidecard.DragCallback;
import com.keepshare.slidecard.SlideCardDragHelper;
import com.keepshare.slidecard.SlideCardLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private CardListAdapter mAdapter;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.card_rv);
        initList();
        initDataList();
    }

    private void initList() {
//        LinearLayoutManager lm = new LinearLayoutManager(this);
        final SlideCardLayoutManager lm = new SlideCardLayoutManager(this);
        rv.setLayoutManager(lm);
        mAdapter = new CardListAdapter(this);
        rv.setAdapter(mAdapter);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragCallback(mAdapter));
//        itemTouchHelper.attachToRecyclerView(rv);
        SlideCardDragHelper dragHelper = new SlideCardDragHelper(this, mAdapter);
        dragHelper.attachToParent(rv);
    }

    private void initDataList() {
        final List<CardBean> cardBeanList = new ArrayList<>();
        cardBeanList.add(new CardBean(i++,"http://img5q.duitang.com/uploads/item/201505/04/20150504155117_8i4Rk.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"http://cdnq.duitang.com/uploads/item/201505/04/20150504155014_irFvu.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"http://img5q.duitang.com/uploads/item/201309/17/20130917032356_hdGjF.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"http://img5q.duitang.com/uploads/item/201505/04/20150504155137_JWcRK.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"http://img5q.duitang.com/uploads/item/201505/04/20150504155036_zNrPT.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"http://img5q.duitang.com/uploads/item/201504/26/201504262026_weMcT.thumb.700_0.jpeg"));
        cardBeanList.add(new CardBean(i++,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560164210849&di=c6ea3fdd3ec938600ddde9022f46033c&imgtype=0&src=http%3A%2F%2Fbbs-fd.zol-img.com.cn%2Ft_s800x5000%2Fg4%2FM09%2F00%2F07%2FCg-4WlJA9zCIPZ8PAAQWAhRW0ssAAMA8wD2hYAABBYa996.jpg"));
        cardBeanList.add(new CardBean(i++,"http://imgq.duitang.com/uploads/item/201504/21/20150421H2323_uwdEs.jpeg"));

        mAdapter.setDataList(cardBeanList);
    }
}