package depauw.edu.myro.android.hierarchy;

import java.util.ArrayList;

import depauw.edu.myro.android.sandbox.Movement_Accelerometer;
import depauw.edu.myro.android.sandbox.Movement_Controller;
import depauw.edu.myro.android.sandbox.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MovementActivity extends Activity {
	
	private String[] movementList = {"Accelerometer -->", "Controller -->", "Java Docs -->"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movement_activity_main);
		
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
				// Java Docs
				else if(position == 2)
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
}
