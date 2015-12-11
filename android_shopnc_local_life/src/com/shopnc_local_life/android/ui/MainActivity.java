package com.shopnc_local_life.android.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.shopnc_local_life.android.R;
import com.shopnc_local_life.android.common.MyApp;
import com.shopnc_local_life.android.ui.home.HomeActivity;
import com.shopnc_local_life.android.ui.home.CategoryActivity;
import com.shopnc_local_life.android.ui.market.MarketLoginActivity;
import com.shopnc_local_life.android.ui.my.MyActivity;
import com.shopnc_local_life.android.widget.MyMainOutDialog;

public class MainActivity extends TabActivity{
	/** tab标签名*/
	public final static String TAB_TAG_HOME = "home";
	public final static String TAB_TAG_TEST1 = "test1";
	public final static String TAB_TAG_TEST2 = "test2";
	public final static String TAB_TAG_TEST3 = "test3";
	
	private TabHost tabHost;
	
	/** 启动每个操作项的Intent */
	private Intent homeIntent;
	private Intent test1Intent;
	private Intent test2Intent;
	private Intent test3Intent;

	
	/** 界面上的各个单选按钮 */
	private RadioButton btn_home;
	private RadioButton btn_test1;
	private RadioButton btn_test2;
	private RadioButton btn_test3;
	
	private String city_name;
	
	private MyApp myApp; 
	
	public MyMainOutDialog dialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		
		myApp = (MyApp) MainActivity.this.getApplication();
		
		dialog=new MyMainOutDialog(MainActivity.this);
		
		city_name=MainActivity.this.getIntent().getStringExtra("city_name");
		
		homeIntent = new Intent(this, HomeActivity.class);
		homeIntent.putExtra("city_name", city_name);
		test1Intent = new Intent(this, CategoryActivity.class);
		test1Intent.putExtra("class_id","2");
		test1Intent.putExtra("position",0);
		test2Intent = new Intent(this, MarketLoginActivity.class);
		test3Intent = new Intent(this, MyActivity.class);
		

		
		tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_HOME).setIndicator(TAB_TAG_HOME).setContent(homeIntent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_TEST1).setIndicator(TAB_TAG_TEST1).setContent(test1Intent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_TEST2).setIndicator(TAB_TAG_TEST2).setContent(test2Intent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_TEST3).setIndicator(TAB_TAG_TEST3).setContent(test3Intent));

		
		tabHost.setCurrentTab(0);
		////////////////////// find View ////////////////////////////
		btn_home = (RadioButton)this.findViewById(R.id.main_index_firstpage);
		btn_test1 = (RadioButton)this.findViewById(R.id.main_index_category);
		btn_test2 = (RadioButton)this.findViewById(R.id.main_index_market);
		btn_test3 = (RadioButton)this.findViewById(R.id.main_index_my);
		
		
		myApp.setFirst_start_flag("1");
		myApp.setTabHost(tabHost);
		myApp.setBtn_test2(btn_test2);
		
		MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
		btn_home.setOnClickListener(listener);
		btn_test1.setOnClickListener(listener);
		btn_test2.setOnClickListener(listener);
		btn_test3.setOnClickListener(listener);
		
		dialog.text_btu_on.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.this.finish();
			}
		});
		dialog.text_btu_off.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
	}
	
	class MyRadioButtonClickListener implements View.OnClickListener{
		public void onClick(View v) {
			RadioButton btn = (RadioButton)v;
			switch(btn.getId()){
			case R.id.main_index_firstpage:
				tabHost.setCurrentTabByTag(TAB_TAG_HOME);
				break;
			case R.id.main_index_category:
				tabHost.setCurrentTabByTag(TAB_TAG_TEST1);
				break;
			case R.id.main_index_market:
				tabHost.setCurrentTabByTag(TAB_TAG_TEST2);
				break;
			case R.id.main_index_my:
				tabHost.setCurrentTabByTag(TAB_TAG_TEST3);
				break;
			}
		}
	}
}