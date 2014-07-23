package depauw.edu.myro.android.sandbox;

import depauw.edu.myro.original.MyroUtils;
import depauw.edu.myro.andriod.Scribbler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Movement_Simple_Controller extends Activity {
	
		
	private Sandbox appState;
	  @SuppressWarnings({ "unused" })
	private TextView intro, forward, backward, turnRight, turnLeft;
	  /**
	   * The button committing the execution
	   */
	  private Button forwardGo, backwardGo, turnRightGo, turnLeftGo, stop;
	  /**
	   * The editTexts with the values for both the speed and time
	   */
	  private EditText forwardSpeed, backwardSpeed, turnRightSpeed, turnLeftSpeed;
	  private EditText forwardTime, backwardTime, turnRightTime,turnLeftTime;
	  private Resources res;
	  
	  // Intent request codes
	  private static final int REQUEST_CONNECT_DEVICE = 1;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.movement_simple_controller);

	    appState = (Sandbox) getApplicationContext();
	    res = getResources();
	    
	    // Instances of TextViews
	    intro = (TextView) findViewById(R.id.intro);
	    forward = (TextView) findViewById(R.id.forwardText);
	    backward = (TextView) findViewById(R.id.backwardText);
	    turnRight = (TextView) findViewById(R.id.turnRightText);
	    turnLeft = (TextView) findViewById(R.id.turnLeftText);
	    
	    // Instances of Buttons
	    forwardGo = (Button) findViewById(R.id.forwardGo);
	    backwardGo = (Button) findViewById(R.id.backwardGo);
	    turnRightGo = (Button) findViewById(R.id.turnRightGo);
	    turnLeftGo = (Button) findViewById(R.id.turnLeftGo);
	    stop = (Button) findViewById(R.id.stop);
	    
	    // Instances of EditTexts
	    forwardSpeed = (EditText) findViewById(R.id.forwardDistance);
	    backwardSpeed = (EditText) findViewById(R.id.backwardDistance);
	    turnRightSpeed = (EditText) findViewById(R.id.turnRightDistance);
	    turnLeftSpeed = (EditText) findViewById(R.id.turnLeftDistance);
	    
	    forwardTime = (EditText) findViewById(R.id.forwardTime);
	    backwardTime = (EditText) findViewById(R.id.backwardTime);
	    turnRightTime = (EditText) findViewById(R.id.turnRightTime);
	    turnLeftTime = (EditText) findViewById(R.id.turnLeftTime);
	    
	    
	    
	    //OnClick Listeners
	    forwardGo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String value1 = forwardSpeed.getText().toString();
				Double speed = Double.valueOf(value1);
				assert -1.0 <= speed && speed <= 1.0 : "speed not between -1.0 and 1.0";
				appState.getScribbler().forward(speed);
				
				String value2 = forwardTime.getText().toString();
				double numSeconds = Double.valueOf(value2);
				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
				MyroUtils.sleep(numSeconds);
				appState.getScribbler().stop();
			}
	    });
	   
	    backwardGo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String value1 = backwardSpeed.getText().toString();
				Double speed = Double.valueOf(value1);
				assert -1.0 <= speed && speed <= 1.0 : "speed not between -1.0 and 1.0";
				appState.getScribbler().backward(speed);
				
				String value2 = backwardTime.getText().toString();
				double numSeconds = Double.valueOf(value2);
				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
				MyroUtils.sleep(numSeconds);
				appState.getScribbler().stop();
			}	
	    });
	    
	    turnRightGo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String value1 = turnRightSpeed.getText().toString();
				Double speed = Double.valueOf(value1);
				assert -1.0 <= speed && speed <= 1.0 : "speed not between -1.0 and 1.0";
				appState.getScribbler().turnRight(speed);
				
				String value2 = turnRightTime.getText().toString();
				double numSeconds = Double.valueOf(value2);
				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
				MyroUtils.sleep(numSeconds);
				appState.getScribbler().stop();	
			}    	
	    });
	    
	    turnLeftGo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				String value1 = turnLeftSpeed.getText().toString();
				Double speed = Double.valueOf(value1);
				assert -1.0 <= speed && speed <= 1.0 : "speed not between -1.0 and 1.0";
				appState.getScribbler().turnLeft(speed);
				
				String value2 = turnLeftTime.getText().toString();
				double numSeconds = Double.valueOf(value2);
				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
				MyroUtils.sleep(numSeconds);
				appState.getScribbler().stop();	
			}	    	
	    });
	    
	    stop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				appState.getScribbler().stop();			
			}    	
	    });
	          
 
    }	  
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		@Override
		  public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case R.id.connect:
		      // Launch the DeviceListActivity to see devices and do scan
		      Intent serverIntent = new Intent(this, DeviceListActivity.class);
		      startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		      return true;
		    case R.id.disconnect:
		      final Scribbler s = appState.getScribbler();
		      if (s != null && s.isConnected()) {
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("Are you sure you want to disconnect?").setCancelable(false)
		            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		              @Override
		              public void onClick(DialogInterface dialog, int id) {
		                s.disconnect();
		                Toast.makeText(getApplicationContext(),
		                    res.getString(R.string.success_disconnect), Toast.LENGTH_SHORT)
		                    .show();

		              }
		            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
		              @Override
		              public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		              }
		            });
		        AlertDialog alert = builder.create();
		        alert.show();
		      } else {
		        Toast.makeText(getApplicationContext(),
		            res.getString(R.string.are_not_connected), Toast.LENGTH_SHORT).show();
		      }
		      return true;
		    default:
		      return super.onOptionsItemSelected(item);
		    }
		  }

		  @Override
		  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		    switch (requestCode) {
		    case REQUEST_CONNECT_DEVICE:
		      // When DeviceListActivity returns with a device to connect
		      if (resultCode == Activity.RESULT_OK) {
		        // Get the selected device's MAC address
		        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		       
		        // Create New Scribbler
		        Scribbler newScribbler = new Scribbler(address);

		        // Connect New Scribbler
		        try {
		          if (newScribbler.connect()) {
		            // Persist New Scribbler
		            appState.setScribbler(newScribbler);

		            Toast.makeText(getApplicationContext(),
		                "Successful Connecting to " + address, Toast.LENGTH_SHORT).show();

		            // Successful Connection beeps
		            Scribbler s = appState.getScribbler();
		            s.beep(784, .03f);
		            s.beep(880, .03f);
		            s.beep(698, .03f);
		            s.beep(349, .03f);
		            s.beep(523, .03f);

		          } else {
		            Toast.makeText(getApplicationContext(), "Error Connecting to " + address,
		                Toast.LENGTH_SHORT).show();

		          }
		        } catch (Exception e) {
		        	Toast.makeText(getApplicationContext(), "Something went wrong \n" + e, Toast.LENGTH_SHORT).show();
		        }
		      }
		      break;
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
