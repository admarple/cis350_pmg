/*
 * TouchImageView.java
 * By: Michael Ortiz
 * Updated By: Patrick Lackemacher
 * Updated again By: Alex Marple
 * -------------------
 * Extends Android ImageView to include pinch zooming and panning.
 * Additionally, supports an overlay of shapes that zooms with the image (AM)
 */

package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.HashMap;

import edu.upenn.cis350.ColorShape;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class TouchImageView extends ImageView {

	Matrix matrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF last = new PointF();
	PointF start = new PointF();
	float minScale = 1f;
	float maxScale = 3f;
	float[] m;

	float redundantXSpace, redundantYSpace;

	float width, height;
	static final int CLICK = 3;
	float saveScale = 1f;
	float scaleMappingRatio = 1f;
	float right, bottom, origWidth, origHeight, bmWidth, bmHeight;

	GestureDetector mGestureDetector;
	ScaleGestureDetector mScaleDetector;

	Context context;
	
	// the overlay of shapes we wish to draw
	HashMap<Integer, ColorShape> overlay;

	public TouchImageView(Context context) {
		super(context);
		sharedConstructing(context);
	}

	public TouchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructing(context);
	}

	private void sharedConstructing(Context context) {
		super.setClickable(true);
		overlay = new HashMap<Integer, ColorShape>();
		
		this.context = context;
		mGestureDetector = new GestureDetector(context, new GestureListener());
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		matrix.setTranslate(1f, 1f);
		m = new float[9];
		setImageMatrix(matrix);
		setScaleType(ScaleType.MATRIX);

		setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				mScaleDetector.onTouchEvent(event);

				matrix.getValues(m);
				float x = m[Matrix.MTRANS_X];
				float y = m[Matrix.MTRANS_Y];
				PointF curr = new PointF(event.getX(), event.getY());

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					last.set(event.getX(), event.getY());
					start.set(last);
					mode = DRAG;
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						float deltaX = curr.x - last.x;
						float deltaY = curr.y - last.y;
						float scaleWidth = Math.round(origWidth * saveScale);
						float scaleHeight = Math.round(origHeight * saveScale);
						if (scaleWidth < width) {
							deltaX = 0;
							if (y + deltaY > 0)
								deltaY = -y;
							else if (y + deltaY < -bottom)
								deltaY = -(y + bottom);
						} else if (scaleHeight < height) {
							deltaY = 0;
							if (x + deltaX > 0)
								deltaX = -x;
							else if (x + deltaX < -right)
								deltaX = -(x + right);
						} else {
							if (x + deltaX > 0)
								deltaX = -x;
							else if (x + deltaX < -right)
								deltaX = -(x + right);

							if (y + deltaY > 0)
								deltaY = -y;
							else if (y + deltaY < -bottom)
								deltaY = -(y + bottom);
						}
						matrix.postTranslate(deltaX, deltaY);
						last.set(curr.x, curr.y);
					}
					break;

				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				}
				setImageMatrix(matrix);
				invalidate();
				return true; // indicate event was handled
			}

		});
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		if (bm != null) {
			bmWidth = bm.getWidth();
			bmHeight = bm.getHeight();
		}
	}

	public void setMaxZoom(float x) {
		maxScale = x;
	}

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			mode = NONE;
			// check to see if we want to go to a POI page
			PointF curr = new PointF(e.getX(), e.getY());
			checkForPOINav(curr);
			performClick();
			return true;
		}

		@Override
		public boolean onDoubleTap(final MotionEvent e) {
			final float targetScale;

			if (Math.ceil(saveScale) < maxScale) {
				targetScale = maxScale;
			} else if (Math.ceil(saveScale) == maxScale) {
				targetScale = minScale;
			} else {
				return false;
			}

			matrix.getValues(m);
			final float scale = m[Matrix.MSCALE_X];
			final float scaleFactor = targetScale / scaleMappingRatio;

			final float[] doubleTapImagePoint = new float[] { e.getX(),
					e.getY() };
			Matrix inverse = new Matrix();
			matrix.invert(inverse);
			inverse.mapPoints(doubleTapImagePoint);

			final float finalX;
			final float finalY;
			if (targetScale == minScale) {
				RectF imageBounds = new RectF(getDrawable().getBounds());

				matrix.reset();
				matrix.postTranslate(-doubleTapImagePoint[0],
						-doubleTapImagePoint[1]);
				matrix.postScale(scaleFactor, scaleFactor);
				matrix.mapRect(imageBounds);

				finalX = ((width - imageBounds.width()) / 2.0f)
						- imageBounds.left;
				finalY = ((height - imageBounds.height()) / 2.0f)
						- imageBounds.top;
			} else {
				finalX = e.getX();
				finalY = e.getY();
			}

			final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
			final long startTime = System.currentTimeMillis();
			final long duration = 800;

			post(new Runnable() {

				public void run() {

					float t = (float) (System.currentTimeMillis() - startTime)
							/ duration;
					t = t > 1.0f ? 1.0f : t;
					float interpolatedRatio = interpolator.getInterpolation(t);

					float mScaleFactor = scale + interpolatedRatio
							* (scaleFactor - scale);
					saveScale = mScaleFactor * scaleMappingRatio;

					float tempX = e.getX() + interpolatedRatio
							* (finalX - e.getX());
					float tempY = e.getY() + interpolatedRatio
							* (finalY - e.getY());

					matrix.reset();
					matrix.postTranslate(-doubleTapImagePoint[0],
							-doubleTapImagePoint[1]);
					matrix.postScale(mScaleFactor, mScaleFactor);
					matrix.postTranslate(tempX, tempY);

					right = width * saveScale - width
							- (2 * redundantXSpace * saveScale);
					bottom = height * saveScale - height
							- (2 * redundantYSpace * saveScale);
					if (origWidth * saveScale <= width
							|| origHeight * saveScale <= height) {
						matrix.getValues(m);
						float x = m[Matrix.MTRANS_X];
						float y = m[Matrix.MTRANS_Y];
						if (mScaleFactor < 1) {
							if (Math.round(origWidth * saveScale) < width) {
								if (y < -bottom) {
									matrix.postTranslate(0, -(y + bottom));
								} else if (y > 0) {
									matrix.postTranslate(0, -y);
								}
							} else {
								if (x < -right) {
									matrix.postTranslate(-(x + right), 0);
								} else if (x > 0) {
									matrix.postTranslate(-x, 0);
								}
							}
						}
					} else {
						matrix.getValues(m);
						float x = m[Matrix.MTRANS_X];
						float y = m[Matrix.MTRANS_Y];
						if (mScaleFactor < 1) {
							if (x < -right) {
								matrix.postTranslate(-(x + right), 0);
							} else if (x > 0) {
								matrix.postTranslate(-x, 0);
							}
							if (y < -bottom) {
								matrix.postTranslate(0, -(y + bottom));
							} else if (y > 0) {
								matrix.postTranslate(0, -y);
							}
						}
					}

					setImageMatrix(matrix);

					if (t < 1.0f) {
						post(this);
					}
				}
			});

			return true;
		}
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = (float) Math.min(
					Math.max(.95f, detector.getScaleFactor()), 1.05);
			float origScale = saveScale;
			saveScale *= mScaleFactor;
			if (saveScale > maxScale) {
				saveScale = maxScale;
				mScaleFactor = maxScale / origScale;
			} else if (saveScale < minScale) {
				saveScale = minScale;
				mScaleFactor = minScale / origScale;
			}
			right = width * saveScale - width
					- (2 * redundantXSpace * saveScale);
			bottom = height * saveScale - height
					- (2 * redundantYSpace * saveScale);
			if (origWidth * saveScale <= width
					|| origHeight * saveScale <= height) {
				matrix.postScale(mScaleFactor, mScaleFactor, width / 2,
						height / 2);
				if (mScaleFactor < 1) {
					matrix.getValues(m);
					float x = m[Matrix.MTRANS_X];
					float y = m[Matrix.MTRANS_Y];
					if (mScaleFactor < 1) {
						if (Math.round(origWidth * saveScale) < width) {
							if (y < -bottom)
								matrix.postTranslate(0, -(y + bottom));
							else if (y > 0)
								matrix.postTranslate(0, -y);
						} else {
							if (x < -right)
								matrix.postTranslate(-(x + right), 0);
							else if (x > 0)
								matrix.postTranslate(-x, 0);
						}
					}
				}
			} else {
				matrix.postScale(mScaleFactor, mScaleFactor,
						detector.getFocusX(), detector.getFocusY());
				matrix.getValues(m);
				float x = m[Matrix.MTRANS_X];
				float y = m[Matrix.MTRANS_Y];
				if (mScaleFactor < 1) {
					if (x < -right)
						matrix.postTranslate(-(x + right), 0);
					else if (x > 0)
						matrix.postTranslate(-x, 0);
					if (y < -bottom)
						matrix.postTranslate(0, -(y + bottom));
					else if (y > 0)
						matrix.postTranslate(0, -y);
				}
			}
			return true;

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		// Fit to screen.
		float scale;
		float scaleX = (float) width / (float) bmWidth;
		float scaleY = (float) height / (float) bmHeight;
		scale = Math.min(scaleX, scaleY);
		matrix.setScale(scale, scale);
		setImageMatrix(matrix);
		saveScale = 1f;
		scaleMappingRatio = saveScale / scale;

		// Center the image
		redundantYSpace = (float) height - (scale * (float) bmHeight);
		redundantXSpace = (float) width - (scale * (float) bmWidth);
		redundantYSpace /= (float) 2;
		redundantXSpace /= (float) 2;

		matrix.postTranslate(redundantXSpace, redundantYSpace);

		origWidth = width - 2 * redundantXSpace;
		origHeight = height - 2 * redundantYSpace;
		right = width * saveScale - width - (2 * redundantXSpace * saveScale);
		bottom = height * saveScale - height
				- (2 * redundantYSpace * saveScale);
		setImageMatrix(matrix);
	}
	
	@Override
	public void setImageMatrix(Matrix matrix) {
		super.setImageMatrix(matrix);
		// we need to redraw all of the shapes in the overlay based on the new matrix
		float[] m = new float[9];
		matrix.getValues(m);
		for (ColorShape cs : overlay.values()) {
			int newLeft = Math.round(cs.storeBounds.left * m[Matrix.MSCALE_X] + m[Matrix.MTRANS_X]); // need to get back to integer...
			int newTop = Math.round(cs.storeBounds.top * m[Matrix.MSCALE_Y] + m[Matrix.MTRANS_Y]);
			int newRight = Math.round(newLeft + (cs.storeBounds.right - cs.storeBounds.left) * m[Matrix.MSCALE_X]);
			int newBottom = Math.round(newTop + (cs.storeBounds.bottom - cs.storeBounds.top) * m[Matrix.MSCALE_Y]);
			cs.shape.setBounds(newLeft, newTop, newRight, newBottom);
		}
	}
	
	public void setImageOverlay(int overlayCode) {
		// TODO:set up the particular overlays
		switch (overlayCode) {
		case MapActivity.BASEMENT_CODE:
			// POI 3, East of Recycling Room
			addOverlayIcon(3, 900, 433, 917, 450);
			break;
		case MapActivity.EXTERIOR_CODE:
			// POI 4, Courtyard North
			addOverlayIcon(4, 700, 600, 717, 617);
			// POI 5, Outdoor Labyrinth, First Alley Walls
			addOverlayIcon(5, 383, 400, 400, 417);
			// POI 6, Under Huppa
			addOverlayIcon(6, 225, 391, 242, 408);
			// POI 7, West wall near sanctuary
			addOverlayIcon(7, 200, 100, 217, 117);
			// POI 8, Garden
			addOverlayIcon(8, 300, 200, 317, 217);
			// POI 9, West Wall "Philadelphia"
			addOverlayIcon(9, 500, 75, 517, 92);
			// POI 10, Alley 2 [???]
			addOverlayIcon(10, 466, 175, 483, 192);
			// POI 11, Pool 1
			addOverlayIcon(11, 608, 366, 625, 383);
			break;
		case MapActivity.INDOORS_CODE:
			// POI 0, Lobby
			addOverlayIcon(0, 800, 333, 817, 350);
			// POI 12, Watkins Street
			addOverlayIcon(12, 1033, 350, 1050, 367);
			break;
		case MapActivity.STUDIO_CODE:
			// POI 1, Gallery 1 South Wall
			addOverlayIcon(1, 608, 541, 625, 558);
			// POI 2, Gallery 1 West Wall
			addOverlayIcon(2, 358, 266, 375, 283);
			break;
		}
	}
	
	/* 
	 * having this method makes it dangerous to use this class without an Activity as the Context
	 */
	protected void checkForPOINav(PointF p) {
		for (int key : overlay.keySet()) {
			ColorShape icon = overlay.get(key);
			if (icon != null) {
				if (pointInEllipse(p.x, p.y, icon.shape.copyBounds() )) {
					Intent intent = new Intent(((Activity)getContext()), POIActivity.class);
        			intent.putExtra(POIActivity.POI_CODE_KEY, key);
        			((Activity)getContext()).startActivity(intent);
				}
			}
		}
	}
	
	public void setHighlightIcon(int iconIndex) {
		ColorShape toHighlight = overlay.get(iconIndex);
		if (toHighlight != null) {
			toHighlight.highlight();
		}
	}
	
	protected void addOverlayIcon(int poiIndex, int left, int top, int right, int bottom) {
		ShapeDrawable sd0 = new ShapeDrawable(new OvalShape());
		sd0.setBounds(left, top, right, bottom);
		ColorShape shape0 = new ColorShape(sd0, Color.BLUE, Color.RED);
		overlay.put(poiIndex, shape0);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Just draw all of the shapes
		for (ColorShape cs : overlay.values()) {
			cs.drawShape(canvas);
		}
	} 
	
	/**
	 * Determining the intersection of two shapes is non-trivial.  Some would argue that
	 * collide() belongs in ColorShape, but that is a dangerous move.  It would give
	 * the impression that no matter what new shapes we wanted to check for collision, we
	 * would be able to handle them appropriately, and this is an unreasonable request
	 * unless we have our shapes represented as lists of vertices or sides vectors and
	 * curves.  Since we don't (and developing a general graphics library simply for an
	 * Android graphics tutorial seems a bit much), we explicitly handle the cases that
	 * we wish to support right here in ShapesView.  This allows us to continue to use
	 * the Android graphics libraries.
	 * 
	 * Currently, we check intersections of RectShape and OvalShape objects in any order.
	 */
	public static boolean collide(ColorShape s1, ColorShape s2) {
		Rect r1 = s1.shape.getBounds();
		Rect r2 = s2.shape.getBounds();
		
		/* 
		 * Set up the bounding rectangles so that r2 is in the 1st quadrant with respect to r1
		 * This may cause reflections of the x- and y- axis resulting in an orientation of r1 
		 * and r2 where r1 is centered at origin and r2 is in the first quadrant.
		 */
		Rect r3 = new Rect();
		int left = r1.centerX() + Math.abs(r2.centerX() - r1.centerX()) - r2.width()/2;
		int right = r1.centerX() + Math.abs(r2.centerX() - r1.centerX()) + r2.width()/2;
		int bottom = r1.centerY() + Math.abs(r2.centerY() - r1.centerY()) - r2.height()/2;
		int top = r1.centerY() + Math.abs(r2.centerY() - r1.centerY()) + r2.height()/2;
		r3.set(left, top, right, bottom);
		r2 = r3;
		
		if (s1.shape.getShape() instanceof OvalShape) {
			if (s2.shape.getShape() instanceof OvalShape) {
				/* 
				 * CASE 1: Two Ovals.  Center r1 at origin.  Iterate along the bottom-left 
				 * portion of r2, and if any point on the ellipse is inside r1, the shapes 
				 * collide.  Note that points on the perimeter of r2 may be in the 4th quadrant 
				 * with respect to r1 (i.e. r1 is inside of r2).  These ellipses still 
				 * intersect, and so instead of using the point on the perimeter, we use the 
				 * projection onto either the positive X or positive Y axis.  The rationale 
				 * here is that if (x, -y) is a perimeter point of r2, then (x,0) is an 
				 * internal point of r2 (similarly, if (-x,y) is a perimeter point of r2, then 
				 * (0,y) is an internal point of r2, and if (-x,-y) is a perimeter point of r2,
				 * then (0,0) is an internal point of r2).  
				 */
				// for each integer between its center and the max on its semimajor axis
				for (int i = Math.min(r2.left, r2.right); i <= r2.centerX(); i++) {
					double x = i;
					double x0 = r2.centerX();
					double y0 = r2.centerY();
					double a = r2.width()/2.0;
					double b = r2.height()/2.0;

					double y = y0 - Math.sqrt(b*b*(1 - (x-x0)*(x-x0)/(a*a)));
					if (pointInEllipse( Math.max(x, r1.centerX()), Math.max(y, r1.centerY()), r1)) {
						// they collide
						return true;
					}
				}
				// they don't collide
				return false;
			} else if (s2.shape.getShape() instanceof RectShape) {
				/*
				 * CASE 2: Oval at origin, rectangle in first quadrant
				 * 
				 * We can just check the bottom corner of r2.  If it is either inside the oval
				 * or in the 2nd, 3rd, or 4th quadrant, then the shapes intersect.
				 */
				return pointInEllipse(Math.max(r2.left, r1.centerX()), Math.max(r2.bottom, r1.centerY()), r1);
			} else {
				// we don't recognize the type of s2
				return false;
			}
		} else if (s1.shape.getShape() instanceof RectShape) {
			if (s2.shape.getShape() instanceof OvalShape) {
				/*
				 * CASE 3:  Case 2 in reverse 
				 * 
				 * Just swap the arguments and call again
				 */
				return collide(s2, s1);
			} else if (s2.shape.getShape() instanceof RectShape) {
				/*
				 * CASE 4:  Two rectangles
				 * 
				 * Just check to see that they overlap in both X and Y
				 */
				return (r2.left < Math.max(r1.left, r1.right) && r2.bottom < Math.max(r1.top, r1.bottom));
			} else {
				// we don't recognize the type of s2
				return false;
			}
		} else {
			// we don't recognize the type of s1
			return false;
		}
	}
	
	/* Based on the Cartesian equation for an ellipse within rectangle defined
	 * by r.
	 *  
	 * Technically, ellipse != oval, but I suspect  that OvalShape is actually
	 * an ellipse, not an oval.  Either way, the difference between an ellipse
	 * and an oval within a bounding rectangle is negligible and this should
	 * work for all intents and purposes.
	 */
	public static boolean pointInEllipse(double x, double y, Rect r) {
		double x0 = r.centerX();
		double y0 = r.centerY();
		double a = r.width()/2;
		double b = r.height()/2;
		
		double eq = ( ((x - x0)*(x - x0)) / (a*a) ) + ( ((y - y0)*(y - y0)) / (b*b) );
		return (eq <= 1.0);
	}
}