package depauw.edu.myro.android.sandbox;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import depauw.edu.myro.andriod.Scribbler;
import depauw.edu.myro.android.hierarchy.CameraActivity;
import depauw.edu.myro.android.hierarchy.GraphicActivity;
import depauw.edu.myro.android.hierarchy.MovementActivity;
import depauw.edu.myro.android.hierarchy.MusicActivity;
import depauw.edu.myro.android.hierarchy.VoiceActivity;

public class MainActivity extends Activity {
	
	// Debugging
	  private static final String TAG = "MainTabWidget";
	  private static final boolean D = false;

	  private Resources res;
	  private Sandbox appState;

	  // Intent request codes
	  private static final int REQUEST_CONNECT_DEVICE = 1;

	private String[] firstList = {"Camera -->", "Movement -->", "Information -->", "2D graphics -->", "Music -->", "Voice -->"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		res = getResources();
		// Set dummy scribbler. will be replaced once user connects
		appState = (Sandbox) getApplicationContext();
		appState.setScribbler(new Scribbler());
		
		/*
		 * list instantiate
		 */
		final ListView listview = (ListView) findViewById(R.id.listView1);
		final ArrayList<String> list = new ArrayList<String>();
	    
		for (int i = 0; i < firstList.length; ++i)
		{
	      list.add(firstList[i]);
	    }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				// Camera
				if(position == 0)
				{
					Intent cameraIntent = new Intent(MainActivity.this, CameraActivity.class);
					startActivity(cameraIntent);
				}
				// Movement
				else if(position == 1)
				{
					Intent movementIntent = new Intent(MainActivity.this, MovementActivity.class);
					startActivity(movementIntent);
				}
				// Information
				else if(position == 2)
				{
					Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
					startActivity(infoIntent);
				}
				// 2D graphics
				else if(position == 3)
				{
					Intent graphicIntent = new Intent(MainActivity.this, GraphicActivity.class);
					startActivity(graphicIntent);
				}
				// Music
				else if(position == 4)
				{
					Intent musicIntent = new Intent(MainActivity.this, MusicActivity.class);
					startActivity(musicIntent);
				}
				// Voice
				else if(position == 5)
				{
					Intent voiceIntent = new Intent(MainActivity.this, VoiceActivity.class);
					startActivity(voiceIntent);
				}
			}
		});
		/*
		 * end of list instantiate
		 */
	}

		@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
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
	        if (D) Log.d(TAG, "User Requested" + address);

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

	            if (D) Log.i(TAG, "Scribbler Persisted");

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

	  public static void emphasizeConnectivity() {
	    ScaleAnimation s = new ScaleAnimation(0.75f, 1, 0.75f, 1, Animation.RELATIVE_TO_SELF,
	        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	    s.setDuration(500);
	  }
	  
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