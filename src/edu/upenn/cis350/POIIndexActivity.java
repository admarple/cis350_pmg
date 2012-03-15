package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class POIIndexActivity extends Activity {
	
	final Activity pushThis = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout layout = new LinearLayout (this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);

		// LinearLayout layout = (LinearLayout) findViewById(R.id.poi_layout);

		TextView header = new TextView(this);
		header.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		header.setText(R.string.poi_index_header);
		header.setTextSize((float) 22.0);
		
        String[] poiObject = getResources().getStringArray(R.array.poi_object);
        int numPOIs = poiObject.length;
        
        for (int i=0; i < numPOIs; i++) {
        	final int j = i;
            Button b = new Button (this);
            b.setText(poiObject[i]);
            b.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View view) {
        			Intent intent = new Intent(pushThis, POIActivity.class);
        			intent.putExtra(POIActivity.POI_CODE_KEY, j);
        			startActivity(intent);
        		}
        	});
            layout.addView(b);
        } 

        super.setContentView(layout);
	}
	
}
