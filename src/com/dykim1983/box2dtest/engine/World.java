package com.dykim1983.box2dtest.engine;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.dykim1983.box2dtest.polygon.Polygon;


public class World {
	private static Thread worldThread;
	
	private List<Polygon> poly;
	
	public World() {
		worldThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
			}
		});
        worldThread.start();
	}
	
	
	private class WorldTask extends AsyncTask<Float, Long, Long> {
		private long counter = 0;
		
	    protected Long doInBackground(Float... yposes) {
	    	counter++;
	    	for (Float ypos : yposes) {
	    		ypos += 0.1f;
	        }
	    	return counter;
	    }

	    protected void onProgressUpdate(Long... progress) {
	    	//Log.i("OpenGLRenderer.class", String.format("%d", counter));
	    	if(60L > counter) {
	    		//Log.i("OpenGLRenderer.class", String.format("%f", ypos));
	    		//Log.i("OpenGLRenderer.class", String.format("%d", counter));
	    	}
	    }

	    protected void onPostExecute(Long counter) {
	    	Log.i("OpenGLRenderer.class", String.format("%d", counter));
	    	if(60L > counter) {
	    		
	    	} else return;
	    }
	}


}
