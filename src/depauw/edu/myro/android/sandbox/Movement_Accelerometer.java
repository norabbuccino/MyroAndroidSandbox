package depauw.edu.myro.android.sandbox;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This class enables the use of the Scribbler's movement by utilizing An Android's Accelerometer.
 * The Accelerometer {@link http://developer.android.com/guide/topics/sensors/sensors_overview.html}
 * 
 * 
 * @author Alexander Miller (DePauw University, Class of 2016), alexander.miller110@gmail.com
 * @version Summer 2014
 *
 */
public class Movement_Accelerometer extends Activity implements SensorEventListener {
	
	private SensorManager sensorManager;	
	private Sandbox appState;
	private double threshold = 2.0;
	private double negThreshold = -2.0;	
	private float x, y;
	
	private ImageView up, down, left, right;
	private Button bool;
	private boolean allEnabled = false;
	private boolean upEnabled = false;
	private boolean downEnabled = false; 
	private boolean leftEnabled = false; 
	private boolean rightEnabled = false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movement_accelerometer_main);
		
		appState = (Sandbox) getApplicationContext();
		
		sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),	SensorManager.SENSOR_DELAY_NORMAL);
		
		/*	More sensor speeds (taken from api docs)
		    SENSOR_DELAY_FASTEST get sensor data as fast as possible  <-- overloads the program
		    SENSOR_DELAY_GAME	rate suitable for games               <--- not quick enough to respond to user input
		 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes
		*/
		
		up = (ImageView) findViewById(R.id.accel_up);
		down = (ImageView) findViewById(R.id.accel_down);
		left = (ImageView) findViewById(R.id.accel_left);
		right = (ImageView) findViewById(R.id.accel_right);
		bool = (Button) findViewById(R.id.bool_button);
		bool.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				if(bool.getText().equals("Enable")){
					bool.setText("Disable");
					allEnabled = true;
				}
				else{
					bool.setText("Enable");
					allEnabled = false;
				}
			}
		});
		
	}

	public void onAccuracyChanged(Sensor sensor,int accuracy){
		
	}
	
	/**
	 * onSensorChanged() checks to see if the scribbler is connected
	 * and if the Accelerometer is available. If so, then the values
	 * taken from the Accelerometer are taken and evaluated to perform
	 * Scribbler movements based on which direction the Android device
	 * is pointing towards.
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	public void onSensorChanged(SensorEvent event){		
		
		// check sensor type
		if(allEnabled == true && event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			
			
			// assign directions
			x = event.values[0];
			y = event.values[1];
			
			// STOP: within 2 or -2 of any direction while its moving
			if(appState.getScribbler().isMoving() &&
					appState.getScribbler().isConnected() && 
					x > negThreshold &&
					x < threshold){
				
					appState.getScribbler().move(0, 0);
					appState.getScribbler().setMoving(false);
					
					if(upEnabled == true){
						
						up.setImageResource(R.drawable.top_arrow);
						upEnabled = false;
					}
					else if(downEnabled == true){
						
						down.setImageResource(R.drawable.bottom_arrow);
						downEnabled = false;
					}
					else if(rightEnabled == true){
						
						right.setImageResource(R.drawable.right_arrow);
						rightEnabled = false;
					}
					else if(leftEnabled == true){
						
						left.setImageResource(R.drawable.left_arrow);
						leftEnabled = false;
					}				
			}
			
			// Move the Scribbler BACKWARDS
	        if (appState.getScribbler().isConnected() && 
	        		x < negThreshold &&
	        		 !(x > negThreshold)) {
	          
	          // describing the speed based on what x is 
	          if(x < -2.0 && x > -2.5){
	        	  appState.getScribbler().backward(0.2);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -2.6 && x > -3.0){
	        	  appState.getScribbler().backward(0.3);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -3.1 && x > -3.5){
	        	  appState.getScribbler().backward(0.4);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -3.6 && x > -4.0){
	        	  appState.getScribbler().backward(0.5);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -4.1 && x > -4.5){
	        	  appState.getScribbler().backward(0.6);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -4.6 && x > -5.0){
	        	  appState.getScribbler().backward(0.7);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -5.1 && x > -5.5){
	        	  appState.getScribbler().backward(0.8);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x < -5.6 && x > -6.0){
	        	  appState.getScribbler().backward(0.9);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	          else if(x > -6.1){
	        	  appState.getScribbler().backward(1.0);
	        	  appState.getScribbler().setMoving(true);
	        	  down.setImageResource(R.drawable.bottom_arrow_selected);
	        	  downEnabled = true;
	          }
	         }
		          // Move the Scribbler FORWARDS
	         else if (appState.getScribbler().isConnected() &&
	        		 x > threshold &&
	        		 !(x < negThreshold)) {
	       	  
	       	  // describing the speed based on what x is 
	       	  
	       	  if(x > 2.0 && x < 2.5){
	       		  appState.getScribbler().forward(0.2);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	        	  upEnabled = true;
	       	  }
	       	  else if(x > 2.6 && x < 3.0){
	       		  appState.getScribbler().forward(0.3);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 3.1 && x < 3.5){
	       		  appState.getScribbler().forward(0.4);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 3.6 && x < 4.0){
	       		  appState.getScribbler().forward(0.5);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 4.1 && x < 4.5){
	       		  appState.getScribbler().forward(0.6);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 4.6 && x < 5.0){
	       		  appState.getScribbler().forward(0.7);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 5.1 && x < 5.5){
	       		  appState.getScribbler().forward(0.8);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > 5.6 && x < 6.0){
	       		  appState.getScribbler().forward(0.9);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	       	  else if(x > -6.1){
	       		  appState.getScribbler().forward(1.0);
	       		  appState.getScribbler().setMoving(true);
	       		  up.setImageResource(R.drawable.up_arrow_selected);
	       		  upEnabled = true;
	       	  }
	         }
	        
	        // Move the Scribbler RIGHT
	        if (appState.getScribbler().isConnected() && 
	        		y < negThreshold &&
	        		 !(y > negThreshold)) {
	          
	          // describing the speed based on what x is 
	          if(y > -2.5){
	        	  appState.getScribbler().turnRight(0.2);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -2.6 && y > -3.0){
	        	  appState.getScribbler().turnRight(0.3);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -3.1 && y > -3.5){
	        	  appState.getScribbler().turnRight(0.4);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -3.6 && y > -4.0){
	        	  appState.getScribbler().turnRight(0.5);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -4.1 && y > -4.5){
	        	  appState.getScribbler().turnRight(0.6);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -4.6 && x > -5.0){
	        	  appState.getScribbler().turnRight(0.7);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -5.1 && y > -5.5){
	        	  appState.getScribbler().turnRight(0.8);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y < -5.6 && y > -6.0){
	        	  appState.getScribbler().turnRight(0.9);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	          else if(y > -6.1){
	        	  appState.getScribbler().turnRight(1.0);
	        	  appState.getScribbler().setMoving(true);
	        	  right.setImageResource(R.drawable.right_arrow_selected);
	        	  rightEnabled = true;
	          }
	         }
		          // Move the Scribbler LEFT
	         else if (appState.getScribbler().isConnected() &&
	        		 y > threshold &&
	        		 !(y < negThreshold)) {
	       	  
	       	  // describing the speed based on what x is 
	       	  
	       	  if(y > 2.0 && y < 2.5){
	       		  appState.getScribbler().turnLeft( 0.2);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 2.6 && y < 3.0){
	       		  appState.getScribbler().turnLeft( 0.3);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 3.1 && y < 3.5){
	       		  appState.getScribbler().turnLeft( 0.4);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 3.6 && y < 4.0){
	       		  appState.getScribbler().turnLeft( 0.5);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 4.1 && y < 4.5){
	       		  appState.getScribbler().turnLeft(0.6);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 4.6 && y < 5.0){
	       		  appState.getScribbler().turnLeft(0.7);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 5.1 && y < 5.5){
	       		  appState.getScribbler().turnLeft(0.8);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 5.6 && y < 6.0){
	       		  appState.getScribbler().turnLeft(0.9);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	       	  else if(y > 6.1){
	       		  appState.getScribbler().turnLeft(1.0);
	       		  appState.getScribbler().setMoving(true);
	       		  left.setImageResource(R.drawable.left_arrow_selected);
	        	  leftEnabled = true;
	       	  }
	         }
		}
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