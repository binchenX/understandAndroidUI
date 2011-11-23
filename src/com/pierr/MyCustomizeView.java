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
	
	String mFocusIndicate = "uknown";
	public MyCustomizeView(Context context) {
		super(context);
				init();
			
	}

	
    //This is a MUST if you want to use MyCustomizeView in the XML
	public MyCustomizeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
		
		setFocusable(true);
		mPaintBackground.setARGB(255, 255, 0, 0);
		mPaintText.setColor(Color.GREEN);
		mPaintText.setTextSize(10);
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
		
		
		canvas.drawText(mFocusIndicate,
				50,  100, 
				mPaintText);
		
		
	}


	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		//super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		
		//String msg = "";
		if(gainFocus == true)
		{
			mFocusIndicate = "I am in Focus";	
			
		}else {
			
			
			mFocusIndicate = "unFoucsed.";
		}
		
		invalidate();
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	
	
	
	
}