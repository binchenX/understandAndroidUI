/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pierr;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Demonstrates how to take over the Surface from a window to do direct drawing
 * to it (without going through the view hierarchy).
 */
public class UnderstandAndroidUIActivity extends Activity implements
		SurfaceHolder.Callback2 {
	DrawingThread mDrawingThread;

	static final String TAG = "UnderstandAndroidUI";

	//Window mWindow = null;

	MySurfaceView mSurfaceView = null;

	UnderstandAndroidUIActivity that = this;

	boolean toogle = false; // false means pause
	
	int requestCode = 0x1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Tell the activity's window that we want to do our own drawing
		// to its surface. This prevents the view hierarchy from drawing to
		// it, though we can still add views to capture input if desired.
		// getWindow().takeSurface(this);

		setContentView(R.layout.main);


		
		
		//I want to know 
		// 1. which window i belong to
		// 2. my ViewRoot
		// 3. My Surface
		
		//http://stackoverflow.com/questions/4486034/android-how-to-get-root-view-from-current-activity
		//which is a frameLayout
		View viewRoot = findViewById(android.R.id.content);
		
		
		Window window = getWindow();
		
		//It is this decoView that will be treated as Window and 
		//add to the Window Manager
		
		View decoView = window.getDecorView();
		
		//Show the Window and ViewRoot attribution in the TextArea
		

		StringBuilder sb = new StringBuilder("Info of Activity1\n");
		
		//WindowManager wm = window.getWindowManager();
		
		
		
		sb.append("\n window " + window)
		  .append("\n decoView " + decoView)
		  .append("\n viewRoot " + viewRoot)
		  .append("\n width " +  viewRoot.getWidth())   // 0 
		  .append("\n height " + viewRoot.getHeight())  // 0 
		  ;
		
		TextView textV = (TextView)findViewById(R.id.text1);
		
		textV.setTextSize(15.0f);
		textV.setText(sb.toString());
		
		
		//View viewRoot2 = getWindow().getDecorView().findViewById(android.R.id.content)?
		
		

		// just to getTheSurfaceHold...
		// It is not later try to hold the surface when you click the button

		// mWindow.takeSurface(this);

		setUpListeners();
//
		mSurfaceView = (MySurfaceView) findViewById(R.id.surfaceView1);

		// This is the thread that will be drawing to our surface.
		// mDrawingThread = new DrawingThread();
		// mDrawingThread.start();

	}

	private void drawSurfaceView() {

		//Canvas canvas = mSurfaceView.getHolder().lockCanvas();

		//mSurfaceView.draw(canvas);

		//mSurfaceView.getHolder().unlockCanvasAndPost(canvas);

	}

	private void setUpListeners() {

		Button bt = (Button) findViewById(R.id.button1);

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// mWindow.takeSurface(that);
				toogle = !toogle;
				if (toogle == true) {
					Log.d(TAG, "Let's play mSurfaceView");
					mSurfaceView.play();
					
				} else {
					Log.d(TAG, "Let's pause mSurfaceView");
					mSurfaceView.pause();
					Log.d(TAG, "end pause ");
				}

			}
		});
		
		
		Button bt2 = (Button) findViewById(R.id.button2);

		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(UnderstandAndroidUIActivity.this, TransparentActivity.class);
					
				startActivityForResult(intent, requestCode);
				

			}
		});

	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();

		/*
		 * // Make sure the drawing thread is not running while we are paused.
		 * synchronized (mDrawingThread) { mDrawingThread.mRunning = false;
		 * mDrawingThread.notify(); }
		 */

	}

	@Override
	protected void onResume() {
		super.onResume();

		/*
		 * // Let the drawing thread resume running. synchronized
		 * (mDrawingThread) { mDrawingThread.mRunning = true;
		 * mDrawingThread.notify(); }
		 */
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		/*
		 * // Make sure the drawing thread goes away. synchronized
		 * (mDrawingThread) { mDrawingThread.mQuit = true;
		 * mDrawingThread.notify(); }
		 */
	}

	public void takeOver() {

		/*
		 * Log.d(TAG, "takeOver"); if(mDrawingThread.mSurfaceHolder != null) {
		 * mDrawingThread.notify(); }
		 */
	}

	/* implement SurfaceHolder.Callback2 */

	public void surfaceCreated(SurfaceHolder holder) {

		Log.d(TAG, "surfacecreated");
		// Tell the drawing thread that a surface is available.
		synchronized (mDrawingThread) {
			mDrawingThread.mSurfaceHolder = holder;
			mDrawingThread.notify();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// Don't need to do anything here; the drawing thread will pick up
		// new sizes from the canvas.
	}

	public void surfaceRedrawNeeded(SurfaceHolder holder) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// We need to tell the drawing thread to stop, and block until
		// it has done so.
		Log.d(TAG, "surfaceDestroyed");
		synchronized (mDrawingThread) {
			mDrawingThread.mSurfaceHolder = holder;
			mDrawingThread.notify();

			while (mDrawingThread.mActive) {
				try {
					mDrawingThread.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This is a thread that will be running a loop, drawing into the window's
	 * surface.
	 */
	class DrawingThread extends Thread {
		// These are protected by the Thread's lock.
		SurfaceHolder mSurfaceHolder;
		boolean mRunning = false;
		boolean mActive;
		boolean mQuit;

		// Internal state.
		int mLineWidth;
		float mMinStep;
		float mMaxStep;

		boolean mInitialized;
		// final MovingPoint mPoint1 = new MovingPoint();
		// final MovingPoint mPoint2 = new MovingPoint();

		static final int NUM_OLD = 100;
		int mNumOld = 0;
		final float[] mOld = new float[NUM_OLD * 4];
		final int[] mOldColor = new int[NUM_OLD];
		int mBrightLine = 0;

		float startX = 0;
		float startY = 0;

		// X is red, Y is blue.
		// final MovingPoint mColor = new MovingPoint();

		final Paint mBackground = new Paint();
		final Paint mForeground = new Paint();

		int makeGreen(int index) {
			int dist = Math.abs(mBrightLine - index);
			if (dist > 10)
				return 0;
			return (255 - (dist * (255 / 10))) << 8;
		}

		@Override
		public void run() {
			mLineWidth = (int) (getResources().getDisplayMetrics().density * 1.5);
			if (mLineWidth < 1)
				mLineWidth = 1;
			mMinStep = mLineWidth * 2;
			mMaxStep = mMinStep * 3;

			mBackground.setColor(0xff000000);
			mForeground.setColor(0xff00ffff);
			mForeground.setAntiAlias(false);
			mForeground.setStrokeWidth(mLineWidth);

			// for (int i = 0; i< 100; i++)
			{
				// while (true) {
				// Synchronize with activity: block until the activity is ready
				// and we have a surface; report whether we are active or
				// inactive
				// at this point; exit thread when asked to quit.

				try {

					Thread.sleep(1000);
				} catch (Exception ex) {
					Log.d(TAG, "Sleep 1 seoncd was interrupted");

				}
				synchronized (this) {
					while (mSurfaceHolder == null || !mRunning) {
						Log.d(TAG, "waiting to run..");
						if (mActive) {
							mActive = false;
							notify();
						}
						if (mQuit) {
							return;
						}
						try {
							wait();
						} catch (InterruptedException e) {
						}
					}

					if (!mActive) {
						mActive = true;
						notify();
					}

					// Lock the canvas for drawing.
					Canvas canvas = mSurfaceHolder.lockCanvas();

					Log.d(TAG, "Will modify the surface attribution");
					/*
					 * Let's see what we can do with the surface*
					 * 
					 * These change are useless!!!
					 */
					Surface surface = mSurfaceHolder.getSurface();

					surface.setAlpha(0.2f);
					surface.setLayer(1);
					surface.setPosition(100, 100);
					surface.setSize(300, 300);

					Log.d(TAG, "lockCanvas");
					if (canvas == null) {
						Log.i("WindowSurface", "Failure locking canvas");
						// continue;
					}

					StringBuilder info = new StringBuilder();

					info.append(" width  :" + canvas.getWidth()).append(
							" height:  " + canvas.getHeight()).append(
							" density: " + canvas.getDensity());

					// canvas.

					Log.d(TAG, info.toString());

					canvas.drawText("DrawLine from (0 ,100 ) to (400,100)", 50,
							50, mForeground);
					// draw a line
					canvas.drawLine(0, 100, 400, 100, mForeground);

					canvas
							.drawText(
									"DrawLine from (0 ,160 ) to (400,160) but with clip (0,150,200,200) ",
									50, 120, mForeground);

					canvas.clipRect(new Rect(0, 150, 200, 200));

					// draw a line
					canvas.drawLine(0, 160, 400, 160, mForeground);

					try {
						// Thread.sleep(2000);
					} catch (Exception ex) {

					}

					// All done!
					mSurfaceHolder.unlockCanvasAndPost(canvas);
					Log.d(TAG, "unlockCanvasAndPost");
				}
			}
		}
	}
}
