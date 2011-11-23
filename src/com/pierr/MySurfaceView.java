package com.pierr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Runnable {

	SurfaceHolder holder = null;
	Thread t;
	boolean mShouldRun = false;
	boolean mShouldExit = false;

	float x = 20;
	float y = 20;

	static final String TAG = "UnderstandAndroidUI";

	public MySurfaceView(Context context) {

		super(context);
		// TODO Auto-generated constructor stub

		holder = getHolder();

		Log.d("UnderstandAndroidUI", "MySurfaceView is created");

		t = new Thread(this);

	}

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		Log.d("UnderstandAndroidUI", "MySurfaceView 2  is created");
		// TODO Auto-generated constructor stub
		init();
	}
	
	//This constructor must be defined in order to use MySurfaceView in the XML file

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("UnderstandAndroidUI", "MySurfaceView 3 is created");
		// TODO Auto-generated constructor stub

		init();
	}

	public void init() {
		holder = getHolder();

		Log.d("UnderstandAndroidUI", "MySurfaceView is created");

		t = new Thread(this);
	}

	static boolean isFirstTime = true;

	void play() {

		if (isFirstTime) {
			isFirstTime = false;
			mShouldRun = true;
			t.start();
		} else {
			resume();
		}
	}

	@Override
	public void run() {
		while (true) {

			// run it slowly , otherwise , pause/resume might be blocked as it won't be able 
			// to get he monitor on this object by using synchronized
			
			//better to set the priority of this thread..
			
			if(mShouldExit)
			{
				Log.d("UnderstandAndroidUI", "SurfaceView draw Thread exit");
				return;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {

			}
			synchronized (this) {
				if (mShouldRun == false) {
					try {

						Log.d("UnderstandAndroidUI", "Waiting to be run");
						wait();
					} catch (Exception ex) {

					}
				}

				Canvas canvas = holder.lockCanvas();

				int width = canvas.getWidth();
				int height = canvas.getHeight();

				
				String text = "MySurfaceView canvas width " + width + " height " + height;
				
				
	

				Paint paint = new Paint();
				
				paint.setARGB(255, 255, 0, 0);
				
				

				
				canvas.drawText(text, 0, 40, paint);
	
				//print clip
				Rect r = canvas.getClipBounds();
				
				 text = "MySurfaceView canvas clip " + r.right + " height " + r.bottom;
				
				 canvas.drawText(text, 0, 60, paint);
				
				
				
				
				//draw an outline use width ,height
				
				//float []pts = {5 , 5 , width - 100 , 5, width - 50 , 5 , width - 50 , height -5};
				//canvas.drawLines(pts, paint);
				
				
				
				
//				float []pts2 = {0 , 5 , 400 , 5, 400 , 5 , 400 , height -5};
//				canvas.drawLines(pts2, paint);
//				
//				float []pts3 = {0 , 10 , 448 , 10, 448 , 10 , 448 , height -10};
//				canvas.drawLines(pts3, paint);
				
				//canvs.drawLine(10 ,10 , width - 100, 10)
				
				//the report canvas width is 450 , but actually, actually
				//you can only see half of the circle ,indicating that the 
				//valid width is only 400

				
				canvas.drawCircle(400, 50, 50, paint);
				
				y += 50;
				
			
				
				
				//The x ,y is relative to the left, top
				//drawing operation outside of the canvas will be clipped 
				canvas.drawCircle(x, y, 20, paint);

				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void pause() {

		Log.d(TAG, "enter on pause");
		synchronized (this) {
			mShouldRun = false;

		}
	}
	
	public void stop(){
		Log.d(TAG, "enter stop");
		synchronized (this) {
			mShouldExit = true;
			mShouldRun = false;
			//notify();
		}
		
		
	}

	public void resume() {

		synchronized (this) {
			mShouldRun = true;
			notify();
		}
	}

}
