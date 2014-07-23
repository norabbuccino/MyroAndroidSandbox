package depauw.edu.myro.android.sandbox;

//Concept taken from http://androidbite.blogspot.com/2013/04/android-voice-recognition-example.html

import java.util.ArrayList;
import java.util.List;

import depauw.edu.myro.original.MyroUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Voice_Simple extends Activity {

	private static final int REQUEST_CODE = 1234;
	private ListView resultList;
	Button speakButton;
	
	private Sandbox appState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_simple_voice);
		
		appState = (Sandbox) getApplicationContext();

		speakButton = (Button) findViewById(R.id.speakButton);
		resultList = (ListView) findViewById(R.id.list);

		// Disable button if no recognition service is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
  
		if (activities.size() == 0) {
			
			speakButton.setEnabled(false);
			Toast.makeText(getApplicationContext(), "Recognizer Not Found", Toast.LENGTH_LONG).show();
		}
  
		speakButton.setOnClickListener(new OnClickListener() {
   
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});
 }


	/**
	 * Starts the intent which communicates with the Google servers
	 * to decipher the audio file just recorded. 
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "AndroidBite Voice Recognition...");
		startActivityForResult(intent, REQUEST_CODE);	
	}

	/**
	 * Once the startVoiceRecognitionActivity() has finished, this function
	 * takes all of the Strings (presented in an ArrayList) and then 
	 * figures out if the user said forward, backward, turn left, turn right,
	 * or beep. If one of those conditions are met, then the Scribbler performs 
	 * the action. If not, there is a Toast print with an error telling the user
	 * what was just said was unknown.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			resultList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, matches));
			
			for(int i = 0; i < matches.size(); i++){
				
				// FORWARD
				if(matches.get(i).trim().equalsIgnoreCase("forward")){
					
					appState.getScribbler().forward(1.0);
					MyroUtils.sleep(3.0);
					appState.getScribbler().stop();
				
				}
				else if(matches.get(i).trim().equalsIgnoreCase("backward")){
					
					appState.getScribbler().backward(1.0);
					MyroUtils.sleep(3.0);
					appState.getScribbler().stop();
				}
				else if(matches.get(i).trim().equalsIgnoreCase("turn Left")){
					
					appState.getScribbler().turnLeft(1.0);
					MyroUtils.sleep(3.0);
					appState.getScribbler().stop();
				}
				else if(matches.get(i).trim().equalsIgnoreCase("turn Right")){
					
					appState.getScribbler().turnRight(1.0);
					MyroUtils.sleep(3.0);
					appState.getScribbler().stop();
				}
				else if(matches.get(i).trim().equalsIgnoreCase("Beep")){
					
					appState.getScribbler().beep(800, 4);
				}				
			}
		}
		
	super.onActivityResult(requestCode, resultCode, data);
	
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