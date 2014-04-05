package com.elvn.finger.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvn.finger.R;
import com.elvn.finger.constrant.Const;
import com.elvn.finger.entity.Topic;

public class TopicAdapter extends BaseAdapter {

	private Context context;
	private List<Topic> topickList;
	public int showCount = Const.SHOWCOUNT;
	
	public TopicAdapter(Context context, List<Topic> topickList) {
		this.context = context;
		this.topickList = topickList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return showCount;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lv_title_item, null);
			holder.tv_title_item = (TextView)convertView.findViewById(R.id.tv_title_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv_title_item.setText(topickList.get(position).getTitle());
		return convertView;
	}

	class ViewHolder {
		TextView tv_title_item;
	}
}
