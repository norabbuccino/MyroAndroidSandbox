package depauw.edu.myro.android.sandbox;

import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * This class enables the use of the Scribbler's information to be sent to an Android device
 * in the means of numbers; especially battery level being represented in the form of both 
 * an integer value and a picture. This picture is one of a battery which changes "charge levels"
 * based on the integer level received.
 * 
 * @author Alexander Miller (DePauw University, Class of 2016), alexander.miller110@gmail.com
 * @version Summer 2014
 *
 */
public class InfoActivity extends Activity {
  // Debugging
  private static final String TAG = "RobotInfoActivity";
  private static final boolean D = false;

  private Sandbox appState;

  private TextView name, battery;
  private TextView obstacleLeft, obstacleCenter, obstacleRight;
  private TextView lightLeft, lightCenter, lightRight;
  private TextView irLeft, irRight;
  private TextView lineLeft, lineRight;
  private float batteryValue;
  private int[] obstacleValues, lightValues, irValues, lineValues;
  private String nameValue;

  private SharedPreferences settings;
  private Resources res;
  private Handler handler;

  private ToggleButton toggleButton;
  private Button manualButton;
  private Runnable r;

  private ImageView battery_level;
  
  // Must be instance variable to avoid garbage collection!
  private OnSharedPreferenceChangeListener preferenceListener;  
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.info_activity_main);

    appState = (Sandbox) getApplicationContext();
    settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    res = getResources();
    handler = new Handler();

    // Initialize the textviews that will be updated when requested
    name = (TextView) findViewById(R.id.name_value);
    battery = (TextView) findViewById(R.id.battery_value);
    obstacleLeft = (TextView) findViewById(R.id.obstacle_left_value);
    obstacleCenter = (TextView) findViewById(R.id.obstacle_center_value);
    obstacleRight = (TextView) findViewById(R.id.obstacle_right_value);
    lightLeft = (TextView) findViewById(R.id.light_left_value);
    lightCenter = (TextView) findViewById(R.id.light_center_value);
    lightRight = (TextView) findViewById(R.id.light_right_value);
    irRight = (TextView) findViewById(R.id.ir_right_value);
    irLeft = (TextView) findViewById(R.id.ir_left_value);
    lineLeft = (TextView) findViewById(R.id.line_left_value);
    lineRight = (TextView) findViewById(R.id.line_right_value);
    toggleButton = (ToggleButton) findViewById(R.id.toggleTimer);
    manualButton = (Button) findViewById(R.id.button_manual_refresh);
    battery_level = (ImageView) findViewById(R.id.battery_level_image);
    
    // set the correct control button based on previous preference
    if (settings.getBoolean(res.getString(R.string.autoRefresh_pref), false)) {
      toggleButton.setChecked(false);
      toggleButton.setVisibility(View.VISIBLE);
      manualButton.setVisibility(View.GONE);
    } else {
      toggleButton.setVisibility(View.GONE);
      manualButton.setVisibility(View.VISIBLE);
    }
    
    // Listener that will change the control button as needed
    preferenceListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
      @Override
      public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

        if (key.equals(res.getString(R.string.autoRefresh_pref))) {
          boolean auto = settings.getBoolean(res.getString(R.string.autoRefresh_pref), false);
          
          if (auto) {
            toggleButton.setVisibility(View.VISIBLE);
            manualButton.setVisibility(View.GONE);
          } else {
            toggleButton.setChecked(false);
            toggleButton.setVisibility(View.GONE);
            manualButton.setVisibility(View.VISIBLE);
          }
        }
      }
    };
    settings.registerOnSharedPreferenceChangeListener(preferenceListener);

    
    r = new Runnable() {
      @Override
      public void run() {
        // Update only of scribbler is connected
        if (appState.getScribbler().isConnected()) {
          if (D) Log.i(TAG, "Populating Values");
          updateValues();
          populate();
        } else {
          if (D) Log.e(TAG, "Disconnected unexpectedly...");
          toggleButton.setChecked(false);
        }
        // Keep polling until user decides to stop or leaves activity
        if (toggleButton.isChecked()) {
          handler.postDelayed(
              this,
              Integer.parseInt(settings.getString(
                  res.getString(R.string.refresh_rate_pref),
                  res.getString(R.string.default_refresh_rate))));
        }
      }
    };

    toggleButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (appState.getScribbler().isConnected()) {
          if (toggleButton.isChecked())
            handler.postDelayed(
                r,
                Integer.parseInt(settings.getString(
                    res.getString(R.string.refresh_rate_pref),
                    res.getString(R.string.default_refresh_rate))));
        } else {
          toggleButton.setChecked(false);
        }
            
      }
    });

    // update information once
    manualButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (appState.getScribbler().isConnected()) 
          r.run(); 
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    toggleButton.setChecked(false);
  }

  @Override
  public void onStop() {
    super.onStop();
    toggleButton.setChecked(false);
  }

  /**
   * Function that properly updates variables containing information for the
   * sensors.
   */
  private void updateValues() {
    HashMap<String, int[]> hm = appState.getScribbler().getAll();
    lineValues = hm.get("LINE");
    irValues = hm.get("IR");
    lightValues = hm.get("LIGHT");
    nameValue = appState.getScribbler().getName();
    batteryValue = appState.getScribbler().getBattery();
    obstacleValues = appState.getScribbler().getObstacle("all");
  }

  /**
   * Function that properly populates the UI with the current sensors values.
   */
  private void populate() {
    // Update Name
    name.setText(nameValue);

    // Update Battery
    battery.setText(Float.toString(batteryValue));
    if (batteryValue < 6.2) {
    	battery.setTextColor(Color.RED);
      	battery_level.setImageResource(R.drawable.battery_low);
    
    } else if (batteryValue >= 6.3 && batteryValue <= 6.8) {
    	battery.setTextColor(Color.YELLOW);
      	battery_level.setImageResource(R.drawable.battery_half);
    
    } else if(batteryValue >= 6.9 && batteryValue <= 7.4 ){
    	battery.setTextColor(Color.YELLOW);
    	battery_level.setImageResource(R.drawable.battery_3_4);
    }
   
    else{
    	battery.setTextColor(Color.GREEN);
    	battery_level.setImageResource(R.drawable.battery_full);
    }

    // Update Obstacle Values
    obstacleLeft.setText(Integer.toString(obstacleValues[0]));
    obstacleCenter.setText(Integer.toString(obstacleValues[1]));
    obstacleRight.setText(Integer.toString(obstacleValues[2]));

    // Update Light Values
    lightLeft.setText(Integer.toString(lightValues[0]));
    lightCenter.setText(Integer.toString(lightValues[1]));
    lightRight.setText(Integer.toString(lightValues[2]));

    // Update IR Values
    irLeft.setText(Integer.toString(irValues[0]));
    irRight.setText(Integer.toString(irValues[1]));

    // Update Line Values
    lineLeft.setText(Integer.toString(lineValues[0]));
    lineRight.setText(Integer.toString(lineValues[1]));
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
