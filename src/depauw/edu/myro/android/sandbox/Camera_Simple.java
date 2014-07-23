package depauw.edu.myro.android.sandbox;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
    
    private Sandbox appState;
    private Button cameraBool, savePicture, showPicture;
    private ImageView image, imageShow;
    private Bitmap bitmapImage;
    private boolean pictureHasBeenTaken = false;
    private String path = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_simple);
        
        appState = (Sandbox) getApplicationContext();
        
        cameraBool = (Button) findViewById(R.id.camera_on_off);
        showPicture = (Button) findViewById(R.id.showPictureButton);
        imageShow = (ImageView) findViewById(R.id.camera_simple_show);
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
        
        showPicture.setOnClickListener(new OnClickListener(){
            
            @Override
            public void onClick(View v) {
                if(pictureHasBeenTaken == true){
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Camera_Simple.this);
                    
                    // Setting Dialog Title
                    alertDialog2.setTitle("Show Picture");
                    
                    // Setting Dialog Message
                    alertDialog2.setMessage("Where is the location of your last picture? External or Internal");
                    
                    // Setting Icon to Dialog
                    alertDialog2.setIcon(R.drawable.ic_launcher);
                    
                    // External
                    alertDialog2.setNeutralButton("External", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            
                            // Want to make sure there is a mounted external drive
                            if(isExternalStorageWritable() == true){
                                // saveToExternalStorage returns a string with the path
                                path = saveImageToExternal(bitmapImage);
                            }
                        }
                    });
                    
                    // Internal
                    alertDialog2.setNeutralButton("Internal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // saveToInternalStorage returns a string with the path
                            File filePath = getFileStreamPath("temp.jpg");
                            imageShow.setImageDrawable(Drawable.createFromPath(filePath.toString()));
                        }
                    });
                    
                    // Cancel
                    alertDialog2.setNegativeButton("Cancel",
                                                   new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
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
                                
                                //path = saveImageToExternal(bitmapImage);
                            }
                        }
                    });
                    
                    // Internal
                    alertDialog2.setNegativeButton("Internal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // saveToInternalStorage returns a string with the path
                            path = saveToInternalSorage(bitmapImage);
                            if(!path.equals("")){
                                Toast.makeText(getApplicationContext(), "Save complete", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            }
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
    
    /**
     * This function saves Bitmap images to external memory.
     * @return String with the file path of the image
     * @param Bitmap file
     */
    private String saveImageToExternal(Bitmap finalBitmap) {
        
	    String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/Sandbox/saved_images");
	    myDir.mkdirs();
	    Random generator = new Random();
	    int n = 10000;
	    n = generator.nextInt(n);
	    String fname = "Image-"+ n +".jpg";
	    File file = new File (myDir, fname);
	    if (file.exists ()) file.delete ();
	    try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            
	    } catch (Exception e) {
            e.printStackTrace();
	    }
	    
	    return myDir.getAbsolutePath();
	}
    
    /**
     * This function saves Bitmap images to internal memory.
     * @param bitmapImage
     * @return String with the file path of the image
     */ 
    private String saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        
        // path to /data/data/yourapp/app_data/Sandbox
        File directory = cw.getDir("Sandbox", Context.MODE_PRIVATE);
        
        // Create imageDir
        File mypath=new File(directory, "temp.jpg");
        
        FileOutputStream fos = null;
        try {           
            
            fos = new FileOutputStream(mypath);
            
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
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
