package com.pierr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Runnable {

	SurfaceHolder holder = null;
	Thread t;
	boolean shouldRun = false;

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
			shouldRun = true;
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

			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {

			}
			synchronized (this) {
				if (shouldRun == false) {
					try {

						Log.d("UnderstandAndroidUI", "Waiting to be run");
						wait();
					} catch (Exception ex) {

					}
				}

				Canvas canvas = holder.lockCanvas();

				int width = canvas.getWidth();
				int height = canvas.getHeight();

				Log.d("MySurfaceView", "width x height = " + width + ","
						+ height);

				y += 50;
				Paint paint = new Paint();
				paint.setARGB(255, 255, 0, 0);

				canvas.drawCircle(x, y, 20, paint);

				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void pause() {

		Log.d(TAG, "enter on pause");
		synchronized (this) {
			shouldRun = false;

		}
	}

	public void resume() {

		synchronized (this) {
			shouldRun = true;
			notify();
		}
	}

}
