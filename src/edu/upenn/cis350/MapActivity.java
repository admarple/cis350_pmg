package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class MapActivity extends Activity {

	public static final int EXTERIOR_CODE = 1;
	public static final int INDOORS_CODE = 2;
	public static final int STUDIO_CODE = 3;
	public static final String MAP_CODE_KEY = "mapCode";
	public static final String HIGHLIGHT_CODE_KEY = "highlightCode";
	public static final int GET_HIGHLIGHT_POI_INDEX = 1;
	TouchImageView toUpdate;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setTouchImage(getIntent().getIntExtra(MAP_CODE_KEY, 1), 
        		      getIntent().getIntExtra(HIGHLIGHT_CODE_KEY, -1));
    }
	
	protected void setTouchImage(int touchCode, int highlightCode) {
		
		toUpdate = new TouchImageView(this);
        // toUpdate.setMaxZoom(4f);
		Bitmap bMap;
		switch (touchCode) {
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
	
	/*
	 * Need to reset the highlighted icon if we're starting to loop from map to POI pages  
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_HIGHLIGHT_POI_INDEX) {
            if (resultCode == RESULT_OK) {
                toUpdate.setHighlightIcon(data.getIntExtra(HIGHLIGHT_CODE_KEY, -1));
            }
        }
    }
	
}
