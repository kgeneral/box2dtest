package com.dykim1983.box2dtest.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.dykim1983.box2dtest.opengl.polygon.Square;

public class OpenGLRenderer implements Renderer {
	
	private Square square;
	
	private float ypos = 0; 
	
	public OpenGLRenderer() {
		// Initialize our square. 		
		square = new Square();		
		final Handler handler = new Handler();		
		Thread th = new Thread(new Runnable() {			
			@Override			
			public void run() {
				handler.post(new Runnable() {
					@Override	
					public void run() {
							new WorldTask().execute(ypos);
					}
				});
				ypos += 0.1f;
			}		
		});
		th.start();
		
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.
         * microedition.khronos.opengles.GL10, javax.microedition.khronos.
         * egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // OpenGL docs.
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);// OpenGL docs.
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                          GL10.GL_NICEST);
		
		GLU.gluLookAt(gl, 0, 0, -5, 0, 0, -1, 0, 1, 0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.
         * microedition.khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                           GL10.GL_DEPTH_BUFFER_BIT);
		
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		
		gl.glTranslatef(0, 0, -10 + ypos);
		
		// Draw our square.
		square.draw(gl); // ( NEW )
		
		//ypos += 0.1f;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.
         * microedition.khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);// OpenGL docs.
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
		// Reset the projection matrix
		gl.glLoadIdentity();// OpenGL docs.
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f,
                                   (float) width / (float) height,
                                   0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
		// Reset the modelview matrix
		gl.glLoadIdentity();// OpenGL docs.
	}
	
	private class WorldTask extends AsyncTask<Float, Long, Long> {
		private long counter = 0;
		
	    protected Long doInBackground(Float... yposes) {
	    	
	    	for (Float ypos : yposes) {
	    		while(60L > counter) {
	    			ypos += 0.1f;
	    			//Log.i("OpenGLRenderer.class", String.format("%f", ypos));
	    			counter++;
	    			publishProgress(counter);

		    	}
	        }
	    	return counter;
	    }

	    protected void onProgressUpdate(Long... progress) {
	    	
	    	Log.i("OpenGLRenderer.class", String.format("%d", progress[0]));
	    	//Log.i("OpenGLRenderer.class", String.format("%f", ypos));
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

