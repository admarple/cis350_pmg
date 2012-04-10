package edu.upenn.cis350;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class MapActivity extends Activity {

	public static final int BASEMENT_CODE = 0;
	public static final int EXTERIOR_CODE = 1;
	public static final int INDOORS_CODE = 2;
	public static final int STUDIO_CODE = 3;
	public static final String MAP_CODE_KEY = "mapCode";
	public static final String HIGHLIGHT_CODE_KEY = "highlightCode";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setTouchImage(getIntent().getIntExtra(MAP_CODE_KEY, 0), 
        		      getIntent().getIntExtra(HIGHLIGHT_CODE_KEY, -1));
    }
	
	protected void setTouchImage(int touchCode, int highlightCode) {
		
		TouchImageView toUpdate = new TouchImageView(this);
        // toUpdate.setMaxZoom(4f);
		Bitmap bMap;
		switch (touchCode) {
		case BASEMENT_CODE:
			bMap = BitmapFactory.decodeResource(getResources(), R.drawable.pmg_basement1);
			toUpdate.setImageBitmap(bMap);
			break;
		case EXTERIOR_CODE:
			bMap = BitmapFactory.decodeResource(getResources(), R.drawable.pmg_garden_plan_exterior1);
			toUpdate.setImageBitmap(bMap);
			break;
		case INDOORS_CODE:
			bMap = BitmapFactory.decodeResource(getResources(), R.drawable.pmg_indoors1);
			toUpdate.setImageBitmap(bMap);
			break;
		case STUDIO_CODE:
			bMap = BitmapFactory.decodeResource(getResources(), R.drawable.pmg_studio_plan1);
			toUpdate.setImageBitmap(bMap);
			break;
		}
		toUpdate.setImageOverlay(touchCode);
		toUpdate.setHighlightIcon(highlightCode);
		setContentView(toUpdate);
		toUpdate.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
}
