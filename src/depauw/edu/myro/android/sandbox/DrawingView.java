package depauw.edu.myro.android.sandbox;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Creates a customized view that allows shapes to be drawn on a canvas.
 * 
 * @author Leonora Bresette Buccino and Alexander Miller
 * @version Summer 2014
 */
public class DrawingView extends View {

	/**
	 * the draw paint color
	 */
	private Paint drawPaint;

	/**
	 * The canvas paint color
	 */
	private Paint canvasPaint;

	/**
	 * the default paint color
	 */
	private int paintColor = 0xFF660000;

	/**
	 * The canvas that is displayed on the view
	 */
	private Canvas drawCanvas;

	/**
	 * The bitmap of the canvas
	 */
	private Bitmap canvasBitmap;

	/**
	 * DrawingView constructor. Creates a new drawing view on the given context
	 * with the given attribute set
	 * 
	 * @param context
	 *            of the view
	 * @param attrs
	 *            the attributes of the XML
	 */
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupDrawing();
	}

	/**
	 * Method to set up a new drawing on the drawing view.
	 */
	private void setupDrawing() {
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}

	/**
	 * This method is called with the size of the layout has changed. This will
	 * change the canvas size to be the same as the new size of the layout.
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}

	/**
	 * This method displays the canvas on the view.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
	}

	/**
	 * Draws a circle on the canvas with the center point at (x,y) and with the
	 * given radius and paint color
	 * 
	 * @param x
	 *            the integer of the x coordinate of the center of the circle
	 * @param y
	 *            the integer of the y coordinate of the center of the circle
	 * @param radius
	 *            the integer value of the radius of the circle
	 * @param paint
	 *            the paint color of the circle
	 */
	public void drawCircle(int x, int y, int radius, Paint paint) {
		drawCanvas.drawCircle(x, y, radius, paint);
		invalidate();
	}

	/**
	 * Draws a line on the canvas that starts at (xStart,yStart) and ends at
	 * (xEnd,yEnd) with the given paint color
	 * 
	 * @param xStart
	 *            the starting x coordinate of the line
	 * @param yStart
	 *            the starting y coordinate of the line
	 * @param xEnd
	 *            the ending x coordinate of the line
	 * @param yEnd
	 *            the end y coordinate of the line
	 * @param paint
	 *            the color of the line
	 */
	public void drawLine(int xStart, int yStart, int xEnd, int yEnd, Paint paint) {
		drawCanvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
		invalidate();
	}

	/**
	 * Draws an oval on the given canvas centered at (x,y) with the given
	 * height, width and paint color.
	 * 
	 * @param x
	 *            the center x coordinate of the oval
	 * @param y
	 *            the center y coordinate of the oval
	 * @param height
	 *            the height of the oval
	 * @param width
	 *            the width of the oval
	 * @param paint
	 *            the color of the oval
	 */
	public void drawOval(int x, int y, int height, int width, Paint paint) {
		RectF ovalRect = new RectF(x - (width / 2), y - (width / 2), x
				+ (width / 2), y + (height / 2));
		drawCanvas.drawOval(ovalRect, paint);
		invalidate();
	}

	/**
	 * draws a point on the canvas at (x,y) with given paint color
	 * 
	 * @param x
	 *            the x coordinate of the point
	 * @param y
	 *            the y coordinate of the point
	 * @param paint
	 *            the color of the point
	 */
	public void drawPoint(int x, int y, Paint paint) {
		drawCanvas.drawPoint(x, y, paint);
		invalidate();

	}

	/**
	 * Draws a polygon centered at x,y with the given number of sides, radius
	 * and paint color. Does this using paths
	 * 
	 * @param x
	 *            the x coordinate of the center of the polygon
	 * @param y
	 *            the y coordinate of the center of the polygon
	 * @param numOfSides
	 *            the number of side the polygon has
	 * @param radius
	 *            the radius of the polygon
	 * @param paint
	 *            the color of the polygon
	 */
	public void drawPolygon(int x, int y, int numOfSides, int radius,
			Paint paint) {
		double a = (Math.PI * 2) / numOfSides;
		Path pathy = new Path();
		pathy.moveTo((float) (x + radius), y);
		for (int i = 1; i < numOfSides; i++) {
			pathy.rLineTo(Float.valueOf((float) (radius * Math.cos(a * i))),
					(float) (radius * Math.sin(a * i)));
		}
		drawCanvas.drawPath(pathy, paint);
		invalidate();
	}

	/**
	 * draws a rectangle on the canvas with the top left corner at x,y and with
	 * given length, width and paint color
	 * 
	 * @param x
	 *            the x coordinate of the top left corner of the rectangle
	 * @param y
	 *            the y coordinate of the top left corner of the rectangle
	 * @param length
	 *            the length of the rectangle
	 * @param width
	 *            the width of the rectangle
	 * @param paint
	 *            the color of the rectangle
	 */
	public void drawRectangle(int x, int y, int length, int width, Paint paint) {
		drawCanvas.drawRect(x, y, x + length, y + width, paint);
		invalidate();
	}

	/**
	 * Displays the given text on the canvas at (x,y) with given paint color
	 * 
	 * @param text
	 *            the string of text
	 * @param x
	 *            the x coordinate of the text
	 * @param y
	 *            the y coordinate of the text
	 * @param paint
	 *            the color of the text
	 */
	public void drawText(String text, int x, int y, Paint paint) {
		drawCanvas.drawText(text, x, y, paint);
		invalidate();
	}

	/**
	 * Adds the given image to the canvas. Takes in the image as a bitmap. This
	 * is used when taking a picture with the camera, getting a picture from the
	 * gallery or taking a picture with the scribbler
	 * 
	 * @param bit
	 *            the Bitmap of the image
	 */
	public void addImage(Bitmap bit) {
		drawCanvas.drawBitmap(bit, 0, 0, canvasPaint);
		invalidate();
	}
}
