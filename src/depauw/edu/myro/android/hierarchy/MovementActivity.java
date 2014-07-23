package depauw.edu.myro.android.hierarchy;

import java.util.ArrayList;

import depauw.edu.myro.android.sandbox.Movement_Accelerometer;
import depauw.edu.myro.android.sandbox.Movement_Controller;
import depauw.edu.myro.android.sandbox.Movement_Explorer;
import depauw.edu.myro.android.sandbox.Movement_Simple_Controller;
import depauw.edu.myro.android.sandbox.R;
import depauw.edu.myro.android.sandbox.Sandbox;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class acts as a middle-man navigation tool. If the user wants to use the movement of a 
 * Scribbler, then they should have multiple options available.
 * 
 * @author Alexander Miller (DePauw University, Class of 2016), alexander.miller110@gmail.com
 * @version Summer 2014
 *
 */
public class MovementActivity extends Activity {
	
	private String[] movementList = {"Accelerometer -->", "Controller -->", "Explorer -->", "Simple Controller -->", "Java Docs -->"};
	private Sandbox appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movement_activity_main);
		
		appState = (Sandbox) getApplicationContext();
	    appState.getScribbler();
		
		/*
		 * list instantiate
		 */
		ListView graphiclist = (ListView) findViewById (R.id.movementList);
		ArrayList<String> list = new ArrayList<String>();
	    
		for (int i = 0; i < movementList.length; ++i)
		{
			list.add(movementList[i]);
	    }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    
		graphiclist.setAdapter(adapter);
		
		graphiclist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				// Accelerometer
				if(position == 0)
				{
					Intent accelerometerIntent = new Intent(MovementActivity.this, Movement_Accelerometer.class);
					startActivity(accelerometerIntent);
				}
				// Controller
				else if(position == 1)
				{
					Intent controllerIntent = new Intent(MovementActivity.this, Movement_Controller.class);
					startActivity(controllerIntent);
				}
				// Explorer
				else if(position == 2)
				{
					Intent explorerIntent = new Intent(MovementActivity.this, Movement_Explorer.class);
					startActivity(explorerIntent);
				}
				// Simple Controller
				else if(position == 3)
				{
					Intent simpleControllerIntent = new Intent(MovementActivity.this, Movement_Simple_Controller.class);
					startActivity(simpleControllerIntent);
				}
				// Java Docs
				else if(position == 4)
				{
				}
			}
		});
		/*
		 * end of list instantiate
		 */
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