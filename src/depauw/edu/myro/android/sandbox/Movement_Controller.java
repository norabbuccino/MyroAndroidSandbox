package depauw.edu.myro.android.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Movement_Controller extends Activity {
	
	  private Sandbox appState;
	  private ImageButton up, down, left, right;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.movement_controller);

	    appState = (Sandbox) getApplicationContext();
	    
	    up = (ImageButton) findViewById(R.id.control_up);
	    up.setOnTouchListener(new View.OnTouchListener() {        

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(appState.getScribbler().isConnected() == true){
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//PRESED
						appState.getScribbler().forward(1);
						return true;
					}
					else if(event.getAction() == MotionEvent.ACTION_UP){
						//RELEASED
						appState.getScribbler().move(0, 0);
						return true;
					}
				}
				return false;
			}
	    	});
		
	    down = (ImageButton) findViewById(R.id.control_down);
		down.setOnTouchListener(new View.OnTouchListener() {        

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(appState.getScribbler().isConnected() == true){
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//PRESED
						appState.getScribbler().backward(1);;
						return true;
					}
					else if(event.getAction() == MotionEvent.ACTION_UP){
						//RELEASED
						appState.getScribbler().move(0, 0);
						return true;
					}
				}
	            return false;
				}
	    	});
		
		left = (ImageButton) findViewById(R.id.control_left);
		left.setOnTouchListener(new View.OnTouchListener() {        

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(appState.getScribbler().isConnected() == true){
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//PRESED
						appState.getScribbler().turnLeft(1);;
						return true;
					}
					else if(event.getAction() == MotionEvent.ACTION_UP){
						//RELEASED
						appState.getScribbler().move(0, 0);
						return true;
					}
				}
	            return false;
				}
	    	});
		
		right = (ImageButton) findViewById(R.id.control_right);
		right.setOnTouchListener(new View.OnTouchListener() {        

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(appState.getScribbler().isConnected() == true){
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						//PRESED
						appState.getScribbler().turnRight(1);;
						return true;
					}
					else if(event.getAction() == MotionEvent.ACTION_UP){
						//RELEASED
						appState.getScribbler().move(0, 0);
						return true;
					}
				}
	            return false;
				}
	    	});
	  }
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
	  
	  /**
		 * Used to help maintain clean activity across the app. If someone is connected 
		 * to the Scribbler, they should disconnect before leaving the app. This function
		 * ensures that happens.
		 */
	  @Override
	    public boolean dispatchKeyEvent(KeyEvent event)
	    {
			  if (event.getAction() == KeyEvent.ACTION_DOWN) {
				  switch (event.getKeyCode()) {
				  case KeyEvent.KEYCODE_HOME: 
					  if(appState.getScribbler().isConnected()){
						  appState.getScribbler().disconnect();
						  Toast.makeText(getApplicationContext(), "Disconnected from Scribbler", Toast.LENGTH_LONG).show();
					  }
	      	  return true;
				  }
			  }
			  return super.dispatchKeyEvent(event);  // let the default handling take care of it
	    }
}