package depauw.edu.myro.android.sandbox;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.EditText;
import android.view.View.OnClickListener;

/**
 * Builds the canvas activity which uses the drawing view functionality. Allows
 * users to draw shapes on a canvas and add photos. In order to take a picture
 * using the scribbler robot, there must be scribbler connected
 *
 * Used tutorial from
 * http://www.c-sharpcorner.com/UploadFile/e14021/capture-image
 * -from-camera-and-selecting-image-from-gallery-o/ to allow the application to
 * access the android camera and gallery
 *
 * @author Leonora Bresette Buccino and Alexander Miller
 * @version Summer 2014
 */
public class Graphic_Canvas extends Activity implements OnClickListener {
    
	/**
	 * Button to create a circle
	 */
	private ImageButton circleBtn;
	/**
	 * Button to create a line
	 */
	private ImageButton lineBtn;
	/**
	 * Button to create an oval
	 */
	private ImageButton ovalBtn;
	/**
	 * Button to create a point
	 */
	private ImageButton pointBtn;
	/**
	 * Button to create a polygon
	 */
	private ImageButton polygonBtn;
	/**
	 * Button to create a rectangle
	 */
	private ImageButton rectangleBtn;
	/**
	 * Button to create text
	 */
	private ImageButton textBtn;
	/**
	 * Button to add an image
	 */
	private ImageButton cameraBtn;
	/**
	 * Custom view created for adding shapes to canvases
	 */
	private DrawingView drawView;
    
	final Context context = this;
	/**
	 * The result of the intent to take a picture
	 */
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	/**
	 * The state of the application, used to get the current scribbler
	 */
	private Sandbox appState;
    
	/**
	 * When this method is called the activity is created. This also creates the
	 * drawing view as well as all of the buttons
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_main);
        
		appState = (Sandbox) getApplicationContext();
		appState.getScribbler();
        
		drawView = (DrawingView) findViewById(R.id.drawing);
		circleBtn = (ImageButton) findViewById(R.id.circle_btn);
		circleBtn.setOnClickListener(this);
		lineBtn = (ImageButton) findViewById(R.id.line_btn);
		lineBtn.setOnClickListener(this);
		ovalBtn = (ImageButton) findViewById(R.id.oval_btn);
		ovalBtn.setOnClickListener(this);
		pointBtn = (ImageButton) findViewById(R.id.point_btn);
		pointBtn.setOnClickListener(this);
		polygonBtn = (ImageButton) findViewById(R.id.polygon_btn);
		polygonBtn.setOnClickListener(this);
		rectangleBtn = (ImageButton) findViewById(R.id.rectangle_btn);
		rectangleBtn.setOnClickListener(this);
		textBtn = (ImageButton) findViewById(R.id.text_btn);
		textBtn.setOnClickListener(this);
		cameraBtn = (ImageButton) findViewById(R.id.camera_btn);
		cameraBtn.setOnClickListener(this);
	}
    
	/**
	 * Initialize the contents of the option menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
	/**
	 * Initialize the on click method for the given view. This controls the
	 * creation of the shapes on the canvas by controlling the buttons.
	 */
	public void onClick(View view) {
		// If the circle button is clicked
		if (view.getId() == R.id.circle_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.circle_prompt,
                                                     null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText radius = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText4);
            
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawCircle(Integer
                                        .parseInt(xCoordinate.getText()
                                                  .toString()), Integer
                                        .parseInt(yCoordinate.getText()
                                                  .toString()), Integer
                                        .parseInt(radius.getText()
                                                  .toString()), paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
            
		}
		// If the line button is clicked
		else if (view.getId() == R.id.line_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater
            .inflate(R.layout.line_prompt, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xStart = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yStart = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText xEnd = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText yEnd = (EditText) promptView
            .findViewById(R.id.editText4);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText5);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawLine(Integer.parseInt(xStart
                                                       .getText().toString()), Integer
                                      .parseInt(yStart.getText()
                                                .toString()),
                                      Integer.parseInt(xEnd.getText()
                                                       .toString()), Integer
                                      .parseInt(yEnd.getText()
                                                .toString()),
                                      paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the oval button is clicked
		else if (view.getId() == R.id.oval_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater
            .inflate(R.layout.oval_prompt, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText height = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText width = (EditText) promptView
            .findViewById(R.id.editText4);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText5);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawOval(Integer
                                      .parseInt(xCoordinate.getText()
                                                .toString()), Integer
                                      .parseInt(yCoordinate.getText()
                                                .toString()), Integer
                                      .parseInt(height.getText()
                                                .toString()), Integer
                                      .parseInt(width.getText()
                                                .toString()), paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the point button is clicked
		else if (view.getId() == R.id.point_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.point_prompt,
                                                     null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText3);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawPoint(Integer
                                       .parseInt(xCoordinate.getText()
                                                 .toString()), Integer
                                       .parseInt(yCoordinate.getText()
                                                 .toString()), paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the polygon button is clicked
		else if (view.getId() == R.id.polygon_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.polygon_prompt,
                                                     null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText numSides = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText radius = (EditText) promptView
            .findViewById(R.id.editText4);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText5);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawPolygon(Integer
                                         .parseInt(xCoordinate.getText()
                                                   .toString()), Integer
                                         .parseInt(yCoordinate.getText()
                                                   .toString()), Integer
                                         .parseInt(numSides.getText()
                                                   .toString()), Integer
                                         .parseInt(radius.getText()
                                                   .toString()), paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the rectangle button is clicked
		else if (view.getId() == R.id.rectangle_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.rectangle_prompt,
                                                     null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText length = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText width = (EditText) promptView
            .findViewById(R.id.editText4);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText5);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawRectangle(Integer
                                           .parseInt(xCoordinate.getText()
                                                     .toString()), Integer
                                           .parseInt(yCoordinate.getText()
                                                     .toString()), Integer
                                           .parseInt(length.getText()
                                                     .toString()), Integer
                                           .parseInt(width.getText()
                                                     .toString()), paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the text button is clicked
		else if (view.getId() == R.id.text_btn) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater
            .inflate(R.layout.text_prompt, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                             context);
			alertDialogBuilder.setView(promptView);
			final EditText text = (EditText) promptView
            .findViewById(R.id.editText1);
			final EditText xCoordinate = (EditText) promptView
            .findViewById(R.id.editText2);
			final EditText yCoordinate = (EditText) promptView
            .findViewById(R.id.editText3);
			final EditText paint = (EditText) promptView
            .findViewById(R.id.editText4);
			// setup a dialog window
			alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    new Color();
                    // get user input and create circle
                    String rgb = paint.getText().toString();
                    String[] rgbArray = rgb.split(",");
                    final Paint paintColor = new Paint();
                    paintColor.setColor(Color.rgb(
                                                  Integer.parseInt(rgbArray[0]),
                                                  Integer.parseInt(rgbArray[1]),
                                                  Integer.parseInt(rgbArray[2])));
                    drawView.drawText(
                                      text.getText().toString(), Integer
                                      .parseInt(xCoordinate
                                                .getText()
                                                .toString()),
                                      Integer.parseInt(yCoordinate
                                                       .getText().toString()),
                                      paintColor);
                }
            })
            .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {
                    dialog.cancel();
                }
            });
			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
		}
		// if the camera button is clicked
		else if (view.getId() == R.id.camera_btn) {
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.photo_selector);
			Button galleryButton = (Button) dialog.findViewById(R.id.button1);
			Button androidButton = (Button) dialog.findViewById(R.id.button2);
			Button scribblerButton = (Button) dialog.findViewById(R.id.button3);
            
			galleryButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(
                                               Intent.ACTION_PICK,
                                               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);
				}
			});
            
			androidButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent takePictureIntent = new Intent(
                                                          MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
						startActivityForResult(takePictureIntent,
                                               REQUEST_IMAGE_CAPTURE);
					}
				}
			});
            
			scribblerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Sandbox appstate = (Sandbox) getApplicationContext();
					if (appstate.getScribbler().isConnected()) {
						Bitmap photo = appstate.getScribbler().takePicture();
						drawView.addImage(photo);
						dialog.dismiss();
					} else {
						Toast.makeText(getApplicationContext(),
                                       "You are not connected to a scribbler",
                                       Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
				}
			});
			dialog.show();
		}
        
	}
    
	/**
	 * This is called when the activity you called has exited. It receives the
	 * result and request codes along with the data. The requests are either
	 * that an image has been loaded from the android gallery or that a picture
	 * has been taken with the camera
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == RESULT_OK) {
            
			Uri selectedImage = data.getData();
			String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor c = getContentResolver().query(selectedImage, filePath,
                                                  null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePath[0]);
			String picturePath = c.getString(columnIndex);
			c.close();
			Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            
			drawView.addImage(thumbnail);
            
		} else if (requestCode == REQUEST_IMAGE_CAPTURE
                   && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			drawView.addImage(imageBitmap);
		}
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