package com.list.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListdemoActivity extends Activity implements OnItemClickListener,
		OnTouchListener {
	private ArrayList<Map<String, Object>> mData;
	ListView mListView;
	MyAdapter mAdapter;
	public Button mDeleteButton, mEditButton;
	float x, y, upx, upy;
	private Map<Integer, Boolean> isFrist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_record);
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); // 获取当前年份
		int month = c.get(Calendar.MONTH);// 获取当前月份
		int day = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
		int hour = c.get(Calendar.HOUR_OF_DAY);// 获取当前的小时数
		int minute = c.get(Calendar.MINUTE);// 获取当前的分钟数
		isFrist = new HashMap<Integer, Boolean>();
		String minutes = "";
		if (minute < 10) {
			minutes = "0" + minute;
		}
		String time_value = year + ":" + month + ":" + day + ":" + hour + ":"
				+ minutes;

		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.ic_launcher);
			map.put("maxValue", "最高值：" + i);
			map.put("avgValue", "平均值：" + i);
			map.put("time", time_value);
			map.put("how_time", "持续时间: " + i);
			map.put("last_time", "最后时间：2012-11");
			map.put("isVisible", false);
			mData.add(map);
		}

		mAdapter = new MyAdapter(this);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setCacheColorHint(Color.TRANSPARENT); // 设置背景透明度
		mListView.setAdapter(mAdapter);
		mListView.setOnTouchListener(this);
		mListView.setLayoutAnimation(getListAnim());

	}

	private LayoutAnimationController getListAnim() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(500);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		return controller;
	}

	class ViewHolder {
		public TextView maxValue;
		public Button del_btn;
		public TextView how_time;
		public TextView last_time;
		public TextView avgValue;
		public ImageView image;
		public boolean isVisible = true;
	}

	public class MyAdapter extends BaseAdapter {
		private Context mContext;

		private MyAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mData.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.record_info, null);
				holder.del_btn = (Button) convertView.findViewById(R.id.del);
				holder.how_time = (TextView) convertView
						.findViewById(R.id.how_time);
				holder.last_time = (TextView) convertView
						.findViewById(R.id.last_time);
				holder.avgValue = (TextView) convertView
						.findViewById(R.id.avgValue);
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.maxValue = (TextView) convertView
						.findViewById(R.id.maxValue);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (isFrist.get(position) == null || isFrist.get(position)) {
				AnimationSet set = new AnimationSet(true);
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(500);
				set.addAnimation(animation);

				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation.setDuration(500);
				set.addAnimation(animation);
				convertView.startAnimation(set);
				isFrist.put(position, false);
			}
			holder.image.setBackgroundResource((Integer) mData.get(position)
					.get("image"));
			holder.del_btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mData.remove(position);
					mAdapter.notifyDataSetChanged();

				}
			});
			holder.how_time.setText((String) (mData.get(position)
					.get("how_time")));
			holder.last_time.setText((String) (mData.get(position)
					.get("last_time")));
			holder.avgValue.setText((String) (mData.get(position)
					.get("avgValue")));
			holder.maxValue.setText((String) (mData.get(position)
					.get("maxValue")));
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		new AlertDialog.Builder(ListdemoActivity.this)
				.setTitle("标题")
				.setMessage("提示内容")
				.setPositiveButton("删除", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mData.remove(position);
						mAdapter.notifyDataSetChanged();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("你好啊");
					}
				}).show();
	}

	// protected void removeListItem(View rowView, final int positon) {
	// final Animation animation = (Animation) AnimationUtils.loadAnimation(
	// rowView.getContext(), R.anim.item_anim);
	// animation.setAnimationListener(new AnimationListener() {
	// public void onAnimationStart(Animation animation) {
	// }
	//
	// public void onAnimationRepeat(Animation animation) {
	// }
	//
	// public void onAnimationEnd(Animation animation) {
	// mData.remove(positon);
	// mAdapter.notifyDataSetChanged();
	// animation.cancel();
	// }
	// });
	//
	// rowView.startAnimation(animation);
	// }

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			x = event.getX();
			y = event.getY();
			Log.v("@@@@@@", "is on touch down x = " + x + " ,y = " + y);
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			upx = event.getX();
			upy = event.getY();
			int position1 = ((ListView) v).pointToPosition((int) x, (int) y);
			int position2 = ((ListView) v)
					.pointToPosition((int) upx, (int) upy);

			// if (position1 == position2 && Math.abs(x - upx) > 10) {
			// View view = ((ListView) v).getChildAt(position1);
			// removeListItem(view, position1);
			// }
		}
		return false;
	}
}
