package depauw.edu.myro.android.sandbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This class enables the use of the Scribbler's camera along with the saving file potential
 * of Android.
 *
 * @author Alexander Miller (DePauw University, Class of 2016), alexander.miller110@gmail.com
 * @version Summer 2014
 *
 */
public class Camera_Simple extends Activity {
    
	/**	Maintains the bluetooth connection*/
    private Sandbox appState;
    /**	 Button used for taking the picture with a scribbler*/
    private Button cameraBool;
    /**	 Button used for saving the picture to the Phone's gallery*/
    private Button savePicture;
    /**	 Image of what the Scribbler just took*/
    private ImageView image;
    /**	 Image resource taken by the scirbbler but not always shown.*/
    private Bitmap bitmapImage;
    /**	 boolean to make sure that a picture has been taken*/
    private boolean pictureHasBeenTaken = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_simple);
        
        appState = (Sandbox) getApplicationContext();
        
        cameraBool = (Button) findViewById(R.id.camera_on_off);
        image = (ImageView) findViewById(R.id.camera_simple_image);
        savePicture = (Button) findViewById(R.id.savePictureButton);
        cameraBool.setText("Take Picture");
        cameraBool.setOnClickListener(new OnClickListener(){
            
            @Override
            public void onClick(View v) {
                if(appState.getScribbler().isConnected()){
                    
                    Toast.makeText(getApplicationContext(), "Taking picture...", Toast.LENGTH_LONG).show();
                    bitmapImage = appState.getScribbler().takePicture();
                    pictureHasBeenTaken = true;
                    image.setImageBitmap(bitmapImage);
                }
                else{
                    Toast.makeText(getApplicationContext(), "It appears you are not connected to a Scribbler", Toast.LENGTH_LONG).show();
                }
                
            }
        });
        
        savePicture.setOnClickListener(new OnClickListener(){
            
            @Override
            public void onClick(View v) {
                if(pictureHasBeenTaken == true){
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Camera_Simple.this);
                    
                    // Setting Dialog Title
                    alertDialog2.setTitle("Save Location");
                    
                    // Setting Dialog Message
                    alertDialog2.setMessage("Where would you like to save this picture? External or Internal");
                    
                    // Setting Icon to Dialog
                    alertDialog2.setIcon(R.drawable.ic_launcher);
                    
                    // External
                    alertDialog2.setPositiveButton("External", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            
                            // Want to make sure there is a mounted external drive
                            if(isExternalStorageWritable() == true){
                                // saveToExternalStorage returns a string with the path
                                
                                //content, image, title, description
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmapImage, "Bitmap1", "Woohoo");
                               
                            }
                        }
                    });
                    
                    // Internal
                    alertDialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // saveToInternalStorage returns a string with the path
                            dialog.cancel();
                        }
                    });
                    
                    // Showing Alert Dialog
                    alertDialog2.show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "It appears you haven't taken a picture yet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    
    /**
     * Checks if external storage is available for read and write
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    
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
