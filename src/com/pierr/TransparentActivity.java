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
		
		
<<<<<<< HEAD
		View viewRoot = findViewById(android.R.id.content);
=======
	    View viewRoot = findViewById(android.R.id.content);
>>>>>>> 46f3c737d500c177f943d424bcadf2b820061596
		
		Window window = getWindow();
		
		//Show the Window and ViewRoot attribution in the TextArea
		
<<<<<<< HEAD
		StringBuilder sb = new StringBuilder("Info of current Activity\n");
		
		sb.append("window " + window).append("\n")
=======
		StringBuilder sb = new StringBuilder("Info of Activity1");
		
		sb.append("window " + window)
>>>>>>> 46f3c737d500c177f943d424bcadf2b820061596
		  .append("viewRoot " + viewRoot);
		
		TextView textV = (TextView)findViewById(R.id.trans_text);
		
		textV.setText(sb.toString());
		
<<<<<<< HEAD
		
=======
>>>>>>> 46f3c737d500c177f943d424bcadf2b820061596
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