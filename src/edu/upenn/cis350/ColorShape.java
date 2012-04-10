package edu.upenn.cis350;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;

/**
 * Essentially, just a wrapper for android.graphics.drawable.ShapeDrawable with a permanent color
 * and the ability to select this shape (which changes its status and color)
 * 
 * @author Alex Marple
 *
 */
public class ColorShape {
	private int defaultColor;
	private int clickColor;
	private boolean clicked;
	public ShapeDrawable shape;
	public Rect storeBounds;
	
	/**
	 * Given a shape, a default color, and a click color, create a ColorShape
	 * @param s ShapeDrawable
	 * @param dc default color
	 * @param cc click color
	 */
	public ColorShape(ShapeDrawable s, int dc, int cc) {
		defaultColor = dc;
		clickColor = cc;
		clicked = false;
		shape = s;
		storeBounds = s.copyBounds();
	}
	
	/**
	 * Draw the shape on the specified canvas in the appropriate color
	 * @param canvas
	 */
	public void drawShape(Canvas canvas) {
		shape.getPaint().setColor(clicked ? clickColor : defaultColor);
		shape.draw(canvas);
	}
	
	/**
	 * Mark this ColorShape as "clicked"
	 */
	public void highlight() {
		clicked = true;
	}
	
	/**
	 * Unmark this ColorShape as "clicked"
	 */
	public void release() {
		clicked = false;
	}
}