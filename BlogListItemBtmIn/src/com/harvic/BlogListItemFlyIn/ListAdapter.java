package com.harvic.BlogListItemFlyIn;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qijian on 16/3/12.
 */
public class ListAdapter extends BaseAdapter {
    private List<Drawable> mDrawableList = new ArrayList<Drawable>();
    private int mLength = 0;
    private LayoutInflater mInflater;
    private Context mContext;
    private ListView mListView;
    private Animation animation;
    private int mFirstTop, mFirstPosition;
    private boolean isScrollDown;

    public ListAdapter(Context context, ListView listView, List<Drawable> drawables, int length) {
        mDrawableList.addAll(drawables);
        mLength = length;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListView = listView;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in_anim);
        mListView.setOnScrollListener(mOnScrollListener);
    }

    AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = view.getChildAt(0);
            if (firstChild == null) return;
            int top = firstChild.getTop();
            /**
             * firstVisibleItem > mFirstPosition琛ㄧず鍚戜笅婊戝姩涓�鏁翠釜Item
             * mFirstTop > top琛ㄧず鍦ㄥ綋鍓嶈繖涓猧tem涓粦鍔�
             */
            isScrollDown = firstVisibleItem > mFirstPosition || mFirstTop > top;
            mFirstTop = top;
            mFirstPosition = firstVisibleItem;
        }
    };

    @Override
    public int getCount() {
        return mLength;
    }

    @Override
    public Object getItem(int position) {
        return mDrawableList.get(position % mDrawableList.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.img);
            holder.mTextView = (TextView) convertView.findViewById(R.id.text);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //娓呴櫎褰撳墠鏄剧ず鍖哄煙涓墍鏈塱tem鐨勫姩鐢�
        for (int i=0;i<mListView.getChildCount();i++){
            View view = mListView.getChildAt(i);
            view.clearAnimation();
        }
        //鐒跺悗缁欏綋鍓峣tem娣诲姞涓婂姩鐢�
        if (isScrollDown) {
            convertView.startAnimation(animation);
        }
        convertView.setTag(holder);

        holder.mImageView.setImageDrawable(mDrawableList.get(position % mDrawableList.size()));
        holder.mTextView.setText(position + "");

        return convertView;
    }

    public class ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
    }
}
