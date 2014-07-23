package depauw.edu.myro.android.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Allows the user to play a tone of a specified frequency and duration using
 * the scribbler robot. There must be a scribbler robot connected for this to
 * work
 * 
 * @author Leonora Bresette Buccino and Alexander Miller
 * @version Summer 2014
 */
public class Music_Beep extends Activity {

	/**
	 * Button to play a tone
	 */
	Button playButton;

	/**
	 * The state of the application
	 */
	private Sandbox appState;

	/**
	 * When the activity is created we build the layout and set the on click
	 * listeners for the play button
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beep_player);
		playButton = (Button) findViewById(R.id.play_button);

		appState = (Sandbox) getApplicationContext();
		final EditText frequency = (EditText) findViewById(R.id.frequency_text);
		final EditText duration = (EditText) findViewById(R.id.duration_text);

		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.play_button) {
					appState.getScribbler().beep(
							Integer.parseInt(frequency.getText().toString()),
							Integer.parseInt(duration.getText().toString()));
				}
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Used to help maintain clean activity across the app. If someone is
	 * connected to the Scribbler, they should disconnect before leaving the
	 * app. This function ensures that happens.
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_HOME:
				if (appState.getScribbler().isConnected()) {
					appState.getScribbler().disconnect();
					Toast.makeText(getApplicationContext(),
							"Disconnected from Scribbler", Toast.LENGTH_LONG)
							.show();
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event); // let the default handling take
												// care of it
	}
}
