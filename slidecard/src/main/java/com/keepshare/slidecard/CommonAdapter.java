package com.keepshare.slidecard;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xinxin
 * @CreateDate: 2020-02-10
 * @Copyright: 海康威视, All rights reserved
 * @Description:
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder>{

    private static final String TAG = "CommonAdapter";
    protected Context mContext;
    protected List<T> dataList;
    private int selectPosition = -1;
    private int limitCount = -1;

    private OnItemClickListener<T> itemClickListener;

    public CommonAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public int getDataListNum() {

        if (dataList == null || dataList.size() <= 0) return 0;

        return dataList.size();
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public void addDataList(List<T> dataList) {
        if (dataList != null && dataList != null && dataList.size() > 0) {
            this.dataList.addAll(dataList);
            notifyItemRangeInserted(this.dataList.size() - dataList.size(),
                    dataList.size());
        }
    }

    public void addHeadDataList(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.dataList.addAll(0, dataList);
            notifyDataSetChanged();
        }
    }

    public void insertItemRange(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.dataList.addAll(this.dataList.size(), dataList);
            notifyItemRangeInserted(this.dataList.size() - dataList.size(), dataList.size());
        }
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onViewAttachedToWindow(CommonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public int getSelectPosition() {
        return selectPosition;

    }

    public void setSelectPosition(int selectPosition) {
        if (this.selectPosition == selectPosition) {
            return;
        }
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public void setSelectPositionWithSame(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public void setSelectPositionNotNotify(int position) {
        this.selectPosition = position;
    }

    public View getItemView(int position, RecyclerView mListVIew) {
        RecyclerView.LayoutManager lm = mListVIew.getLayoutManager();
        return lm.findViewByPosition(position);
    }

    public void insertItemData(int position, T itemData) {
        if (itemData != null) {
            this.dataList.add(position, itemData);
            notifyItemInserted(position);
        }
    }

    public int insertItemData(T itemData) {
        if (dataList == null) return -1;
        final int targetPosition = dataList.size();
        if (itemData != null) {
            this.dataList.add(targetPosition, itemData);
            notifyItemChanged(targetPosition + 1);
        }
        return targetPosition;
    }

    public void insertItemDataRefresh(int position, T itemData) {
        if (itemData != null) {
            this.dataList.add(position, itemData);
            notifyDataSetChanged();
//            notifyItemInserted(1);
//            notifyItemRangeChanged(position, dataList.size() - position);
        }
    }

    public void removeItemData(int position) {
        this.dataList.remove(position);
        notifyDataSetChanged();
    }

    public void removeItemDataRefresh(int position, T itemData) {
        this.dataList.remove(position);
        notifyItemRangeChanged(position, dataList.size() - position);
    }

    public void removeItemData(T itemData) {
        this.dataList.remove(itemData);
        notifyDataSetChanged();
    }

    public T getItemData(int position) {
        return dataList != null && dataList.size() > 0 ? dataList.get(position) : null;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (CommonViewHolder) new CommonViewHolder(mContext, LayoutInflater.from(mContext)
                .inflate(bindItemView(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        final T itemData = getItemData(position);
        onBindView(holder, itemData, position);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(position, itemData);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (limitCount < 0) {
            return dataList != null && dataList.size() > 0 ? dataList.size() : 0;
        } else {
            return dataList != null && dataList.size() > limitCount ? limitCount : dataList.size();
        }
    }

    public abstract void onBindView(CommonViewHolder holder, T itemData, int position);

    public abstract int bindItemView(int viewType);


    public static class CommonViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> itemViews;//行布局中的 view

        private Context tempContext;

        public CommonViewHolder(Context context, View itemView) {
            super(itemView);
            this.tempContext = context;
            itemViews = new SparseArray<>();
        }

        public <V extends View> V getView(int id) {
            View view = itemViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                itemViews.put(id, view);
            }
            return (V) view;
        }

        public View findView(int viewId) {
            return itemView.findViewById(viewId);
        }

        public ImageView getImageView(int viewId) {
            return getView(viewId);
        }

        public CommonViewHolder setText(int viewId, String value) {
            TextView view = getView(viewId);
            view.setText(value);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setEnable(int viewId, boolean enable) {
            View view = getView(viewId);
            view.setEnabled(enable);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(tempContext.getResources().getColor(textColor));
            return CommonViewHolder.this;
        }

        public CommonViewHolder setTextSize(int viewId, int textSize) {
            TextView view = getView(viewId);
            view.setTextSize(textSize);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(tempContext.getResources().getColor(color));
            return CommonViewHolder.this;
        }

        public CommonViewHolder setBackgroundResource(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setImageBitmap(@IdRes int viewId, Bitmap bm){
            View view = getView(viewId);
            ((ImageView)view).setImageBitmap(bm);
            return CommonViewHolder.this;
        }


        public CommonViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return CommonViewHolder.this;
        }

        public CommonViewHolder setInvisible(int viewId) {
            View view = getView(viewId);
            view.setVisibility(View.INVISIBLE);
            return CommonViewHolder.this;
        }
    }

    public interface OnItemClickListener<T> {

        void onItemClick(int position, T itemData);
    }
}

