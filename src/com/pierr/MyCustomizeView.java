package com.pierr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;




public class MyCustomizeView extends View{

	Paint mPaintBackground = new Paint();
	Paint mPaintText = new Paint();
	public MyCustomizeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//mPaint.setColor(Color.GREEN);
		//mPaintBackground.setARGB(255, 255, 0, 0);
	}

	
   //This is a must
	public MyCustomizeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		//mPaint.setColor(Color.RED);
		mPaintBackground.setARGB(255, 255, 0, 0);
		mPaintText.setColor(Color.GREEN);
		mPaintText.setTextSize(10);
		
		//mPaint.s
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
	    super.onDraw(canvas);
		
		
		//I want to know the canvas size
		int canvas_width = canvas.getWidth();
		int canvas_height = canvas.getHeight();
		
		//my RootView will lock and unlock the canvas for me , so all i need is to
		//draw 
		//compare to what you should do in SurfaceView
		
		//how can i know the component's topLeft
		int myview_width = getMeasuredWidth();
		int myview_height = getMeasuredHeight();
		
		
		int top = getTop(); 
		int left = getLeft();
		
		Log.d("MyCustomizeView", "onDraw is called");
		
		//All the coordination you specified here is relative to the parent view.
		//cool!
		Rect r = new Rect(0, 0, myview_width, myview_height);
		
		//this will fillRect
		canvas.drawRect(r, mPaintBackground);
		
		StringBuilder info = new StringBuilder();
		
		info.append(" canvas width = " + canvas_width)
		   .append(" heigth = " + canvas_height)
		   .append(" view width = " + myview_width)
		   .append(" height = " + myview_height);
		
		canvas.drawText(info.toString(), 
				50,  50, 
				mPaintText);
		
		
	}
	
	
	
	
}