package depauw.edu.myro.android.sandbox;

import depauw.edu.myro.andriod.Scribbler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Toast;

/**
 * Builds a piano that will play notes using the scribbler robot. Scribbler must
 * be connected for this to work
 * 
 * @author Leonora Bresette Buccino and Alexander Miller
 * @version Summer 2014
 */
public class Music_Piano extends Activity {

	/**
	 * Button corresponding to the A key on a piano
	 */
	Button aButton;
	/**
	 * Button corresponding to the B key on a piano
	 */
	Button bButton;
	/**
	 * Button corresponding to the C key on a piano
	 */
	Button cButton;
	/**
	 * Button corresponding to the D key on a piano
	 */
	Button dButton;
	/**
	 * Button corresponding to the E key on a piano
	 */
	Button eButton;
	/**
	 * Button corresponding to the F key on a piano
	 */
	Button fButton;
	/**
	 * Button corresponding to the G key on a piano
	 */
	Button gButton;
	/**
	 * Button corresponding to the A# key on a piano
	 */
	Button aSharpButton;
	/**
	 * Button corresponding to the C# key on a piano
	 */
	Button cSharpButton;
	/**
	 * Button corresponding to the D# key on a piano
	 */
	Button dSharpButton;
	/**
	 * Button corresponding to the F# key on a piano
	 */
	Button fSharpButton;
	/**
	 * Button corresponding to the G# key on a piano
	 */
	Button gSharpButton;

	/**
	 * Button to select the first octave
	 */
	Button octave1Button;
	/**
	 * Button to select the second octave
	 */
	Button octave2Button;
	/**
	 * Button to select the third octave
	 */
	Button octave3Button;
	/**
	 * Button to select the fourth octave
	 */
	Button octave4Button;
	/**
	 * Button to select the fifth octave
	 */
	Button octave5Button;
	/**
	 * Button to select the sixth octave
	 */
	Button octave6Button;
	/**
	 * Button to select the seventh octave
	 */
	Button octave7Button;
	/**
	 * Button to select the eighth octave
	 */
	Button octave8Button;

	/**
	 * The current octave selected
	 */
	int octave;

	/**
	 * The state of the application
	 */
	Sandbox appState;
	
	// Debugging
	  private static final String TAG = "MainTabWidget";
	  private static final boolean D = false;

	  private Resources res;

	  // Intent request codes
	  private static final int REQUEST_CONNECT_DEVICE = 1;

	/**
	 * Creates the layout and initializes all of the buttons in the layout and
	 * their on click listeners
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piano_layout);
		aButton = (Button) findViewById(R.id.akey);
		bButton = (Button) findViewById(R.id.bkey);
		cButton = (Button) findViewById(R.id.ckey);
		dButton = (Button) findViewById(R.id.dkey);
		eButton = (Button) findViewById(R.id.ekey);
		fButton = (Button) findViewById(R.id.fkey);
		gButton = (Button) findViewById(R.id.gkey);
		aSharpButton = (Button) findViewById(R.id.asharpkey);
		cSharpButton = (Button) findViewById(R.id.csharpkey);
		dSharpButton = (Button) findViewById(R.id.dsharpkey);
		fSharpButton = (Button) findViewById(R.id.fsharpkey);
		gSharpButton = (Button) findViewById(R.id.gsharpkey);

		octave1Button = (Button) findViewById(R.id.octave1_btn);
		octave2Button = (Button) findViewById(R.id.octave2_btn);
		octave3Button = (Button) findViewById(R.id.octave3_btn);
		octave4Button = (Button) findViewById(R.id.octave4_btn);
		octave5Button = (Button) findViewById(R.id.octave5_btn);
		octave6Button = (Button) findViewById(R.id.octave6_btn);
		octave7Button = (Button) findViewById(R.id.octave7_btn);
		octave8Button = (Button) findViewById(R.id.octave8_btn);

		final Sandbox appstate = (Sandbox) getApplicationContext();

		octave1Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave1_btn) {
					octave = 1;
				}
			}
		});

		octave2Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave2_btn) {
					octave = 2;
				}
			}
		});

		octave3Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave3_btn) {
					octave = 3;
				}
			}
		});

		octave4Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave4_btn) {
					octave = 4;
				}
			}
		});

		octave5Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave5_btn) {
					octave = 5;
				}
			}
		});

		octave6Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave6_btn) {
					octave = 6;
				}
			}
		});

		octave7Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave7_btn) {
					octave = 7;
				}
			}
		});

		octave8Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.octave8_btn) {
					octave = 8;
				}
			}
		});

		aButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.akey) {

					if (octave == 1) {
						appstate.getScribbler().beep(27, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(55, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(110, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(220, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(440, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(880, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1760, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(3520, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		aSharpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.asharpkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(29, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(58, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(116, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(233, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(466, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(932, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1864, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(3729, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		bButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.bkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(30, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(61, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(123, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(246, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(493, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(987, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1975, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(3951, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		cButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.ckey) {

					if (octave == 1) {
						appstate.getScribbler().beep(16, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(32, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(65, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(130, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(261, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(523, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1046, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2093, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		cSharpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.csharpkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(17, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(34, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(69, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(138, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(277, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(554, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1108, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2217, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		dButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.dkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(18, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(36, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(73, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(146, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(293, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(587, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1174, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2349, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		dSharpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.dsharpkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(19, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(38, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(77, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(155, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(311, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(622, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1244, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2489, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		eButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.ekey) {

					if (octave == 1) {
						appstate.getScribbler().beep(20, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(41, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(82, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(164, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(329, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(659, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1318, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2637, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		fButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.fkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(21, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(43, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(87, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(174, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(349, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(698, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1396, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2793, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		fSharpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.fsharpkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(23, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(46, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(92, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(185, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(370, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(740, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1480, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(2960, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		gButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.gkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(24, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(49, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(98, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(195, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(392, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(784, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1568, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(3136, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

		gSharpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.gsharpkey) {

					if (octave == 1) {
						appstate.getScribbler().beep(25, .5f);
					} else if (octave == 2) {
						appstate.getScribbler().beep(51, .5f);
					} else if (octave == 3) {
						appstate.getScribbler().beep(103, .5f);
					} else if (octave == 4) {
						appstate.getScribbler().beep(207, .5f);
					} else if (octave == 5) {
						appstate.getScribbler().beep(415, .5f);
					} else if (octave == 6) {
						appstate.getScribbler().beep(830, .5f);
					} else if (octave == 7) {
						appstate.getScribbler().beep(1661, .5f);
					} else if (octave == 8) {
						appstate.getScribbler().beep(3322, .5f);
					} else
						Toast.makeText(getApplicationContext(),
								"Invalid Octave", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

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
