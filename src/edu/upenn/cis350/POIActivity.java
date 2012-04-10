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
		R.drawable.standard_pmg_logo_jpeg,
		R.drawable.poi3_1,
		R.drawable.standard_pmg_logo_jpeg,
	};
	
	public String[] poiInfo;
	public String[] poiTitle;
	
	public static final String POI_CODE_KEY = "POI_CODE_KEY";
	ImageView poiImage;
	TextView poiText;
	TextView poiHeader;
	PointOfInterest poi;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        poiInfo = getResources().getStringArray(R.array.poi_info);
    	poiTitle = getResources().getStringArray(R.array.poi_theme);
        
        setContentView(R.layout.poi);
        
        int poiNumber = getIntent().getIntExtra(POI_CODE_KEY, 0);
        poi = PointOfInterest.getPOI(poiNumber);
        
        poiHeader = (TextView) findViewById(R.id.poi_header);
        setPoiTitle(poiNumber);
        
        poiText = (TextView) findViewById(R.id.poi_info);
        setPoiText(poiNumber);
        
        setPoiImages(poiNumber);
    }
	
	protected void setPoiImages(int poiNumber) {
		poiImage = (ImageView) findViewById(R.id.poi_image);
		poiNumber = Math.min(poiNumber, poiImages.length-1);
		Bitmap bMap = BitmapFactory.decodeResource(getResources(), poi.images[0]);
        poiImage.setImageBitmap(bMap);
        
        // this is kludgey, but I still haven't found a nice way to deal with an arbitrary number of drawable resources
        if (poi.images.length > 1) {
        	ImageView poiExtra1 = (ImageView) findViewById(R.id.poi_extra_image1);
        	bMap = BitmapFactory.decodeResource(getResources(), poi.images[1]);
        	poiExtra1.setImageBitmap(bMap);
        }
        if (poi.images.length > 2) {
        	ImageView poiExtra2 = (ImageView) findViewById(R.id.poi_extra_image2);
        	bMap = BitmapFactory.decodeResource(getResources(), poi.images[2]);
        	poiExtra2.setImageBitmap(bMap);
        }
        if (poi.images.length > 3) {
        	ImageView poiExtra3 = (ImageView) findViewById(R.id.poi_extra_image3);
        	bMap = BitmapFactory.decodeResource(getResources(), poi.images[3]);
        	poiExtra3.setImageBitmap(bMap);
        }
        if (poi.images.length > 4) {
        	ImageView poiExtra4 = (ImageView) findViewById(R.id.poi_extra_image4);
        	bMap = BitmapFactory.decodeResource(getResources(), poi.images[4]);
        	poiExtra4.setImageBitmap(bMap);
        }
        // and we don't have room for any more right now
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
