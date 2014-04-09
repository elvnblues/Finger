package com.elvn.finger.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elvn.finger.R;
import com.elvn.finger.adapter.TopicAdapter;
import com.elvn.finger.constrant.Const;
import com.elvn.finger.db.dao.TopicDao;
import com.elvn.finger.entity.Topic;

public class MainActivity extends Activity {

	// UI
	private EditText ed_search_title;
	private Button btn_search_title;
	private ListView lv_title;
	private RelativeLayout rl_nofound;

	private TextView title_top_text;
	private Button title_top_setting;

	private LinearLayout ll_title_more;

	// DATA

	private TopicDao tpickDao;
	private List<Map<String, String>> topicList;

	private TopicAdapter adapter;

	// more View
	private View moreView;
	// 最后可见条目的索引
	private int lastVisibleIndex;
	// ListView item的数量
	private int titleListViewCount;
	private int showCount = 10;// 显示的数量
	private Handler handler = new Handler();
	private int prePageNum = 0;
	private boolean toListViewFoot = false;//是否已经到底

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_main);
		init();
		setListener();
	}

	private void init() {
		ed_search_title = (EditText) findViewById(R.id.ed_search_title);
		btn_search_title = (Button) findViewById(R.id.btn_search_title);
		lv_title = (ListView) findViewById(R.id.lv_title);
		rl_nofound = (RelativeLayout)findViewById(R.id.rl_nofound);
		
		//标题栏初始化
		title_top_text = (TextView) findViewById(R.id.title_top_text);
		title_top_text.setText(R.string.app_name);
		title_top_setting = (Button) findViewById(R.id.title_top_right); 
		title_top_setting.setBackgroundResource(R.drawable.title_top_setting);
		title_top_setting.setVisibility(View.VISIBLE);

		moreView = getLayoutInflater().inflate(R.layout.lv_title_more, null);
		moreView.setTag(Const.LISTMORE);
		ll_title_more = (LinearLayout) moreView
				.findViewById(R.id.ll_title_more);
		ll_title_more.setVisibility(View.VISIBLE);

		inflateList(ed_search_title.getText().toString());
	}

	private void setListener() {
		btn_search_title.setOnClickListener(btn_OnClickListener);
		title_top_setting.setOnClickListener(btn_OnClickListener);
		lv_title.setOnItemClickListener(lv_OnItemClickListener);
		lv_title.setOnScrollListener(lv_OnScrollListener);
	}

	/**
	 * 根据输入的title 查看count数量的title ListView
	 * 
	 * @param inf_title
	 * @param count
	 */
	private void inflateList(String inf_title) {
		tpickDao = new TopicDao(MainActivity.this);

		topicList = tpickDao.searchTitle(inf_title,2);
		titleListViewCount = topicList.size();// 获得ListView item的数量
		/*
		 * 当查询到数据时显示到ListView中
		 */
		if(titleListViewCount>0){
			List<Topic> listValue = new ArrayList<Topic>();
			for (int i = 0; i < topicList.size(); i++) {
				Map<String, String> listMap = topicList.get(i);
				Topic topic = new Topic();
				topic.setTitle(listMap.get("title"));
				topic.setContent(listMap.get("content"));
				listValue.add(topic);
			}
			adapter = new TopicAdapter(MainActivity.this, listValue);
			//lv_title.addFooterView(moreView);
			lv_title.setVisibility(View.VISIBLE);
			rl_nofound.setVisibility(View.GONE);
			lv_title.setAdapter(adapter);
		}else{//隐藏ListView，显示查询不到数据
			lv_title.setVisibility(View.GONE);
			rl_nofound.setVisibility(View.VISIBLE);
		}
		
	}

	private OnClickListener btn_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()){
			case R.id.btn_search_title:
				inflateList(ed_search_title.getText().toString());
				hideKeyBoard();
				break;
			case R.id.title_top_right://设置
				Intent settingIntent = new Intent();
				settingIntent.setClass(MainActivity.this, SettingAct.class);
				startActivity(settingIntent);
				break;
			}
			
		}
	};
	private void hideKeyBoard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(ed_search_title.getWindowToken(),0);
	}
	private OnItemClickListener lv_OnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Map topickMap = topicList.get(position);
			String titleString = (String) topickMap.get("title");
			String contentString = (String) topickMap.get("content");
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, TopicContent.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", titleString);
			bundle.putString("content", contentString);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	private OnScrollListener lv_OnScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (lastVisibleIndex == adapter.showCount
					&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				Log.v("@@@@@@", "ScrollStateChanged>>>state:" + scrollState
						+ "lastItem:" + lastVisibleIndex);
				if (adapter.showCount <= titleListViewCount) {
					if(titleListViewCount-adapter.showCount>=Const.SHOWCOUNT){
						adapter.showCount += Const.SHOWCOUNT;
					}else{
						adapter.showCount = titleListViewCount;
					}
					adapter.notifyDataSetChanged();
					Log.v("@@@@@@", "onScrollStateChanged  "
							+ adapter.showCount);
				} else {
					//lv_title.removeFooterView(moreView);
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			Log.v("@@@@@@", "Scroll>>>first: " + firstVisibleItem
					+ ", visible: " + visibleItemCount + ", total: "
					+ totalItemCount);
			lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
			Log.i("@@@@@@", "Scroll>>>lastItem:" + lastVisibleIndex);
			// 显示50条ListItem，即0-49，因为onScroll是在“滑动”执行过之后才触发，所以用adapter.count<=41作条件
			Log.i("@@@@@@", "Scroll>>>titleListViewCount:"+titleListViewCount);
			Log.i("@@@@@@", "Scroll>>>adapter.showCount:"+adapter.showCount);
			if (adapter.showCount <= titleListViewCount) {
								
				if (firstVisibleItem + visibleItemCount == totalItemCount && !toListViewFoot) {
					//判断list的总内容长度去掉显示出来的数量是否还可以再显示10条
					if(titleListViewCount-adapter.showCount>=Const.SHOWCOUNT){
						adapter.showCount += Const.SHOWCOUNT;
						
					}else{
						adapter.showCount = titleListViewCount;
					}
					adapter.notifyDataSetChanged();
					
					Log.v("@@@@@@", "onScroll  " + adapter.showCount);
					int currentPage = 0;
					if(adapter.showCount % Const.SHOWCOUNT == 0){
						currentPage = adapter.showCount / Const.SHOWCOUNT;
						lv_title.setSelection(lastVisibleIndex);
					}else{
						currentPage = (adapter.showCount / Const.SHOWCOUNT) + 1;
					}
					if(prePageNum!=0 && prePageNum==currentPage){
						Toast.makeText(getApplicationContext(),
								"数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
						toListViewFoot = true;
					}else{
						prePageNum = currentPage;
						Toast.makeText(getApplicationContext(),
								"第" + currentPage + "页", Toast.LENGTH_LONG).show();
					}
					
				}
			} else {
				//lv_title.removeFooterView(moreView);
			}
		}
	};

}
