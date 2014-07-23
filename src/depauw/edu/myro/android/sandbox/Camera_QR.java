//package depauw.edu.myro.android.sandbox;
//
//import com.google.zxing.NotFoundException;
//import com.google.zxing.ReaderException;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import depauw.edu.myro.original.MyroQRReader;
//import depauw.edu.myro.original.MyroUtils;
//
//public class Camera_QR extends Activity {
//	
//	private Sandbox appState;
//	private TextView console;
//	private Button bool;
//	private ImageView camera_image_bitmap;
//    private String lastResult = "";
//    private Bitmap newBm;
//    MyroQRReader qrReader;
//    
//    private boolean takePictures = false;
//	 Handler mHandler;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.camera_qr_main);
//		
//		appState = (Sandbox) getApplicationContext();
//		
//		console = (TextView) findViewById(R.id.camera_console);
//		
//		//the initial clear, used later as a testing tool
//		console.setText("");
//		
//		// this line below allows the textview to be scrollable
//		console.setMovementMethod(new ScrollingMovementMethod());
//		
//		camera_image_bitmap = (ImageView) findViewById(R.id.camera_qr_image_bitmap);
//		
//		bool = (Button) findViewById(R.id.camera_bool);
//		bool.setText("Enable");
//		bool.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				
//				if(takePictures == true){
//					//how to cancel it all
//					bool.setText("Enable Camera");
//					takePictures = false;
//				}
//				else{
//					//start taking pictures repeatedly 
//					bool.setText("Disable Camera");
//					takePictures = true;
//					
//					if(appState.getScribbler().isConnected())
//					{
//						
//						newBm = appState.getScribbler().takePicture();						
//						camera_image_bitmap.setImageBitmap(newBm);
//						try {
//							Toast.makeText(getApplicationContext(), qrReader.decode(newBm), Toast.LENGTH_LONG).show();
//						} catch (ReaderException e) {
//							Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//						}
//						
//						consoleCheck("Picture Taken");														
//					}
//					else{
//						consoleCheck("Not connected");
//					}
//				}				
//			}
//	    });
//	}	
//	
//	private String scan()
//    {
//		String results = "";
//        while(results.equals(""))
//        {     	
//            
//            Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_LONG).show();
//            // gotta get closer to that image
//        	//appState.getScribbler().forward(0.5);
//			//appState.getScribbler().stop();
//        }
//        return results;
//    }
//	
//	private void qrTest(String QRresults)
//    {
//        if (QRresults.contains(";")) 
//        {
//
//            String[] parts = QRresults.split(";");
//
//            for(int i = 0; i < parts.length; i++)
//            {
//                lastResult = parts[i];
//                if(parts[i].contains("stop"))
//                {
//                	consoleCheck("stop");
//                    appState.getScribbler().stop();
//                    break;
//                }
//                else if(parts[i].contains("forward"))
//                {
//                    String test = parts[i].substring(7, parts[i].length());
//                    String command1 = test.substring(1, test.indexOf(','));
//                    String command2 = test.substring(test.indexOf(',')+2, test.length()-1);
//                    double value1 = Double.parseDouble(command1);
//                    double value2 = Double.parseDouble(command2);
//                    
//                    //debugging
//                    Log.d("Camera_QR_Forward", "Forward");
//                    Log.d("Camera_QR_Forward", "value1 " + value1);
//                    Log.d("Camera_QR_Forward", "value2 " + value2);
//                    
//                    //console
//                    consoleCheck("Forward");
//                    consoleCheck(command1);
//                    consoleCheck(command2);
//                    
//                    //performing the action
//                    appState.getScribbler().forward(value1);
//                    double numSeconds = Double.valueOf(value2);
//    				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
//    				MyroUtils.sleep(numSeconds);
//    				appState.getScribbler().stop();
//
//                }
//                else if(parts[i].contains("backward"))
//                {
//                    String test = parts[i].substring(9, parts[i].length());
//                    String command1 = test.substring(1, test.indexOf(','));
//                    String command2 = test.substring(test.indexOf(',')+2, test.length()-1);
//                    double value1 = Double.parseDouble(command1);
//                    double value2 = Double.parseDouble(command2);
//                    
//                    // debugging
//                    Log.d("Camera_QR_Backward", "backward");
//                    Log.d("Camera_QR_Backward", "value1 " + value1);
//                    Log.d("Camera_QR_Backward", "value2 " + value2);
//                    
//                    // console
//                    consoleCheck("Backward");
//                    consoleCheck(command1);
//                    consoleCheck(command2);
//                    
//                    // performing the action
//                    appState.getScribbler().backward(value1);               
//    				double numSeconds = Double.valueOf(value2);
//    				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
//    				MyroUtils.sleep(numSeconds);
//    				appState.getScribbler().stop();
//                }
//                else if(parts[i].contains("turnRight"))
//                {
//                    String test = parts[i].substring(9, parts[i].length());
//                    String command1 = test.substring(1, test.indexOf(','));
//                    String command2 = test.substring(test.indexOf(',')+2, test.length()-1);
//                    double value1 = Double.parseDouble(command1);
//                    double value2 = Double.parseDouble(command2);
//                    
//                    // debugging
//                    Log.d("Camera_QR_turnRight", "backward");
//                    Log.d("Camera_QR_turnRight", "value1 " + value1);
//                    Log.d("Camera_QR_turnRight", "value2 " + value2);
//                    
//                    // console
//                    consoleCheck("Turn Right");
//                    consoleCheck(command1);
//                    consoleCheck(command2);
//                    
//                    // performing the action
//                    appState.getScribbler().turnRight(value1);
//                    double numSeconds = Double.valueOf(value2);
//    				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
//    				MyroUtils.sleep(numSeconds);
//    				appState.getScribbler().stop();
//                }
//                else if(parts[i].contains("turnLeft"))
//                {
//                    String test = parts[i].substring(8, parts[i].length());
//                    String command1 = test.substring(1, test.indexOf(','));
//                    String command2 = test.substring(test.indexOf(',')+2, test.length()-1);
//                    double value1 = Double.parseDouble(command1);
//                    double value2 = Double.parseDouble(command2);
//                    
//                    // debugging
//                    Log.d("Camera_QR_turnLeft", "backward");
//                    Log.d("Camera_QR_turnLeft", "value1 " + value1);
//                    Log.d("Camera_QR_turnLeft", "value2 " + value2);
//                    
//                    // console
//                    consoleCheck("Turn Right");
//                    consoleCheck(command1);
//                    consoleCheck(command2);
//                    
//                    // performing the action
//                    appState.getScribbler().turnLeft(value1);
//                    double numSeconds = Double.valueOf(value2);
//    				assert numSeconds >= 0.0 : "numSeconds not >= 0.0";
//    				MyroUtils.sleep(numSeconds);
//    				appState.getScribbler().stop();
//                }
//                else
//                {
//                    // Don't know what just happened
//                	appState.getScribbler().stop();
//                	Log.d("Camera_QR_Error", "I don't understand the QR code. It says: " + parts.toString());
//                	consoleCheck("Camera_QR_Error, I don't understand the QR code. It says: " + parts.toString());
//                }
//            }
//            if(!lastResult.contains("stop"))
//            {
//                scan();
//            }
//        } 
//    }
//	
//	/**
//	 * simply checks to see if the TextView is empty or not. 
//	 * 
//	 * If it is empty, then the message is printed. 
//	 * 
//	 * If its not empty, then the text is copied over to a new string with the new message on a new line
//	 * 
//	 * @param String message
//	 */
//	private void consoleCheck(String message)
//	{
//		if(console.getText().equals("")){
//			console.setText(message);
//		}
//		else{
//			String original = console.getText().toString();
//			console.setText(original + "\n" + message);
//		}
//	}
//}