package depauw.edu.myro.android.hierarchy;

import java.util.ArrayList;

import depauw.edu.myro.android.sandbox.Graphic_Canvas;
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

public class GraphicActivity extends Activity {
	
	private String[] graphicList = {"Make a Canvas -->"};
	private Sandbox appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graphic_activity_main);
		
		appState = (Sandbox) getApplicationContext();
	    appState.getScribbler();
		
		/*
		 * list instantiate
		 */
		ListView graphiclist = (ListView) findViewById (android.R.id.list);
		ArrayList<String> list = new ArrayList<String>();
	    
		for (int i = 0; i < graphicList.length; ++i)
		{
			list.add(graphicList[i]);
	    }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
    
		graphiclist.setAdapter(adapter);
		
		graphiclist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				// Make a Canvas
				if(position == 0)
				{
					Intent canvasIntent = new Intent(GraphicActivity.this, Graphic_Canvas.class);
					startActivity(canvasIntent);
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
