package com.pierr;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;



public class TransparentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.transparent_layout);
		
		
	    View viewRoot = findViewById(android.R.id.content);
		
		Window window = getWindow();
		
		//Show the Window and ViewRoot attribution in the TextArea
		
		StringBuilder sb = new StringBuilder("Info of Activity1");
		
		sb.append("window " + window)
		  .append("viewRoot " + viewRoot);
		
		TextView textV = (TextView)findViewById(R.id.trans_text);
		
		textV.setText(sb.toString());
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	
}