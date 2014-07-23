package depauw.edu.myro.android.hierarchy;

import java.util.ArrayList;

import depauw.edu.myro.android.sandbox.Camera_Simple;
import depauw.edu.myro.android.sandbox.R;
import depauw.edu.myro.android.sandbox.Sandbox;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class acts as a middle-man navigation tool. If the user wants to use the camera of a 
 * Scribbler (or possibly the Android device), then they should have multiple options available.
 * 
 * @author Alexander Miller (DePauw University, Class of 2016), alexander.miller110@gmail.com
 * @version Summer 2014
 *
 */
public class CameraActivity extends Activity{
	
	private String[] cameraList = {"QR (Not working yet) -->", "Simple Camera -->","Java Docs -->"};
	private Sandbox appState;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.camera_activity_main);
	    
	    appState = (Sandbox) getApplicationContext();
	    appState.getScribbler();
	    
	    /*
		 * list instantiate
		 */
		ListView Cameralist = (ListView) findViewById (R.id.cameraList);
		ArrayList<String> list = new ArrayList<String>();
	    
		for (int i = 0; i < cameraList.length; ++i)
		{
			list.add(cameraList[i]);
	    }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    
		Cameralist.setAdapter(adapter);
		
		Cameralist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				// QR
				if(position == 0)
				{
					//Intent qrIntent = new Intent(CameraActivity.this, Camera_QR.class);
					//startActivity(qrIntent);
				}
				// Simple Camera
				else if(position == 1)
				{
					Intent simpleCameraIntent = new Intent(CameraActivity.this, Camera_Simple.class);
					startActivity(simpleCameraIntent);
				}
				// Java doc
				else if(position == 2)
				{
					//Intent movementIntent = new Intent(MainActivity.this, MovementActivity.class);
					//startActivity(movementIntent);
				}
			}
		});
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