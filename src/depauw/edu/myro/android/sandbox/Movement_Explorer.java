package depauw.edu.myro.android.sandbox;

import depauw.edu.myro.andriod.Scribbler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Movement_Explorer extends Activity {
	
	  private Sandbox appState;
	  private Button cameraBoolean;
	  private ImageButton up, down, left, right;
	  private Resources res;
	  private ImageView scribblerPictures;
	  private boolean takePictures = false;
	  Handler mHandler;
	  
	  // Intent request codes
	  private static final int REQUEST_CONNECT_DEVICE = 1;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.movement_explorer);

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
	    
	    //Scribbler image views
	    scribblerPictures = (ImageView) findViewById(R.id.scribblerImages);
	    cameraBoolean = (Button) findViewById(R.id.cameraBoolean);
	    
	    cameraBoolean.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				if(takePictures == true){
					//how to cancel it all
					cameraBoolean.setText("Enable Camera");
					mHandler.removeCallbacks(mRunnable);
					takePictures = false;
				}
				else{
					//start taking pictures repeatedly 
					cameraBoolean.setText("Disable Camera");
					useHandler();
					takePictures = true;
				}
				
			}
	    });
    }

	  //Used in taking pictures
	  public void useHandler() {
	    mHandler = new Handler();
	    mHandler.postDelayed(mRunnable, 1000);
	  }

	  private Runnable mRunnable = new Runnable() {

	    @Override
	    public void run() {
	      scribblerPictures.setImageBitmap(appState.getScribbler().takePicture());
	      mHandler.postDelayed(mRunnable, 1000);
	    }
	  };
		
		
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
