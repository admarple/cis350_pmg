package edu.upenn.cis350;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class POIActivity extends Activity {
	
	// add all of the image resources here.  They should be in the same order as the string references for POIs
	public final int[] poiImages = {
		R.drawable.poi0,
		R.drawable.poi1,
	};
	
	public String[] poiInfo;
	public String[] poiTitle;
	
	public static final String POI_CODE_KEY = "POI_CODE_KEY";
	ImageView poiImage;
	TextView poiText;
	TextView poiHeader;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        poiInfo = getResources().getStringArray(R.array.poi_info);
    	poiTitle = getResources().getStringArray(R.array.poi_object);
        
        setContentView(R.layout.poi);
        
        int poiNumber = getIntent().getIntExtra(POI_CODE_KEY, 0);
        
        poiHeader = (TextView) findViewById(R.id.poi_header);
        setPoiTitle(poiNumber);
        
        poiImage = (ImageView) findViewById(R.id.poi_image);
        setPoiImage(poiNumber);
        
        poiText = (TextView) findViewById(R.id.poi_info);
        setPoiText(poiNumber);
        
    }
	
	protected void setPoiImage(int poiNumber) {
		poiNumber = Math.min(poiNumber, poiImages.length-1);
		Bitmap bMap = BitmapFactory.decodeResource(getResources(), poiImages[poiNumber]);
        poiImage.setImageBitmap(bMap);
	}
	
	protected void setPoiText(int poiNumber) {
		poiNumber = Math.min(poiNumber, poiInfo.length-1);
        poiText.setText(poiInfo[poiNumber]);
	}
	
	protected void setPoiTitle(int poiNumber) {
		poiNumber = Math.min(poiNumber, poiTitle.length-1);
        poiHeader.setText(poiTitle[poiNumber]);
	}
	
}
