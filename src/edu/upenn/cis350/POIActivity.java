package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class POIActivity extends Activity {
	
	final Activity pushThis = this;
	
	public String[] poiInfo;
	public String[] poiTitle;
	public String[] poiLocation;
	
	public static final String POI_CODE_KEY = "POI_CODE_KEY";
	ImageView poiImage;
	TextView poiText;
	TextView poiHeader;
	Button mapButton;
	PointOfInterest poi;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        poiInfo = getResources().getStringArray(R.array.poi_info);
    	poiTitle = getResources().getStringArray(R.array.poi_theme);
    	poiLocation = getResources().getStringArray(R.array.poi_location);
        
        setContentView(R.layout.poi);
        
        int poiNumber = getIntent().getIntExtra(POI_CODE_KEY, 0);
        poi = PointOfInterest.getPOI(poiNumber);
        
        setPoiTitle(poiNumber);
        setPoiText(poiNumber);
        setPoiImages(poiNumber);
        setButton(poiNumber);
    }
	
	protected void setPoiImages(int poiNumber) {
		poiImage = (ImageView) findViewById(R.id.poi_image);
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
		poiText = (TextView) findViewById(R.id.poi_info);
		poiNumber = Math.min(poiNumber, poiInfo.length-1);
        poiText.setText(poiInfo[poiNumber]);
	}
	
	protected void setPoiTitle(int poiNumber) {
		poiHeader = (TextView) findViewById(R.id.poi_header);
		poiNumber = Math.min(poiNumber, poiTitle.length-1);
        poiHeader.setText(poiTitle[poiNumber]);
	}
	
	protected void setButton(int poiNumber) {
		mapButton = (Button) findViewById(R.id.map_button);
		final int _poiNumber = Math.min(poiNumber, poiTitle.length-1);
        mapButton.setText(poiLocation[_poiNumber]);
        mapButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    			Intent intent = new Intent(pushThis, MapActivity.class);
    			intent.putExtra(MapActivity.MAP_CODE_KEY, poi.mapNumber);
    			intent.putExtra(MapActivity.HIGHLIGHT_CODE_KEY, _poiNumber);
    			startActivity(intent);
    		}
    	});
	}
	
}
