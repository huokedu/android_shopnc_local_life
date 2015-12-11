/**
 * 
 */
package com.shopnc_local_life.android.ui.home;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.shopnc_local_life.android.R;
import com.shopnc_local_life.android.Adapter.ViewMoreLeftAdapter;
import com.shopnc_local_life.android.Adapter.ViewMoreRightAdapter;
import com.shopnc_local_life.android.common.Constants;
import com.shopnc_local_life.android.handler.RemoteDataHandler;
import com.shopnc_local_life.android.handler.RemoteDataHandler.Callback;
import com.shopnc_local_life.android.modle.CategoryList;
import com.shopnc_local_life.android.modle.ResponseData;
import com.shopnc_local_life.android.ui.MainActivity;
import com.shopnc_local_life.android.ui.Store.StoreListActivity;
import com.shopnc_local_life.android.ui.my.MyActivity;

/**
 * @author jingang
 * 
 */
public class CategoryActivity extends Activity {
	private ArrayList<CategoryList> datas_parent_class;
	private ArrayList<CategoryList> datas_class_json;
	private ListView listview1;
	private ListView listview2;
	private ViewMoreLeftAdapter adapter;
	private ViewMoreRightAdapter adapter2;
	private String class_id = "2";
	private int position = 0;

	// private ImageButton btn_back_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);
		class_id = CategoryActivity.this.getIntent().getStringExtra("class_id");
		position = CategoryActivity.this.getIntent().getIntExtra("position", 0);
		listview1 = (ListView) findViewById(R.id.list_view1);
		listview2 = (ListView) findViewById(R.id.list_view2);
		adapter = new ViewMoreLeftAdapter(CategoryActivity.this);
		adapter2 = new ViewMoreRightAdapter(CategoryActivity.this);
		datas_parent_class = new ArrayList<CategoryList>();
		datas_class_json = new ArrayList<CategoryList>();
		info(position, class_id);
		listview1.setAdapter(adapter);
		listview2.setAdapter(adapter2);
		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int arg2,
					long arg3) {
				CategoryList d = (CategoryList) listview1
						.getItemAtPosition(arg2);
				datas_class_json.clear();
				info(arg2, d.getClass_id());
			}
		});
		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter2.setSelectItem(arg2);
				adapter2.notifyDataSetChanged();
				CategoryList d = (CategoryList) listview2
						.getItemAtPosition(arg2);
				Intent intent = new Intent(CategoryActivity.this,
						StoreListActivity.class);
				intent.putExtra("class_id", d.getClass_id());
				intent.putExtra("url_flag", "storeclass");
				CategoryActivity.this.startActivity(intent);
				//CategoryActivity.this.finish();
			}
		});
	}

	public void info(final int arg, String class_id) {
		RemoteDataHandler.asyncGet3(Constants.URL_CATEGORY_LIST + "&class_id="
				+ class_id, new Callback() {
			@Override
			public void dataLoaded(ResponseData data) {
				if (data.getCode() == HttpStatus.SC_OK) {
					String json = data.getJson();

					try {
						JSONObject obj = new JSONObject(json);
						String parent_class = obj.getString("parent_class")
								.toString();
						String class_json = obj.getString("class").toString();
						datas_parent_class = CategoryList
								.newInstanceList(parent_class);
						datas_class_json = CategoryList
								.newInstanceList(class_json);

						adapter.setDatas(datas_parent_class);
						adapter.setSelectItem(arg);
						adapter.notifyDataSetChanged();

						adapter2.setDatas(datas_class_json);
						adapter2.setSelectItem(0);
						adapter2.notifyDataSetChanged();

					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Toast.makeText(CategoryActivity.this, "加载数据失败，请稍后重试",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			((MainActivity) CategoryActivity.this.getParent()).dialog.show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
