package com.shopnc_local_life.android.ui.market;

//import android.R;
import com.shopnc_local_life.android.R;
import com.shopnc_local_life.android.ui.my.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MarketLoginActivity extends Activity {

	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_login);
		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
             Intent intent = new Intent(MarketLoginActivity.this,LoginActivity.class);
             startActivity(intent);
             
             //intent.setClassName(this, LoginActivity.class);
             
			}
		});

	}

}
